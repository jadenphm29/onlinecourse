package controller.auth;

import dao.UserDAO;
import dto.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy thông tin từ form đăng ký
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String role = "STUDENT";  // Mặc định là sinh viên

        // Kiểm tra nếu mật khẩu và xác nhận mật khẩu trùng nhau
        if (password.equals(confirmPassword)) {
            // Tạo đối tượng User với các thông tin
            User newUser = new User(0, fullName, email, password, phone, address, role, "ACTIVE");

            // Gọi phương thức đăng ký người dùng
            boolean success = userDAO.registerUser(newUser);

            if (success) {
                // Đăng ký thành công, chuyển hướng đến trang login
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                // Đăng ký thất bại, hiển thị thông báo lỗi
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } else {
            // Nếu mật khẩu không khớp
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}

