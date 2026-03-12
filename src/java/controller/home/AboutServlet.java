package controller.home;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/about")  // Ánh xạ URL /about cho servlet này
public class AboutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển tiếp yêu cầu tới trang about.jsp
        request.getRequestDispatcher("/about.jsp").forward(request, response);
    }
}
