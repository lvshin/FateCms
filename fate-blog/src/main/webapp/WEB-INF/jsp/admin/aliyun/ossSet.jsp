<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>${setting.siteName}-阿里云</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>

</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
	<div class="reinforce-main">
		<c:import url="navi.jsp">
			<c:param name="index" value="2" />
		</c:import>
		<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a
							href="javascript:;">阿里云</a> > OSS
					</h5>
				</div>
				<div class="site_right"></div>
			</div>
			<div class="page">
				<div class="page-header">
					<h1>
						阿里云OSS <small></small>
					</h1>
				</div>
				<div>
  					<!-- Nav tabs -->
  					<ul class="nav nav-tabs" role="tablist">
    					<li role="presentation" class="active"><a href="#set" aria-controls="home" role="tab" data-toggle="tab">基础设置</a></li>
    					<li role="presentation"><a href="#referer" aria-controls="profile" role="tab" data-toggle="tab">防盗链</a></li>
    					<li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Messages</a></li>
    					<li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">Settings</a></li>
  					</ul>

  					<!-- Tab panes -->
  					<div class="tab-content">
    					<div role="tabpanel" class="tab-pane animated fadeIn active" id="set">
							<form class="mg-t-10" id="setForm" onsubmit="return false;">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label>是否开启OSS远程附件
												<div class="glyphicon glyphicon-question-sign marginl10 help"
													title="开启OSS，将文件存储交给第三方处理，可以减轻服务器压力"></div>
											</label><br> <label><input type="radio" name="on"
												value="false" checked>&nbsp;&nbsp;否</label>&nbsp;&nbsp;&nbsp;&nbsp;
											<label><input type="radio" name="on" value="true">&nbsp;&nbsp;是</label>
										</div>
									</div>
									<div class="col-md-6">
										<label class="help-block">若开启，则将使用阿里云OSS开放式云存储；否则，附件将存放在服务器本地</label>
									</div>
								</div>
				
								<div class="oss">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="ossEndpoint">节点选择</label> <select
													class="form-control" id="endpoint" name="endpoint">
													<option value="oss-cn-qingdao.aliyuncs.com"
														${ossEndpoint=="oss-cn-qingdao.aliyuncs.com"?"selected":""}>青岛节点</option>
													<option value="oss-cn-beijing.aliyuncs.com"
														${ossEndpoint=="oss-cn-beijing.aliyuncs.com"?"selected":""}>北京节点</option>
													<option value="oss.aliyuncs.com"
														${ossEndpoint=="oss.aliyuncs.com"?"selected":""}>杭州节点</option>
													<option value="oss-cn-hongkong.aliyuncs.com"
														${ossEndpoint=="oss-cn-hongkong.aliyuncs.com"?"selected":""}>香港节点</option>
													<option value="oss-cn-shenzhen.aliyuncs.com"
														${ossEndpoint=="oss-cn-shenzhen.aliyuncs.com"?"selected":""}>深圳节点</option>
												</select>
											</div>
										</div>
										<div class="col-md-6">
											<label class="help-block">选择OSS节点</label>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="bucket">Buckets</label>
												<div class="input-group">
													<span class="input-group-btn">
														<button class="btn btn-info" onclick="getBuckets()">获取Buckets</button>
													</span> <select class="form-control" id="bucket" name="bucket">
														<c:forEach items="${buckets}" var="bucket" varStatus="i">
															<option value="${bucket.name}"
																${bucket.name==ossBucket?"selected":""}>${bucket.name}</option>
														</c:forEach>
													</select>
				
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<label class="help-block">选择Bucket</label>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="url">域名绑定</label> <input type="text"
													class="form-control" id="url" placeholder="url" name="url"
													value="${ossUrl}">
											</div>
										</div>
										<div class="col-md-6">
											<label class="help-block">在阿里云OSS设置的域名绑定地址，如果没有则不填</label>
										</div>
									</div>
								</div>
								<button class="btn btn-default" onclick="setSubmit()">提交</button>
							</form>
						</div>
    					<div role="tabpanel" class="tab-pane animated fadeIn" id="referer">
							<form class="form-horizontal mg-t-10" id="refererForm">
								<div class="form-group">
								    <label for="referer-content" class="col-sm-2 control-label">Referer白名单</label>
								    <div class="col-sm-6">
								      	<textarea class="form-control" id="referer-content" rows="6" name="referer" >${referers}</textarea>
								    	<label class="help-block mg-t-5">Refer格式：每个Refer白名单使用换行符分隔，支持通配符(*,?)</label>
								    </div>
								    <div class="col-sm-4"></div>
								</div>
								<div class="form-group">
								    <label for="" class="col-sm-2 control-label">空Referer</label>
								    <div class="col-sm-6">
								      <input type="radio" name="allowEmptyReferer" id="allowEmptyReferer1" value="true" ${allowEmptyReferer?"checked":""}><label for="allowEmptyReferer1">允许refer为空</label>
								      <br>
								      <input type="radio" name="allowEmptyReferer" id="allowEmptyReferer2" value="false" ${!allowEmptyReferer?"checked":""}><label for="allowEmptyReferer2">不允许refer为空</label>
								    </div>
								    <div class="col-sm-4"></div>
								</div>
								<div class="form-group">
								   <div class="col-sm-offset-2 col-sm-10">
								     <button type="button" class="btn btn-primary" onclick="refererSubmit()">设置</button>
								   </div>
								</div>
							</form>
						</div>
    					<div role="tabpanel" class="tab-pane fade" id="messages">...</div>
    					<div role="tabpanel" class="tab-pane fade" id="settings">...</div>
  					</div>

				</div>
			</div>
			<div class="clear"></div>
		</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript">
		$(function() {
			$("input[name=on]").click(function() {
				if ($(this).val() == 'true')
					$(".oss").show();
				else
					$(".oss").hide();
			});
			if (${setting.aliyunUsed})
				$("input[name=on]")[1].click();

			$(".help").poshytip();
		});

		//提交基本设置信息
		function setSubmit() {
			var options = {
				url : "admin/aliyun/updateOSSSet",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr.success(data.msg);
						setTimeout(function() {
							window.location.reload();
						}, 1000);
					} else {
						toastr.error(data.msg);
					}
				},
				error : function() {
					toastr.error("通信错误");
				}

			};
			$("#setForm").ajaxSubmit(options);
		}

		function getBuckets() {
			$
					.ajax({
						type : "post",
						url : "admin/aliyun/getBuckets",
						dataType : 'json',
						data : {
							endpoint : $("#endpoint").val()
						},
						success : function(data) {

							if (data.success) {
								var content = "";
								for (var i = 0; i < data.buckets.length; i++) {
									content += "<option value='"+data.buckets[i].name+"'>"
											+ data.buckets[i].name
											+ "</option>";
								}
								$("#bucket").html(content);
							} else {
								var d = dialog({
									content : data.msg
								});
								d.showModal();
								setTimeout(function() {
									d.close().remove();
								}, 1000);
							}
						},
						error : function() {
							alert("通信异常");
						}
					});
		}
		
		//提交防盗链
		function refererSubmit() {
			var options = {
				url : "admin/aliyun/updateOSSReferer",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr.success(data.msg);
						setTimeout(function() {
							window.location.reload();
						}, 1000);
					} else {
						toastr.error(data.msg);
					}
				},
				error : function() {
					toastr.error("通信错误");
				}

			};
			$("#refererForm").ajaxSubmit(options);
		}
	</script>
</body>
</html>
