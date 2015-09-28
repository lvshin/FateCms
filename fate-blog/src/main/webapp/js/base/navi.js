
$(function(){
	$(".primary_navi:eq(${param.index-1})").children("a").addClass("click_style").css("color","#fff");
	$(".primary_navi:eq(${param.index-1})").children("a").children(".liarrow").children("img").attr("src","images/arrow_up.png");
	$(".primary_navi:eq(${param.index-1})").children("ul").show();
	$(".primary_navi").click(function(){
		$(this).children("a").addClass("click_style");
		$(this).siblings().children("a").removeClass("click_style");
		$(this).children("a").css("color","#fff");
		$(this).siblings().children("a").css("color","#000");
		$(this).children("ul").slideDown();
		$(this).siblings().children("ul").slideUp();
		$(this).children("a").children(".liarrow").children("img").attr("src","images/arrow_up.png");
		$(this).siblings().children("a").children(".liarrow").children("img").attr("src","images/arrow_down.png");
	});
});