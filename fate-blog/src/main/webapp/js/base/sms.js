/*验证码倒计时*/

function countDown() {
	var $send = $("#sendCode");
	var i = 120;
	$("#sendCode").addClass("gray").css({
		cursor : "default"
	}).attr("disabled", "disabled");
	var interval = setInterval(function() {
		$send.html("剩余" + i + "秒");
		i--;
		if (i < 0) {
			$("#sendCode").removeClass("gray").css({
				cursor : "pointer"
			}).removeAttr("disabled");
			$send.html("重新获取");
			clearInterval(interval);
		}
	}, 1000);
}
$(function() {

	$("#sendCode")
			.click(
					function() {
						countDown();
						if ($("#mobile").parent().next().children(
								".Validform_wrong").length == 0) {
							$.ajax({
								url : "op/sendVerificationCode",
								type : 'post',
								dataType : 'json',
								data : {
									mobile : $("#mobile").val(),
									geetest_challenge : $(".geetest_challenge").val(),
									geetest_validate : $(".geetest_validate").val(),
									geetest_seccode : $(".geetest_seccode").val()
								},
								success : function(data) {
									if (data.success) {
										var d2 = dialog({
											content : data.msg
										});
										d2.show();
										setTimeout(function() {
											d2.close().remove();
										}, 2000);
									}else{
										dialog.alert(data.msg);
									}

								},
								error : function(data) {
									alert('通信异常');
								}
							});
						} else
							dialog.alert($("#mobile").parent().next().children(
									".Validform_wrong").html());
					});

	var form = $("#mobileForm").Validform(
			{
				btnReset : "",
				tiptype : function(msg, o, cssctl) {
					switch (o.type) {
					case 1:
						$(o.obj).parent().next()
								.children(".Validform_checktip").removeClass(
										"Validform_right").removeClass(
										"Validform_wrong").html(msg);
						$(o.obj).parent().parent().removeClass("has-error")
								.removeClass("has-success");
						break;
					case 2:
						$(o.obj).parent().next()
								.children(".Validform_checktip").addClass(
										"Validform_right").html(msg);
						$(o.obj).parent().parent().removeClass("has-error")
								.addClass("has-success");
						break;
					case 3:
						$(o.obj).parent().next()
								.children(".Validform_checktip").addClass(
										"Validform_wrong").html(msg);
						$(o.obj).parent().parent().removeClass("has-success")
								.addClass("has-error");
						break;

					default:
						break;
					}

				},
				ignoreHidden : true,
				dragonfly : false,
				tipSweep : false,
				label : ".label",
				showAllError : true,
				postonce : true,
				datatype : {
					"code" : /^\d{6}$/
				},
				usePlugin : {},
				beforeCheck : function(curform) {
					// 在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
					// 这里明确return false的话将不会继续执行验证操作;
				},
				beforeSubmit : function(curform) {
					// 在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
					// 这里明确return false的话表单将不会提交;
				},
				callback : function(data) {
					// 返回数据data是json对象，{"info":"demo info","status":"y"}
					// info: 输出提示信息;
					// status:
					// 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
					// 你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
					// ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**,
					// readyState:**, responseText:** }；

					// 这里执行回调操作;
					// 注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return
					// false，则表单不会提交，如果return true或没有return，则会提交表单。
					mobile();
					return false;
				}
			});

});
function mobile() {
	var options = {
		url : "profile/updateMobile",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			var d = dialog({
				content : data.message
			});
			d.showModal();
			if (data.success) {
				setTimeout(function() {
					location.href = "profile/security";
				}, 1500);
			} else {
				setTimeout(function() {
					d.close().remove();
				}, 1500);
			}
		},
		error : function() {
			dialog.alert("通信异常");
		}
	};
	if ($("#mobile").val() != "" && $("#code").val() != "")
		$("#mobileForm").ajaxSubmit(options);
	else
		return false;
}