package controller.student;

import dao.UserDAO;
import dto.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class WalletServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        double balance = userDAO.getBalance(userId);
        request.setAttribute("balance", balance);
        request.getRequestDispatcher("jsp/student/wallet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        try {
            double amount = Double.parseDouble(request.getParameter("amount"));
            if (amount <= 0) throw new NumberFormatException();

            userDAO.topUp(userId, amount);

            // Cập nhật lại user trong session
            User updatedUser = userDAO.getUserById(userId);
            session.setAttribute("user", updatedUser);

            response.sendRedirect(request.getContextPath() + "/wallet?success=1");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/wallet?error=1");
        }
    }
}