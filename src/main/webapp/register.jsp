<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register | <%=application.getAttribute("appName")%></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
</head>
<body>

<%@include file="includes/header.jsp" %>

<div class="login-container">
	<div class="login-card shadow">

		<% boolean showOtp = "true".equals(request.getParameter("showOtp")); %>

		<% if (!showOtp) { %>
		<h3 class="text-center mb-4">Create Account</h3>

		<form action="SendRegisterOTPServlet" method="post">
			<div class="mb-3">
				<input type="text" name="name" class="form-control"
					placeholder="Full name" required />
			</div>
			<div class="mb-3">
				<input type="email" name="email" class="form-control"
					placeholder="Email" required />
			</div>
			<div class="mb-3">
				<input type="password" name="password" class="form-control"
					placeholder="Password" minlength="6" required />
			</div>
			<div class="mb-3">
				<select name="role" class="form-select" required>
					<option value="user">Job Seeker</option>
					<option value="employer">Employer</option>
				</select>
			</div>
			<button type="submit" class="btn btn-login mt-2">Send OTP</button>
		</form>

		<div class="text-center mt-3">
			<small>Already have an account? <a href="login.jsp"
				class="text-warning">Login</a></small>
		</div>

		<% } else { %>
		<h3 class="text-center mb-4">Verify Email</h3>

		<% if ("invalid".equals(request.getParameter("error"))) { %>
			<div class="alert alert-danger text-center py-1">Invalid OTP. Please try again.</div>
		<% } %>

		<p class="text-center">We sent a 6-digit OTP to your email.</p>

		<form action="VerifyRegisterOTPServlet" method="post">
			<div class="mb-3">
				<input type="text" name="otp" class="form-control"
					placeholder="Enter OTP" maxlength="6" required />
			</div>
			<button type="submit" class="btn btn-login mt-2">Verify &amp; Register</button>
		</form>

		<div class="text-center mt-3">
			<small><a href="register.jsp" class="text-warning">Start over</a></small>
		</div>
		<% } %>

	</div>
</div>

<%@include file="includes/footer.jsp" %>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
