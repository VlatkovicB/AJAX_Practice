<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Prototip</title>
<link rel="stylesheet" href="css/style.css">
</head>
<script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
<body>

	<div class="main clearfix">
		<div id="sidebar">
			<ul>
				<li>
					<button id="1" onclick="displayCategory(this.id)">Category
						1</button>
				</li>
				<li>
					<button id="2" onclick="displayCategory(this.id)">Category
						2</button>
				</li>
				<li>
					<button id="3" onclick="displayCategory(this.id)">Category
						3</button>
				</li>
				<li>
					<button onclick="getProducts()">View All</button>
				</li>
			</ul>
		</div>
		<ul id="content"></ul>
	</div>
	<form id="insert" action="TestServlet" method="POST">
		<div id="new_entry">
			<label for="name">Name:</label> <input id="name" type="text"
				name="name" value=""> <input type="hidden" name="id"
				value="" id="id_product"> <label for="description">Description:</label>
			<input id="description" type="text" name="description" value="">
			<select id="select" name="category">
				<option value="1">Category1</option>
				<option value="2">Category2</option>
				<option value="3">Category3</option>
			</select>
			<button id="submit" type="button" name="button"
				onclick="addProduct()">Save</button>
		</div>
	</form>
	<script src="js/javascript.js" charset="utf-8"></script>
</body>
</html>
