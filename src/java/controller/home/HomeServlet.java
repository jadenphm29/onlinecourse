package controller.home;

import dao.CourseDAO;
import dao.RegistrationDAO;
import dao.UserDAO;
import dto.Course;
import dto.RegistrationView;
import dto.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class HomeServlet extends HttpServlet {

    private final CourseDAO       courseDAO       = new CourseDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final UserDAO         userDAO         = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User   user = null;
        String role = null;

        if (session != null) {
            user = (User)   session.getAttribute("user");
            role = (String) session.getAttribute("role");
        }

        // ── GUEST ────────────────────────────────────────────────
        if (user == null) {
            request.setAttribute("courses", courseDAO.getAllCourse());
        }

        // ── STUDENT ──────────────────────────────────────────────
        // Feature 1: course.status badge populated inside getAllCourse()
        // Feature 2: myRegistrations for "Already Enrolled" check
        else if ("STUDENT".equals(role)) {
            request.setAttribute("courses", courseDAO.getAllCourse());

            // Feature 2 — pass to home.jsp & courseList.jsp
            ArrayList<RegistrationView> myRegistrations =
                    registrationDAO.getRegistrationsByStudent(user.getUserId());
            request.setAttribute("myRegistrations", myRegistrations);

            // keep existing attribute name your JSP already uses
            request.setAttribute("registrations", myRegistrations);
        }

        // ── INSTRUCTOR ───────────────────────────────────────────
        // Feature 1: user.status badge from session user object
        // Feature 3: only own courses + stats + student table
        else if ("INSTRUCTOR".equals(role)) {
            int instructorId = user.getUserId();

            // Feature 3 — only this instructor's courses
            List<Course> courses = courseDAO.getInstructorCourses(instructorId);
            request.setAttribute("courses", courses);

            // Feature 3 — stat boxes
            request.setAttribute("instructorTotalCourses",
                    courses.size());
            request.setAttribute("instructorTotalStudents",
                    registrationDAO.countUniqueStudentsByInstructor(instructorId));
            request.setAttribute("instructorPendingCount",
                    registrationDAO.countByInstructorAndStatus(instructorId, "PENDING"));
            request.setAttribute("instructorApprovedCount",
                    registrationDAO.countByInstructorAndStatus(instructorId, "APPROVED"));

            // Feature 3 — student list table
            ArrayList<RegistrationView> studentRows =
                    registrationDAO.getStudentRowsByInstructor(instructorId);
            request.setAttribute("instructorStudents", studentRows);
        }

        // ── ADMIN ────────────────────────────────────────────────
        else if ("ADMIN".equals(role)) {
            // existing logic unchanged
            int totalStudents      = userDAO.countByRole("STUDENT");
            int totalInstructors   = userDAO.countByRole("INSTRUCTOR"); 
            int totalCourses       = courseDAO.countAllCourses();   
            int totalRegistrations = registrationDAO.getAllRegistrations().size();
            request.setAttribute("totalStudents",      totalStudents);
            request.setAttribute("totalCourses",       totalCourses);
            request.setAttribute("totalInstructors",   totalInstructors);
            request.setAttribute("totalRegistrations", totalRegistrations);
        }

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}