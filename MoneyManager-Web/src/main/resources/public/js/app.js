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
	$(".menu-link").click(function(e) {
		$("#ui-view").load(e.currentTarget.getAttribute("data-link"), function() {
			$('#datatable').DataTable();
			$("#add-new-customer").click(function() {
				$("#div-data-customer").load('/customer/add')
			});
		});
	});
});