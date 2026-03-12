package controller.instructor;
import dao.CourseDAO;
import dao.RegistrationDAO;
import dao.UserDAO;
import dto.Course;
import dto.RegistrationView;
import dto.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UpdateRegistrationStatusServlet extends HttpServlet {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final UserDAO         userDAO         = new UserDAO();
    private final CourseDAO       courseDAO       = new CourseDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"INSTRUCTOR".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int    regId    = Integer.parseInt(request.getParameter("regId"));
        int    courseId = Integer.parseInt(request.getParameter("courseId"));
        String status   = request.getParameter("status");

        RegistrationView reg = registrationDAO.getRegistrationById(regId);
        if (reg == null) {
            response.sendRedirect(request.getContextPath()
                    + "/instructor/registrations?courseId=" + courseId);
            return;
        }

        Course course  = courseDAO.getCourseById(reg.getCourseId());
        User   student = userDAO.getUserById(reg.getStudentId());

        // Chặn APPROVE nếu chưa PAID
        if ("APPROVED".equals(status)) {
            String paymentStatus = registrationDAO.getPaymentStatus(regId);
            if (!"PAID".equals(paymentStatus)) {
                response.sendRedirect(request.getContextPath()
                        + "/instructor/registrations?courseId=" + courseId
                        + "&error=Student+has+not+paid+yet");
                return;
            }
        }

        // Hoàn tiền nếu REJECT + đã PAID
        boolean refunded = false;
        if ("REJECTED".equals(status)) {
            String paymentStatus = registrationDAO.getPaymentStatus(regId);
            if ("PAID".equals(paymentStatus) && course != null
                    && course.getTuitionFee() > 0) {
                userDAO.topUp(reg.getStudentId(), course.getTuitionFee());
                registrationDAO.updatePaymentStatus(regId, "REFUNDED");
                refunded = true;
            }
        }

        registrationDAO.updateStatusByRegId(regId, status);

        // Gửi mail thông báo
        if (student != null && course != null) {
            if ("APPROVED".equals(status)) {
                mylib.EmailUtils.sendApprovalNotice(
                        student.getEmail(),
                        student.getFullName(),
                        course.getName()
                );
            } else if ("REJECTED".equals(status)) {
                mylib.EmailUtils.sendRejectionNotice(
                        student.getEmail(),
                        student.getFullName(),
                        course.getName(),
                        refunded,
                        course.getTuitionFee()
                );
            }
        }

        response.sendRedirect(request.getContextPath()
                + "/instructor/registrations?courseId=" + courseId);
    }
}