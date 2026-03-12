package controller.instructor;

import dao.RegistrationDAO;
import dto.RegistrationView;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

public class InstructorViewRegistrationsServlet extends HttpServlet {

    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"INSTRUCTOR".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int courseId = Integer.parseInt(request.getParameter("courseId"));

        // NEW: dùng method JOIN để có studentName + studentEmail
        ArrayList<RegistrationView> list =
                registrationDAO.getRegistrationsByCourseWithStudentInfo(courseId);

        request.setAttribute("regList",  list);
        request.setAttribute("courseId", courseId);
        request.getRequestDispatcher("/jsp/instructor/registrations.jsp").forward(request, response);
    }
}