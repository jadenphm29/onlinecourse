/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.RegistrationView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author Admin
 */
public class RegistrationDAO {

    // 1. Get all registrations (using RegistrationView)
    public ArrayList<RegistrationView> getAllRegistrations() {
        ArrayList<RegistrationView> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [regId], [studentId], [courseId], [regDate], [status], [note]"
                        + "FROM [dbo].[Registrations]";
                st = cn.prepareStatement(sql);
                rs = st.executeQuery();

                while (rs.next()) {
                    RegistrationView reg = new RegistrationView(
                            rs.getInt("regId"),
                            rs.getInt("studentId"),
                            rs.getInt("courseId"),
                            rs.getDate("regDate"),
                            rs.getString("status"),
                            rs.getString("note")
                    );
                    list.add(reg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    // 2. Get registrations by studentId
    public ArrayList<RegistrationView> getRegistrationsByStudent(int studentId) {
        ArrayList<RegistrationView> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT r.[regId], r.[studentId], r.[courseId], "
                        + "    r.[regDate], r.[status], r.[note], r.[paymentStatus], "
                        + "    u.[fullName] AS studentName,  "
                        + "    u.[email]    AS studentEmail, "
                        + "    c.[name]     AS courseName    "
                        + "FROM [dbo].[Registrations] r "
                        + "JOIN [dbo].[Users]   u ON u.[userId]   = r.[studentId] "
                        + "JOIN [dbo].[Courses] c ON c.[courseId] = r.[courseId]  "
                        + "WHERE r.[studentId] = ? "
                        + "ORDER BY r.[regDate] DESC";
                st = cn.prepareStatement(sql);
                st.setInt(1, studentId);
                rs = st.executeQuery();
                while (rs.next()) {
                    RegistrationView reg = new RegistrationView(
                            rs.getInt("regId"),
                            rs.getInt("studentId"),
                            rs.getInt("courseId"),
                            rs.getDate("regDate"),
                            rs.getString("status"),
                            rs.getString("note")
                    );
                    reg.setStudentName(rs.getString("studentName"));
                    reg.setStudentEmail(rs.getString("studentEmail"));
                    reg.setCourseName(rs.getString("courseName"));
                    reg.setPaymentStatus(rs.getString("paymentStatus")); // thêm
                    list.add(reg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    // 3. Get registrations by courseId
    public ArrayList<RegistrationView> getRegistrationsByCourse(int courseId) {
        ArrayList<RegistrationView> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [regId], [studentId], [courseId], [regDate], [status], [note]"
                        + "FROM [dbo].[Registrations]"
                        + "WHERE [courseId] = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, courseId);
                rs = st.executeQuery();

                while (rs.next()) {
                    RegistrationView reg = new RegistrationView(
                            rs.getInt("regId"),
                            rs.getInt("studentId"),
                            rs.getInt("courseId"),
                            rs.getDate("regDate"),
                            rs.getString("status"),
                            rs.getString("note")
                    );
                    list.add(reg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    // 4. Insert a new registration (using RegistrationDTO)
    public int addRegistration(RegistrationView registration) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {

                String sql = "INSERT INTO [dbo].[Registrations] "
                        + "([studentId], [courseId], [regDate], [status], [note]) "
                        + "VALUES (?, ?, ?, ?, ?)";

                st = cn.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS);

                st.setInt(1, registration.getStudentId());
                st.setInt(2, registration.getCourseId());
                st.setDate(3, registration.getRegDate());
                st.setString(4, registration.getStatus());
                st.setString(5, registration.getNote());

                int rows = st.executeUpdate();

                if (rows > 0) {
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        return rs.getInt(1); // return regId
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return -1; // thất bại
    }

    // 5. Update a registration (using RegistrationView)
    public boolean updateRegistration(RegistrationView registration) {
        Connection cn = null;
        PreparedStatement st = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[Registrations]"
                        + "SET [status] = ?, [note] = ?"
                        + "WHERE [regId] = ?";
                st = cn.prepareStatement(sql);
                st.setString(1, registration.getStatus());
                st.setString(2, registration.getNote());
                st.setInt(3, registration.getRegId());

                return st.executeUpdate() > 0; // return true if update is successful
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    // 6. Delete a registration by studentId and courseId
    public boolean deleteByStudentAndCourse(int studentId, int courseId) {

        Connection cn = null;
        PreparedStatement st = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {

                String sql = "DELETE FROM [dbo].[Registrations] "
                        + "WHERE [studentId] = ? "
                        + "AND [courseId] = ?";

                st = cn.prepareStatement(sql);
                st.setInt(1, studentId);
                st.setInt(2, courseId);

                int rows = st.executeUpdate();
                System.out.println("Rows deleted: " + rows);

                return rows > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }

        return false;
    }

    public boolean isAlreadyRegistered(int studentId, int courseId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {

                String sql = "SELECT COUNT(*) FROM [dbo].[Registrations] "
                        + "WHERE [studentId] = ? "
                        + "AND [courseId] = ? "
                        + "AND [status] IN ('APPROVED','PENDING')";

                st = cn.prepareStatement(sql);
                st.setInt(1, studentId);
                st.setInt(2, courseId);

                rs = st.executeQuery();

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }

        return false;
    }

    public boolean updateStatus(int studentId,
            int courseId,
            String status) {

        Connection cn = null;
        PreparedStatement st = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {

                String sql = "UPDATE [dbo].[Registrations] "
                        + "SET [status] = ? "
                        + "WHERE [studentId] = ? "
                        + "AND [courseId] = ?";

                st = cn.prepareStatement(sql);
                st.setString(1, status);
                st.setInt(2, studentId);
                st.setInt(3, courseId);

                return st.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }

        return false;
    }

// ====== ADD: Update by regId (Instructor use) ======
    public boolean updateStatusByRegId(int regId, String status) {

        Connection cn = null;
        PreparedStatement st = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {

                String sql = "UPDATE [dbo].[Registrations] "
                        + "SET [status] = ? "
                        + "WHERE [regId] = ?";

                st = cn.prepareStatement(sql);
                st.setString(1, status);
                st.setInt(2, regId);

                return st.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }

        return false;
    }
    // ====== ADD: Get payment status by regId ======

    public String getPaymentStatus(int regId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [paymentStatus] FROM [dbo].[Registrations] "
                        + "WHERE [regId] = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, regId);
                rs = st.executeQuery();
                if (rs.next()) {
                    return rs.getString("paymentStatus");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return "UNPAID";
    }

// Chỉ APPROVE nếu đã PAID
    public boolean approveIfPaid(int regId) {
        String sql = "UPDATE Registrations SET status = 'APPROVED' "
                + "WHERE regId = ? AND paymentStatus = 'PAID' AND status = 'PENDING'";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, regId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePaymentStatus(int regId, String status) {
        String sql = "UPDATE Registrations SET paymentStatus = ? WHERE regId = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, regId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public RegistrationView getRegistrationById(int regId) {

        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {

                String sql = "SELECT [regId], [studentId], [courseId], [regDate], [status], [note] "
                        + "FROM [dbo].[Registrations] "
                        + "WHERE [regId] = ?";

                st = cn.prepareStatement(sql);
                st.setInt(1, regId);

                rs = st.executeQuery();

                if (rs.next()) {
                    return new RegistrationView(
                            rs.getInt("regId"),
                            rs.getInt("studentId"),
                            rs.getInt("courseId"),
                            rs.getDate("regDate"),
                            rs.getString("status"),
                            rs.getString("note")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }

        return null;
    }

    /**
     * Lấy danh sách đăng ký theo courseId — JOIN Users để có studentName +
     * studentEmail
     */
    public ArrayList<RegistrationView> getRegistrationsByCourseWithStudentInfo(int courseId) {
        ArrayList<RegistrationView> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT r.[regId], r.[studentId], r.[courseId], "
                        + "    r.[regDate], r.[status], r.[note], r.[paymentStatus], " // thêm paymentStatus
                        + "    u.[fullName] AS studentName, "
                        + "    u.[email]    AS studentEmail "
                        + "FROM [dbo].[Registrations] r "
                        + "JOIN [dbo].[Users] u ON u.[userId] = r.[studentId] "
                        + "WHERE r.[courseId] = ? "
                        + "ORDER BY r.[regDate] DESC";

                st = cn.prepareStatement(sql);
                st.setInt(1, courseId);
                rs = st.executeQuery();

                while (rs.next()) {
                    RegistrationView reg = new RegistrationView(
                            rs.getInt("regId"),
                            rs.getInt("studentId"),
                            rs.getInt("courseId"),
                            rs.getDate("regDate"),
                            rs.getString("status"),
                            rs.getString("note")
                    );
                    reg.setStudentName(rs.getString("studentName"));
                    reg.setStudentEmail(rs.getString("studentEmail"));
                    reg.setPaymentStatus(rs.getString("paymentStatus")); // thêm
                    list.add(reg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }
    // ═══════════════════════════════════════════════════════
// NEW METHOD 1 — Feature 3
// Count unique students across all courses of one instructor.
// Used by HomeServlet → instructorTotalStudents stat box.
// ═══════════════════════════════════════════════════════

    public int countUniqueStudentsByInstructor(int instructorId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(DISTINCT r.[studentId]) "
                        + "FROM [dbo].[Registrations] r "
                        + "JOIN [dbo].[Courses] c ON c.[courseId] = r.[courseId] "
                        + "WHERE c.[instructorId] = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, instructorId);
                rs = st.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return 0;
    }

// ═══════════════════════════════════════════════════════
// NEW METHOD 2 — Feature 3
// Count registrations by instructor + status.
// Used by HomeServlet → instructorPendingCount
//                     → instructorApprovedCount stat boxes.
// Call twice: countByInstructorAndStatus(id, "PENDING")
//             countByInstructorAndStatus(id, "APPROVED")
// ═══════════════════════════════════════════════════════
    public int countByInstructorAndStatus(int instructorId, String status) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*) "
                        + "FROM [dbo].[Registrations] r "
                        + "JOIN [dbo].[Courses] c ON c.[courseId] = r.[courseId] "
                        + "WHERE c.[instructorId] = ? AND r.[status] = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, instructorId);
                st.setString(2, status);
                rs = st.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return 0;
    }

// ═══════════════════════════════════════════════════════
// NEW METHOD 3 — Feature 3
// Fetch joined student rows for instructor's home.jsp table.
// Returns RegistrationView with studentName, studentEmail,
// courseName populated (new fields added to RegistrationView).
// Used by HomeServlet → instructorStudents attribute.
// ═══════════════════════════════════════════════════════
    public ArrayList<RegistrationView> getStudentRowsByInstructor(int instructorId) {
        ArrayList<RegistrationView> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT r.[regId], r.[studentId], r.[courseId], "
                        + "    r.[regDate], r.[status], r.[note], "
                        + "    u.[fullName] AS studentName,  "
                        + "    u.[email]    AS studentEmail, "
                        + "    c.[name]     AS courseName    "
                        + "FROM [dbo].[Registrations] r "
                        + "JOIN [dbo].[Users]   u ON u.[userId]   = r.[studentId] "
                        + "JOIN [dbo].[Courses] c ON c.[courseId] = r.[courseId]  "
                        + "WHERE c.[instructorId] = ? "
                        + "ORDER BY r.[regDate] DESC";

                st = cn.prepareStatement(sql);
                st.setInt(1, instructorId);
                rs = st.executeQuery();

                while (rs.next()) {
                    RegistrationView row = new RegistrationView(
                            rs.getInt("regId"),
                            rs.getInt("studentId"),
                            rs.getInt("courseId"),
                            rs.getDate("regDate"),
                            rs.getString("status"),
                            rs.getString("note")
                    );
                    row.setStudentName(rs.getString("studentName"));
                    row.setStudentEmail(rs.getString("studentEmail"));
                    row.setCourseName(rs.getString("courseName"));
                    list.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    public int countAllRegistrations() {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                st = cn.prepareStatement("SELECT COUNT(*) FROM [dbo].[Registrations]");
                rs = st.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return 0;
    }

    public ArrayList<RegistrationView> getRegistrationsPerCourse() {
        ArrayList<RegistrationView> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT c.[courseId], c.[name] AS courseName, "
                        + "    COUNT(r.[regId]) AS enrolledCount "
                        + "FROM [dbo].[Courses] c "
                        + "LEFT JOIN [dbo].[Registrations] r ON r.[courseId] = c.[courseId] "
                        + "GROUP BY c.[courseId], c.[name] "
                        + "ORDER BY enrolledCount DESC";
                st = cn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    RegistrationView rv = new RegistrationView();
                    rv.setCourseName(rs.getString("courseName"));
                    rv.setEnrolledCount(rs.getInt("enrolledCount"));
                    list.add(rv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    public RegistrationView getRegistrationByStudentAndCourse(int studentId, int courseId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT r.*, c.[name] AS courseName "
                        + "FROM Registrations r "
                        + "JOIN Courses c ON r.courseId = c.courseId "
                        + "WHERE r.studentId = ? AND r.courseId = ? "
                        + "AND r.status != 'CANCELED'";
                st = cn.prepareStatement(sql);
                st.setInt(1, studentId);
                st.setInt(2, courseId);
                rs = st.executeQuery();
                if (rs.next()) {
                    RegistrationView rv = new RegistrationView();
                    rv.setRegId(rs.getInt("regId"));
                    rv.setStudentId(rs.getInt("studentId"));
                    rv.setCourseId(rs.getInt("courseId"));
                    rv.setStatus(rs.getString("status"));
                    rv.setPaymentStatus(rs.getString("paymentStatus"));
                    rv.setCourseName(rs.getString("courseName"));
                    return rv;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return null;
    }

    private void closeAll(ResultSet rs, PreparedStatement st, Connection cn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (cn != null) {
                cn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
