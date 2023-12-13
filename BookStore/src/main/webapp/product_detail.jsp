<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>
<c:if test="${message!=null||!message.isBlank()}">
	<p>${message}</p>
</c:if>
<div class="small-container single-product">
	<div class="row">
		<div class="col2">
			<img src="images/${product.getProductCode()}.png" width=100% id="ProductImg">
		</div>
		<div class="col2">
			<h1>${product.getInfor().getBookName()}</h1>
			<h4>${product.getCurrencyFormat()}</h4>
			<form action="cart" method="post">
				<input type="hidden" name="action" value="new">
				<input type="hidden" name="productCode" value="${product.getProductCode()}">
				<input type="hidden" name="addQuantity" value="1">
				<button class="btn" type="submit">Add to cart</button>
			</form>
			<h3>Author:</h3>
			<p>${product.getInfor().getAuthor()}</p>
			<h3>Description:</h3>
			<p>${product.getInfor().getDetail()}</p>
		</div>
	</div>
</div>
<%@ include file="/includes/footer.jsp" %>