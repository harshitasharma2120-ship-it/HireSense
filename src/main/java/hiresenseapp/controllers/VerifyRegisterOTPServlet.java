package hiresenseapp.controllers;

import java.io.IOException;

import hiresenseapp.dao.UserDao;
import hiresenseapp.pojo.UserPojo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VerifyRegisterOTPServlet")
public class VerifyRegisterOTPServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String inputOtp = req.getParameter("otp");
		String actualOtp = (String) session.getAttribute("regOTP");

		if (actualOtp != null && actualOtp.equals(inputOtp != null ? inputOtp.trim() : null)) {
			// OTP is valid -> Register the user
			String name = (String) session.getAttribute("regName");
			String email = (String) session.getAttribute("regEmail");
			String password = (String) session.getAttribute("regPassword");
			String role = (String) session.getAttribute("regRole");

			try {
				UserPojo user = new UserPojo(0, name, email, password, role, "active", null);
				UserDao.registerUser(user);
				session.removeAttribute("regOTP");
				session.removeAttribute("regName");
				session.removeAttribute("regEmail");
				session.removeAttribute("regPassword");
				session.removeAttribute("regRole");
				res.sendRedirect("login.jsp?registered=true");
			} catch (Exception e) {
				throw new ServletException("Registration failed", e);
			}
		} else {
			res.sendRedirect("register.jsp?showOtp=true&error=invalid");
		}
	}
}
