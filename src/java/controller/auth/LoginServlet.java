package controller.auth;

import dao.UserDAO;
import dto.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();  // Tạo đối tượng của UserDAO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang đăng nhập
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin đăng nhập từ form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Kiểm tra thông tin đăng nhập
        User user = userDAO.checkLogin(email, password);  // Sử dụng đối tượng userDAO để gọi checkLogin()

        if (user != null) {
            // Lưu thông tin người dùng vào session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            session.setAttribute("userId", user.getUserId());
           
            // Chuyển hướng đến trang home dựa trên role
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
