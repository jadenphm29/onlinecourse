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

public class UnenrollCourseServlet extends HttpServlet {

    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final CourseDAO       courseDAO       = new CourseDAO();
    private final UserDAO         userDAO         = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int    studentId = (Integer) session.getAttribute("userId");
        String role      = (String)  session.getAttribute("role");
        if (!"STUDENT".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));

            // Tìm registration hiện tại
            RegistrationView reg = null;
            for (RegistrationView r : registrationDAO.getRegistrationsByStudent(studentId)) {
                if (r.getCourseId() == courseId) {
                    reg = r;
                    break;
                }
            }

            if (reg != null) {
                Course course = courseDAO.getCourseById(courseId);

                // Hoàn tiền nếu đã PAID + chưa APPROVED
                boolean refunded = false;
                if ("PAID".equals(reg.getPaymentStatus())
                        && !"APPROVED".equals(reg.getStatus())
                        && course != null
                        && course.getTuitionFee() > 0) {
                    userDAO.topUp(studentId, course.getTuitionFee());
                    refunded = true;
                }

                boolean deleted = registrationDAO.deleteByStudentAndCourse(studentId, courseId);

                if (deleted) {
                    // Cập nhật balance trong session
                    User updatedUser = userDAO.getUserById(studentId);
                    session.setAttribute("user", updatedUser);

                    // Gửi mail
                    if (course != null) {
                        String refundText = refunded
                            ? " Your payment of <b>"
                              + String.format("%,.0f", course.getTuitionFee())
                              + " VND</b> has been "
                              + "<b style='color:#f59e0b;'>refunded</b> to your wallet."
                            : "";
                        mylib.EmailUtils.sendEmail(
                            updatedUser.getEmail(),
                            "EduOnline — Unenrollment Confirmed",
                            mylib.EmailUtils.buildHtml(
                                "Unenrollment Confirmed &#128683;",
                                "Hi <b>" + updatedUser.getFullName() + "</b>,",
                                "You have successfully unenrolled from <b>"
                                    + course.getName() + "</b>." + refundText,
                                "#6b7280"
                            )
                        );
                    }

                    session.setAttribute("message", "You have successfully unenrolled."
                            + (refunded ? " Your payment has been refunded." : ""));
                } else {
                    session.setAttribute("error", "Unenroll failed.");
                }
            } else {
                session.setAttribute("error", "Registration not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "System error.");
        }

        response.sendRedirect(request.getContextPath() + "/myCourses");
    }
}