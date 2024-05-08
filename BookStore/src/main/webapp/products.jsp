<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>
<div class="small-container">
	<h2 class="title">All Products</h2>
	<div class="row">
		<c:forEach var="product" items="${products}">
			<div class="col4">
				<form action="detail" method="post">
					<input type="hidden" name="csrfToken" value="${csrfToken}">
					<input type="hidden" name="productCode" value="${product.getProductCode()}">
					<button type="submit">
						<img src="images/${product.getProductCode()}.png">
						<h4>${product.getInfor().getBookName()}</h4>
						<p>${product.getCurrencyFormat()}</p>
					</button>
				</form>
			</div>
		</c:forEach>
	</div>
</div>
<%@ include file="/includes/footer.jsp" %>