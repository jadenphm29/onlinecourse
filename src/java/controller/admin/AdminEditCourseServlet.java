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

public class AdminEditCourseServlet extends HttpServlet {

    private final CourseDAO courseDAO = new CourseDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int courseId = Integer.parseInt(request.getParameter("courseId"));
        Course course = courseDAO.getCourseById(courseId);
        List<User> instructors = userDAO.getUsersByRole("INSTRUCTOR");

        if (course == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manageCourses");
            return;
        }

        request.setAttribute("course", course);
        request.setAttribute("instructors", instructors);
        request.getRequestDispatcher("/jsp/admin/adminEditCourse.jsp").forward(request, response);
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
            c.setCourseId(Integer.parseInt(request.getParameter("courseId")));
            c.setName(request.getParameter("name"));
            c.setDescription(request.getParameter("description"));
            c.setTuitionFee(Double.parseDouble(request.getParameter("tuitionFee")));
            c.setStartDate(Date.valueOf(request.getParameter("startDate")));
            c.setEndDate(Date.valueOf(request.getParameter("endDate")));
            c.setScheduleText(request.getParameter("scheduleText"));
            c.setTotalSlots(Integer.parseInt(request.getParameter("totalSlots")));
            c.setStatus(request.getParameter("status"));
            c.setThumbnailUrl(request.getParameter("thumbnailUrl"));
            c.setVideoUrl(request.getParameter("videoUrl"));
            c.setDetailDescription(request.getParameter("detailDescription"));

            String instrParam = request.getParameter("instructorId");
            if (instrParam != null && !instrParam.trim().isEmpty()) {
                c.setInstructorId(Integer.parseInt(instrParam));
            } else {
                c.setInstructorId(null);
            }

            courseDAO.updateCourseByAdmin(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/manageCourses");
    }
}
