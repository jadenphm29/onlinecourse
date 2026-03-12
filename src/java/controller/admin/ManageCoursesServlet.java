package controller.admin;

import dao.CourseDAO;
import dao.UserDAO;
import dto.Course;
import dto.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ManageCoursesServlet extends HttpServlet {

    private final CourseDAO courseDAO = new CourseDAO();
    private final UserDAO   userDAO   = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Course> courses     = courseDAO.getAllCourse();
        List<User>   instructors = userDAO.getUsersByRole("INSTRUCTOR");

        request.setAttribute("courses",     courses);
        request.setAttribute("instructors", instructors);
        // Forward sang trang admin riêng (không dùng chung courseList.jsp)
        request.getRequestDispatcher("/jsp/admin/manageCourses.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            courseDAO.deleteCourse(courseId);
        }

        response.sendRedirect(request.getContextPath() + "/admin/manageCourses");
    }
}