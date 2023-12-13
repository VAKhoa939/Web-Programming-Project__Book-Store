<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>
<div class = "wrapper">
	<div class ="invoice-header">
		<div class = "logo">
			<img src="images/pngLogo.png" width="125">
		</div>
		<div class ="title">
			Your Invoice
		</div>
	</div>	
	<label><b>Invoice number: </b></label>
	<c:out value="${invoice.getInvoiceNum()}"/><br>
	
	<label><b>Date created: </b></label>
	<c:out value="${invoice.getInvoiceDate()}"/><br>
	
	<label><b>Estimate date arrival: </b></label>
	<c:out value="${invoice.getEstArrivalDate()}"/><br>
	
	<label><b>Ship to: </b></label>
	<c:out value="${invoice.getDeliveryAddr()}"/><br>
	
	<label><b>Cart: </b></label><br>
		<table>
		<tr>
			<th>Product</th>
			<th>Quantity</th>
			<th>Amount</th>
		</tr>
		<c:forEach var="item" items="${invoice.getCart().getItems()}">
			<tr>
				<td>
					<div class="cart-info">
						<div>
							<p><c:out value="${item.getProduct().getInfor().getBookName()}"/></p>
							<small>Price: <c:out value="${item.getProduct().getCurrencyFormat()}"/></small><br>
						</div>
					</div>
				</td>
				<td><c:out value="${item.getQuantity()}"/></td>
				<td><c:out value="${item.getTotalCurrencyFormat()}"/></td>
			</tr>
		</c:forEach>
		<tr></tr>
		<tr>
			<td><b>Total</b></td>
			<td></td>
			<td><c:out value="${invoice.getCart().getTotalCurrencyFormat()}"/></td>
		</tr>
	</table>
</div>
<%@ include file="/includes/footer.jsp" %>