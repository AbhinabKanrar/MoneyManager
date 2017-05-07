$(document).ready(function() {
	var navsidebar = document.getElementById("navsidebar");
	var navoverlay = document.getElementById("navoverlay");
	$("#nav-menu-button").click(function() {
		if (navsidebar.style.display === 'block') {
			navsidebar.style.display = 'none';
			navoverlay.style.display = "none";
		} else {
			navsidebar.style.display = 'block';
			navoverlay.style.display = "block";
		}
	});
	$(".nav-menu-button-close").click(function() {
		navsidebar.style.display = "none";
		navoverlay.style.display = "none";
	});
	$('#datatable').DataTable({
		paging : false
	});
	$("#customer-region").change(function() {
		$.ajax({
			  url: "/customer/byregion/"+$("#customer-region option:selected").text(),
			  method: "post",
			  dataType: "json",
			  beforeSend: function(request) {
				    request.setRequestHeader("X-CSRF-TOKEN", $('input[name=_csrf]').val());
			  }
			}).done(function(data) {
				console.log(data)
				alert(data)
			}).fail(function(err) {
				console.log(err)
			});
	});
});