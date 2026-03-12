package controller.home;

import dao.UserDAO;
import dto.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");

// Lấy user mới nhất từ DB
        User user = userDAO.getUserById(sessionUser.getUserId());

        request.setAttribute("user", user);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");
        int userId = sessionUser.getUserId();

        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String oldPassword = request.getParameter("oldPassword");

        boolean updated = userDAO.updateProfile(userId, fullName, phone, address);

        // ===== XỬ LÝ ĐỔI MẬT KHẨU =====
        if (newPassword != null && !newPassword.trim().isEmpty()) {

    // 1Kiểm tra mật khẩu cũ
    if (!userDAO.checkOldPassword(userId, oldPassword)) {
        request.setAttribute("error", "Old password is incorrect!");
        request.setAttribute("user", sessionUser);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
        return;
    }

    // 2Kiểm tra confirm
    if (!newPassword.equals(confirmPassword)) {
        request.setAttribute("error", "Password confirmation does not match!");
        request.setAttribute("user", sessionUser);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
        return;
    }

    userDAO.updatePassword(userId, newPassword);
}

        if (updated) {

            // Cập nhật lại session object
            sessionUser.setFullName(fullName);
            sessionUser.setPhone(phone);
            sessionUser.setAddress(address);

            session.setAttribute("user", sessionUser);
            request.setAttribute("success", "Update successful!");
        } else {
            request.setAttribute("error", "Update failed!");
        }

        request.setAttribute("user", sessionUser);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}
