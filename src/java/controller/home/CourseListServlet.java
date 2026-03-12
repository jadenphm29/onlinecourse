package controller.home;

import dao.CourseDAO;
import dao.RegistrationDAO;
import dto.Course;
import dto.RegistrationView;
import dto.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class CourseListServlet extends HttpServlet {

    private final CourseDAO       courseDAO       = new CourseDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    private static final int PAGE_SIZE = 15;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ── Lấy toàn bộ courses ──
        List<Course> allCourses = courseDAO.getAllCourse();
        int totalCourses = allCourses.size();
        int totalPages   = (int) Math.ceil((double) totalCourses / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;

        // ── Parse page param ──
        int currentPage = 1;
        try {
            String p = request.getParameter("page");
            if (p != null && !p.trim().isEmpty())
                currentPage = Integer.parseInt(p);
        } catch (NumberFormatException ignored) {}

        if (currentPage < 1)          currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        // ── Cắt danh sách theo trang ──
        int from = (currentPage - 1) * PAGE_SIZE;
        int to   = Math.min(from + PAGE_SIZE, totalCourses);
        List<Course> pagedCourses = allCourses.subList(from, to);

        // ── Registrations của student ──
        String role = (String) session.getAttribute("role");
        if ("STUDENT".equals(role)) {
            User user = (User) session.getAttribute("user");
            ArrayList<RegistrationView> myRegistrations =
                    registrationDAO.getRegistrationsByStudent(user.getUserId());
            request.setAttribute("myRegistrations", myRegistrations);
        }

        request.setAttribute("pagedCourses", pagedCourses);
        request.setAttribute("totalCourses", totalCourses);
        request.setAttribute("totalPages",   totalPages);
        request.setAttribute("currentPage",  currentPage);

        request.getRequestDispatcher("jsp/shared/courseList.jsp").forward(request, response);
    }
}