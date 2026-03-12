package controller.admin;

import dao.CourseDAO;
import dao.UserDAO;
import dto.Course;
import dto.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class AdminAddCourseServlet extends HttpServlet {

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

        List<User> instructors = userDAO.getUsersByRole("INSTRUCTOR");
        request.setAttribute("instructors", instructors);
        request.getRequestDispatcher("/jsp/admin/addCourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Course c = new Course();
            c.setName(request.getParameter("name"));
            c.setDescription(request.getParameter("description"));
            c.setTuitionFee(Double.parseDouble(request.getParameter("tuitionFee")));
            c.setStartDate(Date.valueOf(request.getParameter("startDate")));
            c.setEndDate(Date.valueOf(request.getParameter("endDate")));
            c.setScheduleText(request.getParameter("scheduleText"));
            c.setTotalSlots(Integer.parseInt(request.getParameter("totalSlots")));
            c.setStatus(request.getParameter("status"));

            String instrParam = request.getParameter("instructorId");
            if (instrParam != null && !instrParam.trim().isEmpty()) {
                c.setInstructorId(Integer.parseInt(instrParam));
            } else {
                c.setInstructorId(null);
            }

            courseDAO.addCourse(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/manageCourses");
    }
}