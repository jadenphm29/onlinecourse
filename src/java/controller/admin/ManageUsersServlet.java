package controller.admin;

import dao.UserDAO;
import dto.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ManageUsersServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User currentUser = (User) session.getAttribute("user");
        if (!"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String email = request.getParameter("email");
        String role  = request.getParameter("role");
        List<User> users;

        if (email != null && !email.trim().isEmpty())
            users = userDAO.searchUsersByEmail(email.trim());
        else if (role != null && !role.trim().isEmpty())
            users = userDAO.getUsersByRole(role.trim());
        else
            users = userDAO.getAllUsers();

        request.setAttribute("users",       users);
        request.setAttribute("filterEmail", email != null ? email : "");
        request.setAttribute("filterRole",  role  != null ? role  : "");

        request.getRequestDispatcher("/jsp/admin/manageUsers.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User currentUser = (User) session.getAttribute("user");
        if (!"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        int    userId = Integer.parseInt(request.getParameter("userId"));
        User   target = userDAO.getUserById(userId);

        if (target == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageUsers");
            return;
        }

        if ("delete".equals(action)) {
            if (!"ADMIN".equals(target.getRole())
                    && currentUser.getUserId() != userId) {
                userDAO.deleteUser(userId);
            }
        }

        if ("toggleStatus".equals(action)) {
            if (!"ADMIN".equals(target.getRole())) {
                String newStatus = "ACTIVE".equals(target.getStatus())
                        ? "LOCKED" : "ACTIVE";
                userDAO.updateUserStatus(userId, newStatus);
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/manageUsers");
    }
}