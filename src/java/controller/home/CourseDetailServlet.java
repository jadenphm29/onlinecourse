package controller.home;

import dao.CourseDAO;
import dao.RegistrationDAO;
import dto.Course;
import dto.RegistrationView;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CourseDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("courseId");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/courseList");
            return;
        }

        try {
            int courseId = Integer.parseInt(idParam);
            CourseDAO courseDAO = new CourseDAO();
            Course course = courseDAO.getCourseById(courseId);

            if (course == null) {
                response.sendRedirect(request.getContextPath() + "/courseList");
                return;
            }

            // Nếu là STUDENT → lấy registration của họ cho course này
            String role = (String) request.getSession().getAttribute("role");
            if ("STUDENT".equals(role)) {
                Integer userId = (Integer) request.getSession().getAttribute("userId");
                if (userId != null) {
                    RegistrationDAO regDAO = new RegistrationDAO();
                    RegistrationView myReg = regDAO.getRegistrationByStudentAndCourse(userId, courseId);
                    request.setAttribute("myReg", myReg);
                }
            }

            request.setAttribute("course", course);
            request.getRequestDispatcher("/jsp/shared/courseDetail.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/courseList");
        }
    }
}