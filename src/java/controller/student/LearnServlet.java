package controller.student;

import dao.RegistrationDAO;
import dao.CourseDAO;
import dto.Course;
import dto.RegistrationView;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LearnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra đăng nhập
        String role = (String) request.getSession().getAttribute("role");
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (!"STUDENT".equals(role) || userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idParam = request.getParameter("courseId");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/myCourses");
            return;
        }

        try {
            int courseId = Integer.parseInt(idParam);

            RegistrationDAO regDAO = new RegistrationDAO();
            RegistrationView reg = regDAO.getRegistrationByStudentAndCourse(userId, courseId);

            // Chỉ cho vào nếu đã APPROVED
            if (reg == null || !"APPROVED".equals(reg.getStatus())) {
                response.sendRedirect(request.getContextPath() + "/myCourses");
                return;
            }

            CourseDAO courseDAO = new CourseDAO();
            Course course = courseDAO.getCourseById(courseId);

            if (course == null) {
                response.sendRedirect(request.getContextPath() + "/myCourses");
                return;
            }

            request.setAttribute("course", course);
            request.setAttribute("reg", reg);
            request.getRequestDispatcher("/jsp/student/learn.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/myCourses");
        }
    }
}