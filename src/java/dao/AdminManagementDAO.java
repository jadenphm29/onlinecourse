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
public class AdminManagementDAO {
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
                    +"FROM [dbo].[Registrations]";
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
                String sql = "SELECT [regId], [studentId], [courseId], [regDate], [status], [note]"
                    +"FROM [dbo].[Registrations]"
                    +"WHERE [studentId] = ?";
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
                    +"FROM [dbo].[Registrations]"
                    +"WHERE [courseId] = ?";
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

    // 4. Insert a new registration (using RegistrationView)
    public boolean addRegistration(RegistrationView registration) {
        Connection cn = null;
        PreparedStatement st = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO [dbo].[Registrations] ([studentId], [courseId], [regDate], [status], [note])"
                    +"VALUES (?, ?, ?, ?, ?)";
                st = cn.prepareStatement(sql);
                st.setInt(1, registration.getStudentId());
                st.setInt(2, registration.getCourseId());
                st.setDate(3, registration.getRegDate());
                st.setString(4, registration.getStatus());
                st.setString(5, registration.getNote());

                return st.executeUpdate() > 0; // return true if insertion is successful
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    // 5. Update a registration (using RegistrationView)
    public boolean updateRegistration(RegistrationView registration) {
        Connection cn = null;
        PreparedStatement st = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[Registrations]"
                    +"SET [status] = ?, [note] = ?"
                    +"WHERE [regId] = ?";
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

    // 6. Delete a registration by regId
    public boolean deleteRegistration(int regId) {
        Connection cn = null;
        PreparedStatement st = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "DELETE FROM [dbo].[Registrations] WHERE [regId] = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, regId);

                return st.executeUpdate() > 0; // return true if delete is successful
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
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
