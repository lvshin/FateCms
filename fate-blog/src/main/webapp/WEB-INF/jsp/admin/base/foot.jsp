<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="http://open.reinforce.cn/jquery/1.11.3/jquery.min.js"></script>
<script src="http://open.reinforce.cn/poshytip/1.2/jquery.poshytip.min.js"></script>
<script src="http://open.reinforce.cn/Bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="http://open.reinforce.cn/jquery/jquery.md5.min.js"></script>
<script src="http://open.reinforce.cn/jquery/jquery.form.min.js"></script>
<script src="http://open.reinforce.cn/artdialog/6.0.4/js/dialog-plus-min.js"></script>
<script src="http://open.reinforce.cn/toastr/js/toastr.min.js"></script>
<script src="js/base/admin.min.js"></script>
<script type="text/javascript">
//初始化toastr设置 
toastr.options.escapeHtml = true;
toastr.options.closeButton = true;
toastr.options.progressBar = true;
	$(function(){
		$(".navbar-main").children("li:eq("+$(".navbar")[0].dataset.header+")").addClass("active");
		if($(".reinforce-sidebar").length!=0)
			$(".reinforce-menu>li:eq("+$(".reinforce-sidebar")[0].dataset.left+")").addClass("active").children("ul").show();
		$(".reinforce-sidebar .reinforce-menu").on("click","a",function(){
			if($(this).next().length>0){
				$(this).next().slideToggle();
				if($(this).parent().hasClass("active")){
					$(this).parent().removeClass("active");
					$(this).children(".pull-right").removeClass("fa-chevron-down").addClass("fa-chevron-right");
				}else{
					$(this).parent().addClass("active").siblings().removeClass("active").children("ul").slideUp();
					$(this).parent().siblings().children("a").children(".pull-right").removeClass("fa-chevron-down").addClass("fa-chevron-right");
					$(this).children(".pull-right").removeClass("fa-chevron-right").addClass("fa-chevron-down");
				}
			}
		});
			
		$(".reinforce-sidebar .reinforce-menu>li").each(function(){
			if($(this).children("ul").length>0){
				$(this).children("a").append("<i class='fa fa-chevron-right pull-right'></i>");
			}
		});
	});
</script>
