package controller.admin;

import dao.CourseDAO;
import dao.RegistrationDAO;
import dao.UserDAO;
import dto.RegistrationView;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class StatisticsServlet extends HttpServlet {

    private final UserDAO         userDAO         = new UserDAO();
    private final CourseDAO       courseDAO       = new CourseDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int totalStudents      = userDAO.countByRole("STUDENT");
        int totalInstructors   = userDAO.countByRole("INSTRUCTOR");
        int totalCourses       = courseDAO.countAllCourses();
        int totalRegistrations = registrationDAO.countAllRegistrations();

        // Số đăng ký theo từng khóa học
        List<RegistrationView> perCourse = registrationDAO.getRegistrationsPerCourse();

        request.setAttribute("totalStudents",      totalStudents);
        request.setAttribute("totalInstructors",   totalInstructors);
        request.setAttribute("totalCourses",       totalCourses);
        request.setAttribute("totalRegistrations", totalRegistrations);
        request.setAttribute("perCourse",          perCourse);

        request.getRequestDispatcher("/jsp/admin/statistics.jsp")
               .forward(request, response);
    }
}