package dao;

import dto.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mylib.DBUtils;

public class CourseDAO {

    public List<Course> getAllCourse() {
        List<Course> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT c.[courseId], c.[name], c.[description], "
                        + "    c.[tuitionFee], c.[startDate], c.[endDate], "
                        + "    c.[scheduleText], c.[instructorId], "
                        + "    c.[status], c.[totalSlots], "
                        + "    c.[thumbnailUrl], c.[videoUrl], c.[detailDescription], "
                        + "    u.[fullName] AS instructorName, "
                        + "    u.[status]   AS instructorStatus, "
                        + "    (SELECT COUNT(*) FROM [dbo].[Registrations] r "
                        + "     WHERE r.[courseId] = c.[courseId] "
                        + "       AND r.[status] IN ('PENDING','APPROVED')) AS enrolledCount "
                        + "FROM [dbo].[Courses] c "
                        + "LEFT JOIN [dbo].[Users] u ON c.[instructorId] = u.[userId] "
                        + "ORDER BY c.[startDate]";
                st = cn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    Course c = mapCourse(rs);
                    list.add(c);
                }
                System.out.println("Found " + list.size() + " courses.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    public Course getCourseById(int courseId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT c.[courseId], c.[name], c.[description], c.[tuitionFee], "
                        + "    c.[startDate], c.[endDate], c.[scheduleText], c.[instructorId], "
                        + "    c.[status], c.[totalSlots], "
                        + "    c.[thumbnailUrl], c.[videoUrl], c.[detailDescription], "
                        + "    u.[fullName] AS instructorName, "
                        + "    u.[status]   AS instructorStatus, "
                        + "    (SELECT COUNT(*) FROM [dbo].[Registrations] r "
                        + "     WHERE r.[courseId] = c.[courseId] "
                        + "       AND r.[status] IN ('PENDING','APPROVED')) AS enrolledCount "
                        + "FROM [dbo].[Courses] c "
                        + "LEFT JOIN [dbo].[Users] u ON c.[instructorId] = u.[userId] "
                        + "WHERE c.[courseId]=?";
                st = cn.prepareStatement(sql);
                st.setInt(1, courseId);
                rs = st.executeQuery();
                if (rs.next()) {
                    return mapCourse(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return null;
    }

    public boolean addCourse(Course c) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO [dbo].[Courses] "
                        + "([name],[description],[tuitionFee],[startDate],[endDate],"
                        + "[scheduleText],[instructorId],[status],[totalSlots],"
                        + "[thumbnailUrl],[videoUrl],[detailDescription]) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                st = cn.prepareStatement(sql);
                st.setString(1,  c.getName());
                st.setString(2,  c.getDescription());
                st.setDouble(3,  c.getTuitionFee());
                st.setDate(4,    c.getStartDate());
                st.setDate(5,    c.getEndDate());
                st.setString(6,  c.getScheduleText());
                if (c.getInstructorId() == null || c.getInstructorId() == 0)
                    st.setNull(7, java.sql.Types.INTEGER);
                else
                    st.setInt(7, c.getInstructorId());
                st.setString(8,  c.getStatus() != null ? c.getStatus() : "ACTIVE");
                st.setInt(9,     c.getTotalSlots() > 0  ? c.getTotalSlots() : 30);
                st.setString(10, c.getThumbnailUrl());
                st.setString(11, c.getVideoUrl());
                st.setString(12, c.getDetailDescription());
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public boolean updateCourseByAdmin(Course c) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[Courses] SET "
                        + "[name]=?, [description]=?, [tuitionFee]=?, "
                        + "[startDate]=?, [endDate]=?, [scheduleText]=?, "
                        + "[instructorId]=?, [totalSlots]=?, [status]=?, "
                        + "[thumbnailUrl]=?, [videoUrl]=?, [detailDescription]=? "
                        + "WHERE [courseId]=?";
                st = cn.prepareStatement(sql);
                st.setString(1,  c.getName());
                st.setString(2,  c.getDescription());
                st.setDouble(3,  c.getTuitionFee());
                st.setDate(4,    c.getStartDate());
                st.setDate(5,    c.getEndDate());
                st.setString(6,  c.getScheduleText());
                if (c.getInstructorId() == null || c.getInstructorId() == 0)
                    st.setNull(7, java.sql.Types.INTEGER);
                else
                    st.setInt(7, c.getInstructorId());
                st.setInt(8,     c.getTotalSlots());
                st.setString(9,  c.getStatus());
                st.setString(10, c.getThumbnailUrl());
                st.setString(11, c.getVideoUrl());
                st.setString(12, c.getDetailDescription());
                st.setInt(13,    c.getCourseId());
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public boolean updateCourseByInstructor(Course c, int instructorId) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[Courses] SET "
                        + "[name]=?, [description]=?, [tuitionFee]=?, "
                        + "[startDate]=?, [endDate]=?, [scheduleText]=?, "
                        + "[totalSlots]=?, [status]=?, "
                        + "[thumbnailUrl]=?, [videoUrl]=?, [detailDescription]=? "
                        + "WHERE [courseId]=? AND [instructorId]=?";
                st = cn.prepareStatement(sql);
                st.setString(1,  c.getName());
                st.setString(2,  c.getDescription());
                st.setDouble(3,  c.getTuitionFee());
                st.setDate(4,    c.getStartDate());
                st.setDate(5,    c.getEndDate());
                st.setString(6,  c.getScheduleText());
                st.setInt(7,     c.getTotalSlots());
                st.setString(8,  c.getStatus());
                st.setString(9,  c.getThumbnailUrl());
                st.setString(10, c.getVideoUrl());
                st.setString(11, c.getDetailDescription());
                st.setInt(12,    c.getCourseId());
                st.setInt(13,    instructorId);
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public int countAllCourses() {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                st = cn.prepareStatement("SELECT COUNT(*) FROM [dbo].[Courses]");
                rs = st.executeQuery();
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return 0;
    }

    public boolean deleteCourse(int courseId) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                st = cn.prepareStatement(
                        "DELETE FROM [dbo].[Courses] WHERE [courseId]=?");
                st.setInt(1, courseId);
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public List<Course> getStudentCourses(int studentId) {
        List<Course> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT c.[courseId], c.[name], c.[description], "
                        + "    c.[tuitionFee], c.[startDate], c.[endDate], "
                        + "    c.[scheduleText], c.[instructorId], "
                        + "    c.[status], c.[totalSlots], "
                        + "    c.[thumbnailUrl], c.[videoUrl], c.[detailDescription], "
                        + "    u.[fullName] AS instructorName, "
                        + "    u.[status]   AS instructorStatus, "
                        + "    (SELECT COUNT(*) FROM [dbo].[Registrations] r2 "
                        + "     WHERE r2.[courseId] = c.[courseId] "
                        + "       AND r2.[status] IN ('PENDING','APPROVED')) AS enrolledCount "
                        + "FROM [dbo].[Courses] c "
                        + "JOIN [dbo].[Registrations] r ON c.[courseId] = r.[courseId] "
                        + "                            AND r.[studentId] = ? "
                        + "LEFT JOIN [dbo].[Users] u ON c.[instructorId] = u.[userId]";
                st = cn.prepareStatement(sql);
                st.setInt(1, studentId);
                rs = st.executeQuery();
                while (rs.next()) {
                    list.add(mapCourse(rs));
                }
                System.out.println("MyCourses size: " + list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    public List<Course> getInstructorCourses(int instructorId) {
        List<Course> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT c.[courseId], c.[name], c.[description], "
                        + "    c.[tuitionFee], c.[startDate], c.[endDate], "
                        + "    c.[scheduleText], c.[instructorId], "
                        + "    c.[status], c.[totalSlots], "
                        + "    c.[thumbnailUrl], c.[videoUrl], c.[detailDescription], "
                        + "    u.[fullName] AS instructorName, "
                        + "    u.[status]   AS instructorStatus, "
                        + "    (SELECT COUNT(*) FROM [dbo].[Registrations] r "
                        + "     WHERE r.[courseId] = c.[courseId] "
                        + "       AND r.[status] IN ('PENDING','APPROVED')) AS enrolledCount "
                        + "FROM [dbo].[Courses] c "
                        + "LEFT JOIN [dbo].[Users] u ON c.[instructorId] = u.[userId] "
                        + "WHERE c.[instructorId] = ? "
                        + "ORDER BY c.[startDate]";
                st = cn.prepareStatement(sql);
                st.setInt(1, instructorId);
                rs = st.executeQuery();
                while (rs.next()) {
                    list.add(mapCourse(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    // ═══ Helper: map ResultSet → Course ═══
    private Course mapCourse(ResultSet rs) throws Exception {
        Course c = new Course(
                rs.getInt("courseId"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("tuitionFee"),
                rs.getDate("startDate"),
                rs.getDate("endDate"),
                rs.getString("scheduleText"),
                (Integer) rs.getObject("instructorId")
        );
        c.setStatus(rs.getString("status"));
        c.setTotalSlots(rs.getInt("totalSlots"));
        c.setInstructorName(rs.getString("instructorName"));
        c.setInstructorStatus(rs.getString("instructorStatus"));
        c.setEnrolledCount(rs.getInt("enrolledCount"));
        c.setThumbnailUrl(rs.getString("thumbnailUrl"));
        c.setVideoUrl(rs.getString("videoUrl"));
        c.setDetailDescription(rs.getString("detailDescription"));
        return c;
    }

    private void closeAll(ResultSet rs, PreparedStatement st, Connection cn) {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (st != null) st.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (cn != null) cn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
}