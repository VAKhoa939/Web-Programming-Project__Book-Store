<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>
<c:if test="${message!=null||!message.isBlank()}">
	<p>${message}</p>
</c:if>
<div class="account-page">
	<div class="container">
		<div class="form-container">
			<div class="form-btn">
				<span onclick="login()">Login</span>
				<span onclick="register()">Register</span>
				<hr id="Indicator">
			</div>
			
			<form id="LoginForm" action="account" method="post">
				<input type="hidden" name="action" value="login">
				<input type="email" placeholder="Email" name="email" value="<c:out value='${email}'/>"><br>
				<input type="password" placeholder="Password" name="password" value="<c:out value='${password}'/>"><br>
				<button type="submit" class="btn">Login</button>
			</form>
			<form id="RegisterForm" action="account" method="post">
				<input type="hidden" name="action" value="register">
				<input type="email" placeholder="Email" name="email" value="<c:out value='${email}'/>"><br>
				<input type="text" placeholder="User Name" name="userName" value="<c:out value='${userName}'/>"><br>
				<input type="text" placeholder="Address" name="addr" value="<c:out value='${addr}'/>"><br>
				<input type="text" placeholder="Phone Number" name="phoneNo" value="<c:out value='${phoneNo}'/>"><br>
				<input type="password" placeholder="Password" name="password" value="<c:out value='${password}'/>"><br>
				<button type="submit" class="btn">Register</button>
			</form>
		</div>
	</div>
</div>
<!-- js toggle menu -->
<script>
	var LoginForm = document.getElementById("LoginForm");
	var RegisterForm = document.getElementById("RegisterForm");
	var Indicator = document.getElementById("Indicator");
	function register() {
		RegisterForm.style.transform = "translateX(0px)";
		LoginForm.style.transform = "translateX(0px)";
		Indicator.style.transform = "translateX(100px)";
	}

	function login() {
		RegisterForm.style.transform = "translateX(300px)";
		LoginForm.style.transform = "translateX(300px)";
		Indicator.style.transform = "translateX(0px)";
	}
</script>
<%@ include file="/includes/footer.jsp" %>