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
import java.sql.Date;
import java.util.List;

public class EnrollCourseServlet extends HttpServlet {
    private CourseDAO       courseDAO       = new CourseDAO();
    private RegistrationDAO registrationDAO = new RegistrationDAO();
    private UserDAO         userDAO         = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        List<Course> courses = courseDAO.getAllCourse();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/jsp/student/enroll.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String courseIdStr = request.getParameter("courseId");
        if (courseIdStr == null || courseIdStr.isEmpty()) {
            request.setAttribute("error", "Please select a course.");
            doGet(request, response);
            return;
        }
        try {
            int    courseId = Integer.parseInt(courseIdStr);
            Course course   = courseDAO.getCourseById(courseId);
            if (course == null) {
                request.setAttribute("error", "Course not found.");
                doGet(request, response);
                return;
            }
            Date   regDate      = new Date(System.currentTimeMillis());
            RegistrationView reg = new RegistrationView(
                    0, studentId, courseId, regDate, "PENDING", "");
            int regId = registrationDAO.addRegistration(reg);
            if (regId == -1) {
                request.setAttribute("error", "Registration failed.");
                doGet(request, response);
                return;
            }

            // Gửi mail xác nhận đăng ký
            User student = userDAO.getUserById(studentId);
            mylib.EmailUtils.sendEnrollmentConfirm(
                    student.getEmail(),
                    student.getFullName(),
                    course.getName(),
                    course.getTuitionFee()
            );

            if (course.getTuitionFee() <= 0) {
                response.sendRedirect(request.getContextPath() + "/myCourses");
            } else {
                response.sendRedirect(request.getContextPath()
                        + "/payment?regId=" + regId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error.");
            doGet(request, response);
        }
    }
}