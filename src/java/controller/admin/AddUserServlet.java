package controller.admin;

import dao.UserDAO;
import dto.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddUserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/jsp/admin/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String fullName = request.getParameter("fullName");
        String email    = request.getParameter("email");
        String password = request.getParameter("password");
        String phone    = request.getParameter("phone");
        String address  = request.getParameter("address");
        String role     = request.getParameter("role");

        // Kiểm tra email đã tồn tại chưa
        if (userDAO.emailExist(email)) {
            request.setAttribute("error", "Email already exists. Please use a different email.");
            request.getRequestDispatcher("/jsp/admin/addUser.jsp").forward(request, response);
            return;
        }

        User u = new User(0, fullName, email, password, phone, address, role, "ACTIVE");
        boolean ok = userDAO.addUser(u);

        if (ok) {
            response.sendRedirect(request.getContextPath() + "/admin/manageUsers");
        } else {
            request.setAttribute("error", "Failed to add user. Please try again.");
            request.getRequestDispatcher("/jsp/admin/addUser.jsp").forward(request, response);
        }
    }
}