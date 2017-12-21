$('.list-group-item').on('click', function() {
	var $this = $(this);
	var $alias = $this.data('alias');

	$('.active').removeClass('active');
	$this.toggleClass('active');
})

function creaCardProduct(json) {
	var div = jQuery('<div></div>').attr("class", "col-lg-4 col-md-6 mb-4")
			.attr("id", json.id);
	var card = jQuery("<div></div>").attr("class", "card h-100");
	var pic;
	switch(json.priorite) {
		case "Forte":
			pic = jQuery('<a href="#"><img class="card-img-top" src="image/high-priority-96.png" style="width:96px" alt=""/></a>');
			break;
		case "Moyenne":
			pic = jQuery('<a href="#"><img class="card-img-top" src="image/middle-priority-96.png" style="width:96px" alt=""/></a>');
			break;
		case "Faible":
			pic = jQuery('<a href="#"><img class="card-img-top" src="image/low-priority-96.png" style="width:96px" alt=""/></a>');
			break;
	
	} 
	var cardBody = jQuery('<div class="card-body"><h4 class="card-title"><a href="/productpage?item='+json.id+'">'
			+ json.message
			+ '</a> </h4><p class="card-text"> '
			+ ' Echéance : '
			+ json.echeance
			+ '</p> </div>');
	var cardFooter = jQuery('<div class="card-footer"><small class="text-muted">'+json.date+' </small></div>');
	var btnRemove = jQuery('<button name="buttonRemove" type="button" onclick="removeProduct(\''
			+ json.id
			+ '\')" class="btn btn-danger btn-xs"><i class="fa fa-trash fa-lg" aria-hidden="true"></i></button>');
	cardFooter.append(btnRemove);
	card.append(pic);
	card.append(cardBody);
	card.append(cardFooter);
	div.append(card);
	return div;
}

function getByDate(element){
	jQuery.ajax({
		type : 'GET',
		url : 'api',
		data : {
			action : "sortByTime",
			mode : element.text()
		},
		success : function(result) {
			$('#previews').empty();
			console.log("getCategory", result);
			var json = JSON.parse(result);
			for (i = json.length; i--;) {
				console.log(i, creaCardProduct(json[i]));
				$('#previews').append(creaCardProduct(json[i]));
			}
		},
		error : function() {
			console.log('oops !');
		}
	});
}

function getCategory(element) {
	jQuery.ajax({
		type : 'GET',
		url : 'api',
		data : {
			action : "priorite",
			priorite : element.text()
		},
		success : function(result) {
			$('#previews').empty();
			console.log("getCategory", result);
			var json = JSON.parse(result);
			for (i = json.length; i--;) {
				console.log(i, creaCardProduct(json[i]));
				$('#previews').append(creaCardProduct(json[i]));
			}
		},
		error : function() {
			console.log('oops !');
		}
	});
}

function getPreviews() {

	jQuery.ajax({
		type : 'GET',
		url : 'api',
		data : {
			action : "lookup"
		},
		success : function(result) {
			$('#previews').empty();
			console.log("getPreviews", result);
			var json = JSON.parse(result);
			for (i = json.length; i--;) {
				console.log(json[i]);
				$('#previews').append(creaCardProduct(json[i]));
			}
		},
		error : function() {
			console.log('oops previews !');
		}
	});

}

function addProduct() {

	jQuery.ajax({
		type : 'GET',
		url : 'api',
		data : {
			action : "add",
			message : $("#messageinput").val(),
			echeance : $("#datepicker").val(),
			priorite : $("#priorityselect").find("option:selected").text()
		},
		success : function(result) {
			console.log(result);
			var json = JSON.parse(result);
			var productCard = creaCardProduct(json)
			$("#previews").prepend(productCard);
		},
		error : function() {
			console.log('oops !');
		}
	});
	return true;
}

function removeProduct(id) {

	jQuery.ajax({
		type : 'GET',
		url : 'api',
		data : {
			action : "remove",
			id : id
		},
		success : function(result) {
			$('#' + id).hide(300, function() {
				$('#' + id).remove();
			});
		},
		error : function() {
			console.log('oops !');
		}
	});
	return true;
}

function quit() {

	jQuery.ajax({
		type : 'GET',
		url : 'user', 
		data : { 
			action : "disconnect"
		},
		success : function(result) {
			console.log(result);
			location.reload();
		},
		error : function(){
			console.log('oops !');
		}
	});
}

$( "#datepicker" ).datepicker({
	inline: true,
	dateFormat: "dd/mm/yy"
});


getPreviews();

