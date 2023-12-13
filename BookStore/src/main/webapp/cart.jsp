<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>
<div class="small-container cart-page">
	<table>
		<tr>
			<th>Product</th>
			<th>Quantity</th>
			<th>Amount</th>
		</tr>
		<c:forEach var="item" items="${cart.getItems()}">
			<tr>
				<td>
					<div class="cart-info">
						<img src = "images/${item.getProduct().getProductCode()}.png">
						<div>
							<p><c:out value="${item.getProduct().getInfor().getBookName()}"/></p>
							<small>Price: <c:out value="${item.getProduct().getCurrencyFormat()}"/></small><br>
							<form action="cart" method="post">
								<input type="hidden" name="productCode" value="${item.getProduct().getProductCode()}">
								<input type="hidden" name="action" value="remove">
								<button class="btn" type="submit">Remove</button>
							</form>
						</div>
					</div>
				</td>
				<td>
					<form action="cart" method="post">
						<input type="hidden" name="action" value="change">
						<input type="hidden" name="productCode" value="${item.getProduct().getProductCode()}">
						<input type="number" name="quantity" value="<c:out value="${item.getQuantity()}"/>">
						<button class="btn" type="submit">Change</button>
					</form>
				</td>
				<td><c:out value="${item.getTotalCurrencyFormat()}"/></td>
			</tr>
		</c:forEach>
		<tr></tr>
		<tr>
			<td><b>Total</b></td>
			<td></td>
			<td><c:out value="${cart.getTotalCurrencyFormat()}"/></td>
		</tr>
	</table>
	<form action="invoice" method="post">
		<input type="hidden" name="action" value="checkout">
		<input type="hidden" name="cart" value="${cart}">
		<button class="btn" type="submit">Check out</button>
	</form>
</div>
<%@ include file="/includes/footer.jsp" %>