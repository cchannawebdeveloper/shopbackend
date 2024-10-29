function clearFilter() {
	window.location = moduleURL;
}

function showDeleteComfirmModel(link, entityName) {

	console.log('showDeleteComfirmModel work!!!!!!!!!!!!!!!!!');
	entityId = link.attr("entityId");
	
	console.log('showDeleteComfirmModel work!!!!!!!!!!!!!!!!!entityId::'+entityId);
	
	$("#yesButton").attr("href", link.attr("href"));
	$("#comfirmText").text("Are you sure you want to delete this "
			+ entityName +" ID "+ entityId +" ?");
	$("#comfirmModel").modal();
	
}