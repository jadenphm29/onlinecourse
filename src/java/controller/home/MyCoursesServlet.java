package controller.home;

import dao.CourseDAO;
import dao.RegistrationDAO;
import dto.Course;
import dto.RegistrationView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyCoursesServlet extends HttpServlet {

    private final CourseDAO       courseDAO       = new CourseDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private static final int PAGE_SIZE = 15;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String  role   = (String)  session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");

        int currentPage = 1;
        try {
            String p = request.getParameter("page");
            if (p != null && !p.trim().isEmpty())
                currentPage = Integer.parseInt(p);
        } catch (NumberFormatException ignored) {}

        if ("STUDENT".equalsIgnoreCase(role)) {
            List<Course> allCourses = courseDAO.getStudentCourses(userId);
            ArrayList<RegistrationView> myRegs =
                    registrationDAO.getRegistrationsByStudent(userId);

            int totalPages = Math.max(1, (int) Math.ceil((double) allCourses.size() / PAGE_SIZE));
            currentPage = Math.max(1, Math.min(currentPage, totalPages));

            int from = (currentPage - 1) * PAGE_SIZE;
            int to   = Math.min(from + PAGE_SIZE, allCourses.size());

            request.setAttribute("myCourses",    allCourses);
            request.setAttribute("pagedCourses", allCourses.subList(from, to));
            request.setAttribute("myRegs",       myRegs);
            request.setAttribute("totalPages",   totalPages);
            request.setAttribute("currentPage",  currentPage);

        } else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
            List<Course> allCourses = courseDAO.getInstructorCourses(userId);

            int totalPages = Math.max(1, (int) Math.ceil((double) allCourses.size() / PAGE_SIZE));
            currentPage = Math.max(1, Math.min(currentPage, totalPages));

            int from = (currentPage - 1) * PAGE_SIZE;
            int to   = Math.min(from + PAGE_SIZE, allCourses.size());

            request.setAttribute("myCourses",    allCourses);
            request.setAttribute("pagedCourses", allCourses.subList(from, to));
            request.setAttribute("totalPages",   totalPages);
            request.setAttribute("currentPage",  currentPage);
        }

        request.getRequestDispatcher("jsp/shared/myCourses.jsp").forward(request, response);
    }
}