dialog.alert = function(content){
	var d = dialog({
		title : '提示',
		content : content,
		width : '200px',
		okValue : '确定',
		ok : function(){}
	});
	d.showModal();
}