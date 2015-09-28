$(function(){
	$("#selectAll").click(
			function() {
				$(".table tbody input").each(function() {
					if ($("#selectAll")[0].checked != $(this)[0].checked)
						$(this).click();
				});
	});
	$(".table").on("click","input[type=checkbox]",function(){
		$("#selectedFiles").val("");
		$(".table tbody input:checked").each(function() {
			$("#selectedFiles").val($("#selectedFiles").val()+ $(this).val() + ",");
		});
		if ($(".table tbody input:checked").length > 0) {
			$(".pull-left").children().removeAttr("disabled");
		} else {
			$(".pull-left").children().attr("disabled", "disabled");
		}
	})
});

function unSelectAll(){
	$(".table input:checkbox").each(function(){
       $(this).removeAttr("checked");			
	});
	$(".pull-left").children().attr("disabled","disabled");
	$("#selectedFiles").val("");
}