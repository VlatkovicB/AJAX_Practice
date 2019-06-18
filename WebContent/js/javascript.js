var content = $('#content');

function getProducts() {
	content.empty();
	$.ajax({
		type : "GET",
		url : "TestServlet",
		success : function(data) {
			displayData(data);
		},
		error : function(data) {
			console.log(data);
		}
	});
}

function addProduct() {
	var form = $('#insert');
	var url = form.attr('action');
	var method = form.attr('method');

	var data = form.serialize();

	console.log(data);
	$.ajax({
		type : method,
		url : url,
		data : data,
		success : function(response) {
			getProducts();
			form[0].reset();
		},
		error : function(data) {
			console.log(data);
		}
	});
}

function deleteProduct(id) {
	$.ajax({
		type : 'DELETE',
		url : 'TestServlet?id=' + id,
		success : function(response) {
			getProducts();
		},
		error : function(data) {
			console.log(data);
		}
	});
}

function editProduct(productId, productName, productDecription, productCategory) {
	var name = $('#name');
	var description = $('#description');
	var select = $('#select');
	var button = $('#submit');
	var id = $('#id_product');

	id.val(productId);
	name.val(productName);
	description.val(productDecription);
	select.val(productCategory).change();
	button.html('Update');
	button.attr("onclick", "updateProduct()");
}

function resetButton() {
	var button = $('#submit');

	button.html('Save');
	button.attr("onclick", "addProduct()");
}

function updateProduct() {

	var form = $('#insert');
	// var data = form.serialize();

	var name = $('#name').val();
	var description = $('#description').val();
	var category = $('#select').val();
	var id = $('#id_product').val();

	var data = {
		'name' : name,
		'description' : description,
		'id' : id,
		'category' : category
	}

	$.ajax({
		type : 'PUT',
		url : 'TestServlet',
		data : JSON.stringify(data),
		// url : 'TestServlet?id=' + id + '&' + data,
		success : function(response) {
			getProducts();
			resetButton();
			form[0].reset();
		},
		error : function(data) {
			console.log(data);
		}
	});
}

function displayCategory(id) {

	content.empty();

	$.ajax({
		type : 'GET',
		url : 'CategoryServlet?id=' + id,
		success : function(data) {
			displayData(data);
		},
		error : function(data) {
		}
	})
}

function displayData(data) {
	for (var i = 0; i < data.length; i++) {
		content
				.append('<li class="product clearfix"><div id="left"><h3>'
						+ data[i].name
						+ '</h3> <p>'
						+ data[i].description
						+ '</p> <h3>'
						+ data[i].category
						+ '</h3></div><div id="right"><button class="btn" type="button"name="button" onclick="editProduct('
						+ '\''
						+ data[i].id
						+ '\',\''
						+ data[i].name
						+ '\',\''
						+ data[i].description
						+ '\',\''
						+ data[i].category
						+ '\''
						+ ')">Edit</button><button class="btn" onclick="deleteProduct('
						+ data[i].id
						+ ')" type="button" name="button">Delete</button></div></li>');
	}
}
