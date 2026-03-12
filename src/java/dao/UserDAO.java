package dao;

import dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import mylib.DBUtils;

public class UserDAO {

    // ── Helper: map 1 row → User ──────────────────────────────
    private User mapUser(ResultSet rs) throws Exception {
        User u = new User(
                rs.getInt("userId"),
                rs.getString("fullName"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone"),
                rs.getString("address"),
                rs.getString("role"),
                rs.getString("status")
        );
        u.setBalance(rs.getDouble("balance"));
        return u;
    }

    // ─────────────────────────────────────────────────────────
    public ArrayList<User> getAllStudents() {
        ArrayList<User> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [userId],[fullName],[email],[password],[phone],[address],[role],[status],[balance] "
                        + "FROM [dbo].[Users] WHERE [role] = 'STUDENT'";
                st = cn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) list.add(mapUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    public User getUserByEmail(String email) {
        User u = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [userId],[fullName],[email],[password],[phone],[address],[role],[status],[balance] "
                        + "FROM [dbo].[Users] WHERE [email] = ?";
                st = cn.prepareStatement(sql);
                st.setString(1, email);
                rs = st.executeQuery();
                if (rs.next()) u = mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return u;
    }

    public User checkLogin(String email, String password) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [userId],[fullName],[email],[password],[phone],[address],[role],[status],[balance] "
                        + "FROM [dbo].[Users] "
                        + "WHERE [email]=? AND [password]=? AND [status]='ACTIVE'";
                st = cn.prepareStatement(sql);
                st.setString(1, email);
                st.setString(2, password);
                rs = st.executeQuery();
                if (rs.next()) return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return null;
    }

    public boolean emailExist(String email) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT 1 FROM [dbo].[Users] WHERE [email]=?";
                st = cn.prepareStatement(sql);
                st.setString(1, email);
                rs = st.executeQuery();
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return true;
    }

