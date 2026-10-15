package hiresenseapp.controllers;


import java.io.IOException;

import hiresenseapp.dao.JobDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ToggleJobStatusServlet")
public class ToggleJobStatusServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"employer".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        int jobId = Integer.parseInt(request.getParameter("jobId"));
        try {
            JobDao.toggleJobStatus(jobId);
            response.sendRedirect("employerDashboard");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("employerDashboard?error=1");
        }
    }
}

