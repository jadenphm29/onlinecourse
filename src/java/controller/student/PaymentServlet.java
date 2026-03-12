package controller.student;
import dao.CourseDAO;
import dao.RegistrationDAO;
import dao.UserDAO;
import dto.Course;
import dto.RegistrationView;
import dto.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PaymentServlet extends HttpServlet {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final CourseDAO       courseDAO       = new CourseDAO();
    private final UserDAO         userDAO         = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String regIdStr = request.getParameter("regId");
        if (regIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/myCourses");
            return;
        }
        int              regId  = Integer.parseInt(regIdStr);
        RegistrationView reg    = registrationDAO.getRegistrationById(regId);
        if (reg == null) {
            response.sendRedirect(request.getContextPath() + "/myCourses");
            return;
        }
        Course     course  = courseDAO.getCourseById(reg.getCourseId());
        HttpSession session = request.getSession(false);
        int        userId  = (Integer) session.getAttribute("userId");
        double     balance = userDAO.getBalance(userId);

        request.setAttribute("regId",   regId);
        request.setAttribute("course",  course);
        request.setAttribute("balance", balance);
        request.getRequestDispatcher("/jsp/student/payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int    userId = (Integer) session.getAttribute("userId");
        int    regId  = Integer.parseInt(request.getParameter("regId"));

        RegistrationView reg = registrationDAO.getRegistrationById(regId);
        if (reg == null) {
            response.sendRedirect(request.getContextPath() + "/myCourses");
            return;
        }
        Course course = courseDAO.getCourseById(reg.getCourseId());
        double fee    = course.getTuitionFee();

        boolean deducted = userDAO.deductBalance(userId, fee);
        if (!deducted) {
            request.setAttribute("regId",   regId);
            request.setAttribute("course",  course);
            request.setAttribute("balance", userDAO.getBalance(userId));
            request.setAttribute("error",   "Insufficient balance. Please top up.");
            request.getRequestDispatcher("/jsp/student/payment.jsp")
                   .forward(request, response);
            return;
        }

        registrationDAO.updatePaymentStatus(regId, "PAID");

        // Cập nhật session
        User updatedUser = userDAO.getUserById(userId);
        session.setAttribute("user", updatedUser);

        // Gửi mail xác nhận thanh toán
        mylib.EmailUtils.sendPaymentConfirm(
                updatedUser.getEmail(),
                updatedUser.getFullName(),
                course.getName(),
                fee
        );

        response.sendRedirect(request.getContextPath()
                + "/payment-success?regId=" + regId);
    }
}