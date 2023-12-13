<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/includes/header.jsp" %>

<p><i><c:out value='${requestScope.message}'/></i></p>

<p><b>- List of users:</b></p>

<table>
    <tr>
        <th>User name</th>
        <th>Email</th>
        <th>Address</th>
        <th>Phone number</th>
        <th>Password</th>
    </tr>
    <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <td><c:out value='${user.userName}'/></td>
            <td><c:out value='${user.email}'/></td>
            <td><c:out value='${user.addr}'/></td>
            <td><c:out value='${user.phoneNo}'/></td>
            <td><c:out value='${user.password}'/></td>
        </tr>
    </c:forEach>
    <tr></tr>
</table>

<p><b>- List of products:</b></p>

<table>
    <tr>
        <th>Product Code</th>
        <th>Book's name</th>
        <th>Price</th>
        <th>Discount</th>
    </tr>
    <c:forEach var="product" items="${requestScope.products}">
        <tr>
            <td><c:out value='${product.productCode}'/></td>
            <td><c:out value='${product.getInfor().bookName}'/></td>
            <td><c:out value='${product.price}'/></td>
            <td><c:out value='${product.discount}'/></td>
        </tr>
    </c:forEach>
    <tr></tr>
</table>

<p><b>- List of line items:</b></p>

<table>
    <tr>
        <th>Quantity</th>
        <th>Product Code</th>
    </tr>
    <c:forEach var="lineItem" items="${requestScope.lineItems}">
        <tr>
            <td><c:out value='${lineItem.quantity}'/></td>
            <td><c:out value='${lineItem.getProduct().productCode}'/></td>
        </tr>
    </c:forEach>
    <tr></tr>
</table>

<p><b>- List of carts:</b></p>

<table>
    <tr>
        <th>User name</th>
        <th>Number of products</th>
        <th>Active</th>
    </tr>
    <c:forEach var="cart" items="${requestScope.carts}">
        <tr>
            <td><c:out value='${cart.getUser().userName}'/></td>
            <td><c:out value='${cart.getItems().size()}'/></td>
            <td><c:out value='${cart.isActive()}'/></td>
        </tr>
    </c:forEach>
    <tr></tr>
</table>

<p><b>- List of invoices:</b></p>

<table>
    <tr>
        <th>Date created</th>
        <th>Total price</th>
    </tr>
    <c:forEach var="invoice" items="${requestScope.invoices}">
        <tr>
            <td><c:out value='${invoice.invoiceDate}'/></td>
            <td><c:out value='${invoice.getCart().getTotalCurrencyFormat()}'/></td>
        </tr>
    </c:forEach>
    <tr></tr>
</table>

<p><b>- List of books:</b></p>

<table>
    <tr>
        <th>Book name</th>
        <th>Author</th>
        <th>Publisher</th>
        <th>Detail</th>
        <th>Genre</th>
        <th>Release Date</th>
    </tr>
    <c:forEach var="description" items="${requestScope.descriptions}">
        <tr>
            <td><c:out value='${description.bookName}'/></td>
            <td><c:out value='${description.author}'/></td>
            <td><c:out value='${description.publisher}'/></td>
            <td><c:out value='${description.detail}'/></td>
            <td><c:out value='${description.genre}'/></td>
            <td><c:out value='${description.releaseDate}'/></td>
        </tr>
    </c:forEach>
    <tr></tr>
</table>

<form action="bs" method="post">
    <input type="hidden" name="action" value="update">
    <p><b>- Update an user:</b></p>
    
    <label>His/Her email:</label>
    <input type="email" name="email"><br>
    
    <label>His/Her new information (leave blank for not changing):</label><br>
    <label>New user name:</label>
    <input type="text" name="userName"><br>
                
    <label>New address:</label>
    <input type="text" name="addr"><br>
    
    <label>New phone number:</label>
    <input type="text" name="phoneNo"><br>
    
    <label>New password:</label>
    <input type="text" name="password"><br>
    
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm update" class="margin_left">
</form>

<form action="bs" method="post">
    <input type="hidden" name="action" value="updateProduct">
    <p><b>- Update a product:</b></p>
    
    <label>Product code:</label>
    <input type="text" name="productCode"><br>
    
    <label>Its new information (leave blank for not changing):</label><br>
    <label>New Price:</label>
    <input type="text" name="price"><br>
    
    <label>New Discount:</label>
    <input type="text" name="discount"><br>
    
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm update" class="margin_left">
</form>

<form action="bs" method="post">   
    <p><b>- Update description:</b></p>
    <input type="hidden" name="action" value="updateDescription">
    
    <label>Book's name:</label>
    <input type="text" name="bookName"><br>
    
    <label>Its new information (leave blank for not changing):</label><br>
    <label>New Author:</label>
    <input type="text" name="author"><br>
    
    <label>New Publisher:</label>
    <input type="text" name="publisher"><br>
    
    <label>New Detail:</label>
    <input type="text" name="detail"><br>
    
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm update" class="margin_left">
</form>

<form action="bs" method="post">
    <input type="hidden" name="action" value="delete">
    <p><b>- Delete an user:</b></p>
    
    <label>His/Her email:</label>
    <input type="email" name="email"><br>
   
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm delete" class="margin_left">
</form>

<form action="bs" method="post">    
    <input type="hidden" name="action" value="deleteProduct">
    <p><b>- Delete a product:</b></p>
    
    <label>Product's code:</label>
    <input type="text" name="productCode"><br>
    
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm delete" class="margin_left">
</form>

<form action="bs" method="post">       
    <p><b>- Delete a book:</b></p>
    
    <label>Book's name:</label>
    <input type="text" name="bookName"><br>
    
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm delete" class="margin_left">
</form>

<form action="bs" method="post">
    <input type="hidden" name="action" value="insert">
    <p><b>- Insert a new user:</b></p>
    
    <label>User name:</label>
    <input type="text" name="userName"><br>
    
    <label>Email:</label>
    <input type="email" name="email"><br>
    
    <label>Address:</label>
    <input type="text" name="addr"><br>
                
    <label>Phone number:</label>
    <input type="text" name="phoneNo"><br>
    
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm insert" class="margin_left">
</form>

<form action="bs" method="post">
    <input type="hidden" name="action" value="insertProduct">
    <p><b>- Insert a new product:</b></p>
    
    <label>Product's code:</label>
    <input type="text" name="productCode"><br>
    
    <label>Book's name:</label>
    <input type="text" name="bookName"><br>
    
    <label>Price:</label>
    <input type="text" name="price"><br>
    
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm insert" class="margin_left">
</form>

<form action="bs" method="post">
	<input type="hidden" name="action" value="insertDescription">
    <p><b>- Insert new book:</b></p>
    
    <label>Book's name:</label>
    <input type="text" name="bookName"><br>
    
    <label>Author:</label>
    <input type="text" name="author"><br>
    
    <label>Publisher:</label>
    <input type="text" name="publisher"><br>
    
    <label>Detail:</label>
    <input type="text" name="detail"><br>
    
    <label>Genre:</label>
    <input type="text" name="genre"><br>
    
    <label>&nbsp;</label>    
    <input type="submit" value="Confirm insert" class="margin_left">
</form>

<%@ include file="/includes/footer.jsp" %>
