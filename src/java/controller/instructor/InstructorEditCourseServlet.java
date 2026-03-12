package controller.instructor;

import dao.CourseDAO;
import dto.Course;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

public class InstructorEditCourseServlet extends HttpServlet {

    private final CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"INSTRUCTOR".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            Course course = courseDAO.getCourseById(courseId);
            if (course == null) {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }
            request.setAttribute("course", course);
            request.getRequestDispatcher("/jsp/instructor/edit-course.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"INSTRUCTOR".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            int instructorId = (int) session.getAttribute("userId");
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double tuitionFee = Double.parseDouble(request.getParameter("tuitionFee"));
            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));
            String scheduleText = request.getParameter("scheduleText");
            int totalSlots = Integer.parseInt(request.getParameter("totalSlots"));
            String status = request.getParameter("status");
            String thumbnailUrl = request.getParameter("thumbnailUrl");
            String videoUrl = request.getParameter("videoUrl");
            String detailDescription = request.getParameter("detailDescription");

            Course course = new Course(
                    courseId, name, description,
                    tuitionFee, startDate, endDate,
                    scheduleText, instructorId
            );
            course.setTotalSlots(totalSlots);
            course.setStatus(status);
            course.setThumbnailUrl(thumbnailUrl);
            course.setVideoUrl(videoUrl);
            course.setDetailDescription(detailDescription);

            boolean success = courseDAO.updateCourseByInstructor(course, instructorId);
            if (!success) {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/myCourses");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