    public boolean registerStudent(String fullName, String email, String password) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO [dbo].[Users]([fullName],[email],[password],[role],[status]) "
                        + "VALUES(?,?,?,'STUDENT','ACTIVE')";
                st = cn.prepareStatement(sql);
                st.setString(1, fullName);
                st.setString(2, email);
                st.setString(3, password);
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public boolean updateProfile(int userId, String fullName, String phone, String address) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[Users] SET [fullName]=?,[phone]=?,[address]=? WHERE [userId]=?";
                st = cn.prepareStatement(sql);
                st.setString(1, fullName);
                st.setString(2, phone);
                st.setString(3, address);
                st.setInt(4, userId);
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public boolean updatePassword(int userId, String newPassword) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE Users SET password=? WHERE userId=?";
                st = cn.prepareStatement(sql);
                st.setString(1, newPassword);
                st.setInt(2, userId);
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public boolean checkOldPassword(int userId, String oldPassword) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            String sql = "SELECT password FROM Users WHERE userId=?";
            st = cn.prepareStatement(sql);
            st.setInt(1, userId);
            rs = st.executeQuery();
            if (rs.next()) return rs.getString("password").equals(oldPassword);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return false;
    }

    public User getUserById(int userId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [userId],[fullName],[email],[password],[phone],[address],[role],[status],[balance] "
                        + "FROM [dbo].[Users] WHERE [userId]=?";
                st = cn.prepareStatement(sql);
                st.setInt(1, userId);
                rs = st.executeQuery();
                if (rs.next()) return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return null;
    }

    public int countByRole(String role) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*) AS total FROM [dbo].[Users] WHERE [role]=? AND [status]='ACTIVE'";
                st = cn.prepareStatement(sql);
                st.setString(1, role);
                rs = st.executeQuery();
                if (rs.next()) return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return 0;
    }

    public boolean registerUser(User user) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String checkSql = "SELECT COUNT(*) FROM [dbo].[Users] WHERE email=?";
                st = cn.prepareStatement(checkSql);
                st.setString(1, user.getEmail());
                rs = st.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) return false;

                String sql = "INSERT INTO [dbo].[Users]([fullName],[email],[password],[phone],[address],[role],[status]) "
                        + "VALUES(?,?,?,?,?,?,?)";
                st = cn.prepareStatement(sql);
                st.setString(1, user.getFullName());
                st.setString(2, user.getEmail());
                st.setString(3, user.getPassword());
                st.setString(4, user.getPhone());
                st.setString(5, user.getAddress());
                st.setString(6, user.getRole());
                st.setString(7, user.getStatus());
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return false;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [userId],[fullName],[email],[password],[phone],[address],[role],[status],[balance] "
                        + "FROM Users WHERE role IN ('STUDENT','INSTRUCTOR')";
                st = cn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) list.add(mapUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    public ArrayList<User> searchUsersByEmail(String email) {
        ArrayList<User> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [userId],[fullName],[email],[password],[phone],[address],[role],[status],[balance] "
                        + "FROM Users WHERE email LIKE ? AND role IN ('STUDENT','INSTRUCTOR')";
                st = cn.prepareStatement(sql);
                st.setString(1, "%" + email + "%");
                rs = st.executeQuery();
                while (rs.next()) list.add(mapUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    public ArrayList<User> getUsersByRole(String role) {
        ArrayList<User> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [userId],[fullName],[email],[password],[phone],[address],[role],[status],[balance] "
                        + "FROM Users WHERE role=?";
                st = cn.prepareStatement(sql);
                st.setString(1, role);
                rs = st.executeQuery();
                while (rs.next()) list.add(mapUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, st, cn);
        }
        return list;
    }

    public boolean deleteUser(int userId) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "DELETE FROM Users WHERE userId=?";
                st = cn.prepareStatement(sql);
                st.setInt(1, userId);
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public boolean updateUserStatus(int userId, String status) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE Users SET status=? WHERE userId=?";
                st = cn.prepareStatement(sql);
                st.setString(1, status);
                st.setInt(2, userId);
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public boolean addUser(User u) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO [dbo].[Users]([fullName],[email],[password],[phone],[address],[role],[status])"
                        + " VALUES(?,?,?,?,?,?,'ACTIVE')";
                st = cn.prepareStatement(sql);
                st.setString(1, u.getFullName());
                st.setString(2, u.getEmail());
                st.setString(3, u.getPassword());
                st.setString(4, u.getPhone());
                st.setString(5, u.getAddress());
                st.setString(6, u.getRole());
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public boolean updateUser(User u) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[Users] SET "
                        + "[fullName]=?,[email]=?,[phone]=?,[address]=?,[role]=?,[status]=? "
                        + "WHERE [userId]=?";
                st = cn.prepareStatement(sql);
                st.setString(1, u.getFullName());
                st.setString(2, u.getEmail());
                st.setString(3, u.getPhone());
                st.setString(4, u.getAddress());
                st.setString(5, u.getRole());
                st.setString(6, u.getStatus());
                st.setInt(7, u.getUserId());
                return st.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(null, st, cn);
        }
        return false;
    }

    public double getBalance(int userId) {
    Connection cn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
        cn = DBUtils.getConnection();
        if (cn != null) {
            String sql = "SELECT ISNULL(balance, 0) AS balance FROM Users WHERE userId=?";
            st = cn.prepareStatement(sql);
            st.setInt(1, userId);
            rs = st.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeAll(rs, st, cn);
    }
    return 0;
}

    public boolean topUp(int userId, double amount) {
    Connection cn = null;
    PreparedStatement st = null;
    try {
        cn = DBUtils.getConnection();
        if (cn != null) {
            String sql = "UPDATE Users SET balance = ISNULL(balance, 0) + ? WHERE userId=?";
            st = cn.prepareStatement(sql);
            st.setDouble(1, amount);
            st.setInt(2, userId);
            return st.executeUpdate() > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeAll(null, st, cn);
    }
    return false;
}

    public boolean deductBalance(int userId, double amount) {
    Connection cn = null;
    PreparedStatement st = null;
    try {
        cn = DBUtils.getConnection();
        if (cn != null) {
            String sql = "UPDATE Users SET balance = ISNULL(balance, 0) - ? "
                       + "WHERE userId=? AND ISNULL(balance, 0) >= ?";
            st = cn.prepareStatement(sql);
            st.setDouble(1, amount);
            st.setInt(2, userId);
            st.setDouble(3, amount);
            return st.executeUpdate() > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeAll(null, st, cn);
    }
    return false;
}

    private void closeAll(ResultSet rs, PreparedStatement st, Connection cn) {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (st != null) st.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (cn != null) cn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
}