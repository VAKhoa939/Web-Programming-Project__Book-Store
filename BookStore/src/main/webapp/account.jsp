<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>

<div class = "user-page">
	<div class= "user-box">
		<div class= "col1">
			<img src="images/none_avatar.jpg" width = 50% class="default-pic">
		</div>
		<div class = "col2">
			<h1>User's Information</h1>
			<div class = "info-box">
				<label><b>- User Name:</b></label>
				<span><c:out value='${sessionScope.user.userName}'/></span><br>
				
				<label><b>- Email:</b></label>
				<span><c:out value='${sessionScope.user.email}'/></span><br>
						
				<label><b>- Address:</b></label>
				<span><c:out value='${sessionScope.user.addr}'/></span><br>
				
				<label><b>- Phone Number:</b></label>
				<span><c:out value='${sessionScope.user.phoneNo}'/></span><br>
			</div>
			
			<form action="account" method="post">
				<input type="hidden" name="action" value="logout">
				<input type="hidden" name="csrfToken" value="${csrfToken}">
				<button type="submit" value="Log out">Logout</button>
			</form>
		</div>
	</div>
</div>
<%@ include file="/includes/footer.jsp" %>