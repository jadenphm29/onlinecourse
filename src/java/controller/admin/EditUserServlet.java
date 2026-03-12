package controller.admin;

import dao.UserDAO;
import dto.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EditUserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int  userId  = Integer.parseInt(request.getParameter("userId"));
        User editUser = userDAO.getUserById(userId);

        if (editUser == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageUsers");
            return;
        }

        request.setAttribute("editUser", editUser);
        request.getRequestDispatcher("/jsp/admin/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int    userId  = Integer.parseInt(request.getParameter("userId"));
        String fullName = request.getParameter("fullName");
        String email    = request.getParameter("email");
        String phone    = request.getParameter("phone");
        String address  = request.getParameter("address");
        String role     = request.getParameter("role");
        String status   = request.getParameter("status");

        User u = new User(userId, fullName, email, null, phone, address, role, status);
        userDAO.updateUser(u);

        response.sendRedirect(request.getContextPath() + "/admin/manageUsers");
    }
}