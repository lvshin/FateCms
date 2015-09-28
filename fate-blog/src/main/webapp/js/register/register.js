	var email = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
	var mobile =/^1[3|4|5|8][0-9]\d{8}$/;
	$(function(){
	$("#loginName").focus(function(){
	  $(".format_error").css("display","none");
	  $(".loginName_not_null").css("display","none");
	  $(".exist_error").css("display","none");
	  $(".exist_success").css("display","none");
	});
	
	$("#nickName").focus(function(){
	  $(".nickName_not_null").css("display","none");
	});
	
	$("#pwd").focus(function(){
	  $(".pwd_not_null").css("display","none");
	});
	
	$("#pwdcfm").focus(function(){
	  $(".pwdcfm_not_null").css("display","none");
	});
	
	$("#loginName").blur(function(){
	var loginName = $("#loginName").val();
	if(email.test(loginName)||mobile.test(loginName)){
	  $(".spinner").css("display","inline-block");
	  $.ajax({
			type : "post",
			url : "op/register/checkLoginName",
			dataType : 'json',
			data : {
				loginName : loginName,
			},
			error : function() {
				alert("ͨ通信异常");
			},
			success : function(data) {
			 $(".spinner").css("display","none");
			  if(data.success){
			    $(".exist_error").css("display","inline-block");
			    $("#loginsubmit").addClass("disable");
			    $("#loginsubmit").attr("disabled",true);
			  }
			  else{
			    $(".exist_success").css("display","inline-block");
			    $("#loginsubmit").removeClass("disable");
			    $("#loginsubmit").attr("disabled",false);
			  }
			}
		});
	}	
	else
	{
	  $(".format_error").css("display","inline-block");
	  $("#loginsubmit").addClass("disable");
	  $("#loginsubmit").attr("disabled",true);
	}
	});
	
	$("#pwdcfm").blur(function(){
	   if($(this).val()!=""&&$("#pwd").val()!=""){
	      if($(this).val()!=$("#pwd").val()){
	         $(".pwd_error").css("display","inline-block");
	         $("#loginsubmit").addClass("disable");
	         $("#loginsubmit").attr("disabled",true);
	      }else{
	         $(".pwd_error").css("display","none");
			 $("#loginsubmit").removeClass("disable");
			 $("#loginsubmit").attr("disabled",false);
	      }
	   }
	});
	
	$("#pwd").blur(function(){
		if($(this).val().length<$(this).attr("minlength")){
		         $(".pwd_min_length_error").css("display","inline-block");
		         $("#loginsubmit").addClass("disable");
		         $("#loginsubmit").attr("disabled",true);
		      }else{
		         $(".pwd_min_length_error").css("display","none");
				 $("#loginsubmit").removeClass("disable");
				 $("#loginsubmit").attr("disabled",false);
		      }
			
		if($(this).val()!=""&&$("#pwd").val()!=""){
	      if($(this).val()!=$("#pwd").val()){
	         $(".pwd_error").css("display","inline-block");
	         $("#loginsubmit").addClass("disable");
	         $("#loginsubmit").attr("disabled",true);
	      }else{
	         $(".pwd_error").css("display","none");
			 $("#loginsubmit").removeClass("disable");
			 $("#loginsubmit").attr("disabled",false);
	      }
	   }
	});
	
	});
	function register() {
	  if($("#nickName").val()!=""&&$("#loginName").val()!=""&&$("#pwd").val()!=""&&$("#pwdcfm").val()!="")
		$.ajax({
			type : "post",
			url : "op/register/submit",
			dataType : 'json',
			data : {
				nickName : $("#nickName").val(),
				loginName : $("#loginName").val(),
				pwd : $.md5($("#pwd").val())
			},
			error : function() {
				alert("ͨ通信异常1");
			},
			success : function(data) {
				if(data.success){
					var d = dialog({
						content : data.message
					});
				}
				location.href = data.url;
			}
		});
		else
		{
		   if($("#nickName").val()==""){
		     $(".nickName_not_null").css("display","inline-block");
	         $("#loginsubmit").addClass("disable");
	         $("#loginsubmit").attr("disabled",true);
		   }
		   if($("#loginName").val()==""){
		     $(".loginName_not_null").css("display","inline-block");
	         $("#loginsubmit").addClass("disable");
	         $("#loginsubmit").attr("disabled",true);
		   }
		   if($("#pwd").val()==""){
		     $(".pwd_not_null").css("display","inline-block");
	         $("#loginsubmit").addClass("disable");
	         $("#loginsubmit").attr("disabled",true);
		   }
		   if($("#pwdcfm").val()==""){
		     $(".pwdcfm_not_null").css("display","inline-block");
	         $("#loginsubmit").addClass("disable");
	         $("#loginsubmit").attr("disabled",true);
		   }
		}
	}