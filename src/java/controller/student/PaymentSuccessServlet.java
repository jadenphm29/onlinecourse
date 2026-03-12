package controller.student;

import dao.CourseDAO;
import dao.RegistrationDAO;
import dto.Course;
import dto.RegistrationView;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PaymentSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String regIdStr = request.getParameter("regId");
        if (regIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/myCourses");
            return;
        }

        RegistrationDAO registrationDAO = new RegistrationDAO();
        CourseDAO       courseDAO       = new CourseDAO();

        RegistrationView reg = registrationDAO.getRegistrationById(
                Integer.parseInt(regIdStr));
        Course course = reg != null
                ? courseDAO.getCourseById(reg.getCourseId()) : null;

        request.setAttribute("course", course);
        request.getRequestDispatcher("jsp/student/payment-success.jsp").forward(request, response);
    }
}