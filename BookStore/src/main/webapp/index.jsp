<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>
<div class="home">
	<div class="small-container">
		<div class="row">
			<div class="col2">
				<h1>Welcome to our store!!</h1>
				<p>
					Bunch of new stuffs coming from unreliable source <br> 
					are coming along the way as you checking your cart!
				</p>
				<a href="catalog" class="btn">Explore Now &#8594</a>
			</div>
			<div class="col2">
				<img src="images/book_wallpaper.png">
			</div>
		</div>
	</div>
</div>
<%---featured categories --%>
<div class="categories">
	<h2 class="title">Featured Genres</h2>
	<div class ="small-container">
		<div class="row">
			<div class="col3">
				<img src="images/notmytype.png">
			</div>
			<div class="col3">
				<img src="images/thedarkwithin.png">
			</div>
			<div class="col3">
				<img src="images/littleliar.png">
			</div>
		</div>
	</div>
</div>
<!---featured products -->
<div class="small-container">
	<h2 class="title">Featured Products</h2>
	<div class="row">
		<div class="col4">
			<img src="images/countofmontecristo.png">
			<h4>The Count of Monte Cristo</h4>
		</div>
		<div class="col4">
			<img src="images/bloodthristy.png">
			<h4>Bloodthirsty</h4>
		</div>
		<div class="col4">
			<img src="images/themissingqueen.png">
			<h4>The Missing Queen</h4>
		</div>
		<div class="col4">
			<img src="images/excuseme.png">
			<h4>Excuse Me While I Disappear</h4>
		</div>
	</div>
</div>
<%@ include file="/includes/footer.jsp" %>