//站点基本信息更新
function infoSubmit() {
	var options = {
		url : "admin/siteSet/updateSiteInfo",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#infoForm").ajaxSubmit(options);
}

// 极验验证的配置更新
function geetestSubmit() {
	var options = {
		url : "admin/siteSet/updateGeetest",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}
	};
	$("#geetestForm").ajaxSubmit(options);
}

// SEO配置更新
function seoSubmit() {
	var options = {
		url : "admin/siteSet/updateSeo",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#seoForm").ajaxSubmit(options);
}

// 邮件SMTP配置更新
function mailSubmit() {
	var options = {
		url : "admin/siteSet/updateMail",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#mailForm").ajaxSubmit(options);
}

// 发送测试邮件
function sendTestEmail() {
	$.ajax({
		type : "post",
		url : "admin/siteSet/sendTestEmail",
		dataType : 'json',
		data : {},
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}
	});
}

//注册控制更新
function regSubmit() {
	var options = {
		url : "admin/siteSet/updateReg",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#regForm").ajaxSubmit(options);
}

//qq登录配置更新
function qqSubmit() {
	var options = {
		url : "admin/siteSet/updateqq",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#qqForm").ajaxSubmit(options);
}

//多说评论配置更新
function duoshuoSubmit() {
	var options = {
		url : "admin/siteSet/updateDuoshuo",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#duoshuoForm").ajaxSubmit(options);
}

//短信服务配置更新
function smsSubmit() {
	var options = {
		url : "admin/siteSet/updateSms",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#smsForm").ajaxSubmit(options);
}

//多说评论配置更新
function baiduShareSubmit() {
	var options = {
		url : "admin/siteSet/updateBaiduShare",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#baiduShareForm").ajaxSubmit(options);
}

//新浪微博登录配置更新
function xinlangSubmit() {
	var options = {
		url : "admin/siteSet/updatexinlang",
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr.success(data.msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			} else {
				toastr.error(data.msg);
			}
		},
		error : function() {
			toastr.error("通信错误");
		}

	};
	$("#xinlangForm").ajaxSubmit(options);
}


/* 导航设置相关配置开始 */
$(function() {
	$.ajax({//获取导航树
		type : "post",
		url : "admin/siteSet/getNaviRoot",
		dataType : 'json',
		success : function(data) {
			$(".reinforce-content").find(".table").children("tbody").html(
					decodeNavi(data));
			$(".spinner").hide();
		}
	});
});

//把导航树从json解析橙html
function decodeNavi(data) {
	var content = "";
	for (var i = 0; i < data.length; i++) {
		content += "<tr class='forum'>"
				+ "<td><input type='text' class='form-control order_of_show' name='order' value='"
				+ data[i].order
				+ "' /><input type='hidden' name='type' value='" + data[i].type
				+ "' /></td>" + "<td";
		if (data[i].type == 2)
			content += " class='td-forum'";
		content += "><input class='form-control navi_name pull-left' type='text' name='name' value='"
				+ data[i].name
				+ "' /><input type='hidden' name='id' value='"
				+ data[i].id + "' />";

		content += "</td>"
				+ "<td><input class='form-control navi_url pull-left' type='text' name='url' value='"
				+ data[i].url + "' /></td>" + "<td align='reinforce-content'>"
				+ "<div class='btn-group btn-group-xs'>" + "</div>&nbsp;&nbsp;"
				+ "<div class='btn-group btn-group-xs'>"
				+ "<button class='btn btn-danger del' onclick=delNavi('"
				+ data[i].id + "')>删除</button>" + "</div>" + "</td>" + "</tr>"
				+ decodeNavi(data[i].children);
		if (data[i].type == 1) {
			content += "<tr>" + "<td></td>" + "<td colspan='3'>"
					+ "<a href='javascript:;' class='add newSubNavi' id='"
					+ data[i].id + "' rel='nofollow'>"
					+ "<span class='glyphicon glyphicon-plus'></span>"
					+ "&nbsp;添加新下拉菜单项" + "</a>" + "</td>" + "</tr>";
		}
	}
	return content;
}

//新建导航项
function newNavi() {
	var content = "<tr class='forum'>"
			+ "<td><input class='form-control order_of_show' type='text' name='order' value='0' /><input type='hidden' name='type' value='1' /></td>"
			+ "<td><input class='form-control navi_name pull-left' type='text' name='name' /><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a><input type='hidden' name='id' value='0' /></td>"
			+ "<td><input class='form-control navi_url pull-left' type='text' name='url' /></td>"
			+ "<td align='reinforce-content'>" + "</td>" + "</tr>";
	$(".reinforce-content").find(".table").children("tbody").append(content);
}

//新建二级导航
$(document).on("click", ".newSubNavi", function() {
	var content = "<tr class='forum'>"
			+ "<td><input class='form-control order_of_show' type='text' name='order' value='0' /><input type='hidden' name='type' value='2' /></td>"
			+ "<td class='td-forum'><input class='form-control navi_name pull-left' type='text' name='name'/><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a><input type='hidden' name='id' value='0' /><input type='hidden' name='parentId' value='"
			+ $(this).attr("id")
			+ "' /></td>"
			+ "<td><input class='form-control navi_url pull-left' type='text' name='url' /></td>"
			+ "<td align='reinforce-content'>" + "</td>"
			+ "</tr>";
	$(this).parent().parent().before(content);
});

//删除导航
$(document).on("click", ".deleterow", function() {
	$(this).parent().parent().remove();
});

function naviSubmit() {
	var result = "";
	$(".reinforce-content")
			.find(".table")
			.children("tbody")
			.children(".forum")
			.each(
					function() {
						result += "{type:'"
								+ $(this).find("input[name=type]").val()
								+ "',order:'"
								+ $(this).find("input[name=order]").val()
								+ "',name:'"
								+ $(this).find("input[name=name]").val()
								+ "',url:'"
								+ $(this).find("input[name=url]").val()
								+ "',id:'"
								+ $(this).find("input[name=id]").val()
								+ "',parentId:"
								+ ($(this).find("input[name=parentId]").length == 0 ? 0
										: $(this).find("input[name=parentId]")
												.val()) + "},";

					});
	if (result.length > 0) {
		result = result.substring(0, result.length - 1);
		result = "[" + result + "]";
		$.ajax({
			type : "post",
			url : "admin/siteSet/updateNavi",
			dataType : 'json',
			data : {
				result : result
			},
			error : function(data) {
				if (data.status == 404)
					dialog.alert("请求地址不存在");
				else if (data.status == 500)
					dialog.alert("系统内部错误");
				else if (data.status == 200) {
					location.href = "/";
				} else
					dialog.alert("通信异常");
			},
			success : function(data) {
				if (data.success){
					toastr.success("更新成功");
					setTimeout(function(){location.reload()},1500);
				}
				else {
					toastr.error(data.message);
				}

			}
		});
	}
}

//删除已存在的导航
function delNavi(id) {
	var d = dialog({
		content : "确认删除该消息？",
		title : '提示',
		width : '200px',
		okValue : '确定',
		ok : function() {
			$.ajax({
				type : "post",
				url : "admin/siteSet/delNavi",
				dataType : 'json',
				data : {
					id : id
				},
				error : function(data) {
					if (data.status == 404)
						dialog.alert("请求地址不存在");
					else if (data.status == 500)
						dialog.alert("系统内部错误");
					else if (data.status == 200) {
						location.href = "/";
					} else
						dialog.alert("通信异常");
				},
				success : function(data) {
					if (data.success)
						location.reload();
					else {
						dialog.alert(data.message);
					}

				}
			});
			return true;
		},
		cancelValue : "取消",
		cancel : function() {
		}
	});
	d.showModal();
}
/* 导航设置相关配置结束 */