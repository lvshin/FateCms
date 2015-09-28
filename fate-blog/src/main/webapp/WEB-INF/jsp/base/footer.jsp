<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>${adv.active?adv.code:''}</div>
<footer class="footer">
    <p>Copyright©2014-${year}  ${setting.appName} All Rights Reserved</p>
    <p><a href="http://${setting.appUrl}/"><strong>${setting.appName}</strong></a>&nbsp;
		<c:if test="${setting.icp!=null}">
			(<a href="http://www.miitbeian.gov.cn/" rel="nofollow">${setting.icp}</a>)&nbsp;
		</c:if>
		|&nbsp;&nbsp;<a href="javascript:;" rel="nofollow" data-toggle="modal" data-target="#myModal">申请友链</a>&nbsp;
		|&nbsp;&nbsp;<a href="sitemap.xml" target="_blank">网站地图</a>&nbsp;
		|&nbsp;&nbsp;<c:if test="${userSession.user.userType==0}">${setting.statistics}</c:if>
		站长邮箱:<a href="mailto:${setting.adminEmail}" rel="nofollow">${setting.adminEmail}</a>&nbsp;
		<c:if test="${userSession.user.userType!=1}">
			${setting.statistics}
		</c:if>
		</p>
</footer>
<!-- 友链填写框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">提交友链申请</h4>
      </div>
      <div class="modal-body">
				<form class="form-horizontal" id="linkForm" autocomplete="off">
					<div class="form-group">
						<label for="linkName" class="col-sm-2 control-label">网站名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="linkName" name="linkName" datatype="*" placeholder="要显示的友链名称,不超过10个字" maxlength="10">
						</div>
					</div>
					<div class="form-group">
						<label for="linkUrl" class="col-sm-2 control-label">友链地址</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="linkUrl" name="linkUrl" datatype="url" placeholder="友链地址">
						</div>
					</div>
					<div class="form-group">
						<label for="linkEmail" class="col-sm-2 control-label">站长邮箱</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="linkEmail" name="linkEmail" placeholder="友链审核结果会发送到您的邮箱">
						</div>
					</div>
				</form>
			</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="$('#linkForm').submit();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script src="http://open.reinforce.cn/jquery/1.11.3/jquery.min.js"></script>
<script src="http://g.alicdn.com/opensearch/opensearch-console/0.16.0/scripts/jquery-ui-1.10.2.js"></script>
<script src="http://open.reinforce.cn/poshytip/1.2/jquery.poshytip.min.js"></script>
<script src="http://open.reinforce.cn/Bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="http://open.reinforce.cn/jquery/jquery.md5.min.js"></script>
<script src="http://open.reinforce.cn/jquery/jquery.form.min.js"></script>
<script src="http://open.reinforce.cn/jquery/jquery.cookie.min.js"></script>
<script src="http://open.reinforce.cn/artdialog/6.0.4/js/dialog-plus-min.js"></script>
<script src="http://open.reinforce.cn/headroom/headroom.min.js"></script>
<script src="http://open.reinforce.cn/Validform/js/Validform_v5.3.2_min.js"></script>
<script src="http://open.reinforce.cn/nprogress/nprogress.js"></script>
<script src="http://open.reinforce.cn/toastr/js/toastr.min.js"></script>
<script src="js/base/header.js" charset="utf-8"></script>
<script src="js/sockjs-0.3.min.js"></script>
<script>
//初始化toastr设置 
toastr.options.escapeHtml = true;
toastr.options.closeButton = true;
toastr.options.progressBar = true;
    $(function() {
        var hit = 10; // 显示的下拉提示列表的长度，可修改
         
        setTimeout(function() {
            $( "#keyword" ).autocomplete({
                delay: 0,
                source: function(request, response) {
                    $.ajax({
                        url: "op/getSuggest" ,
                        dataType: 'json',//如果需要为jsonp类型，则需要在下面的data属性中加上callback: ?
                        data: {query: $( "#keyword" ).val()},
                        success: function(data) {
                        	console.log(data);
                            if(data.status === 'OK' && data.result) {
                                if(data.result.length >= hit) {
                                    response(data.result.slice(0, hit));
                                } else {
                                    response(data.result);
                                }
                            } else if( data.status === 'FAIL' && data.errors ){
                                //alert(data.errors[0].message);
                            }
                        }

                    });
                }
            }).bind("input.autocomplete", function () {
                // 修复在Firefox中不支持中文的BUG
                $( "#keyword" ).autocomplete("search", $( "#keyword" ).val());
            }).focus();
        }, 0);
    });

</script>
<script>
	
	$(function() {
	
		//滚轮下翻页面时，如果导航条到达浏览器窗口顶部，则固定导航条
		$(".banner ul li").css("height",$(window).height()-41);
		
		//生成二维码
		$(".QR").attr("src","op/getQR?url="+window.location.href);
		$(".QR").parent().hover(function(){
			$(".showQR").show();
		},function(){
			$(".showQR").hide();
		});
		var header = new Headroom(document.querySelector("#header"), {
        	tolerance: 5,
        	offset : 205,
        	classes: {
          	initial: "animated",
          	pinned: "slideDown",
         	unpinned: "slideUp"
        	}
    	});
    	
    	//响应滚动条的header初始化
    	header.init();
    	
    	if($(".table-forum").length>0){
    		$(".table-forum tr").hover(function(){
    			$(this).children().first().children("button").show();
    		},function(){
    			$(this).children().first().children("button").hide();
    		});
    		$(document).on("click",".btn-top",function(){
    			var guid = $(this)[0].dataset.guid;
    			var d = dialog({
    				title : "置顶",
    				content : "<input class='form-control' id='priority' placeholder='优先级' />",
    				okValue : "确定",
    				ok : function(){
    					setTop(guid, $("#priority").val());
    				},
    				cancelValue:"取消",
    				cancel: function(){
    				}
    			});
    			d.showModal();
    		});
    		$(document).on("click",".btn-top-cancel",function(){
    			var guid = $(this)[0].dataset.guid;
    			setTop(guid, 0);
    		});
    	}
    	$("#linkForm")
				.Validform(
						{
							btnReset : "",
							tiptype : function(msg, o, cssctl) {
								switch (o.type) {
								case 1:
									$(o.obj).parent().parent().removeClass(
											"has-error").removeClass(
											"has-success");
									break;
								case 2:
									$(o.obj).parent().removeClass(
											"has-error")
											.addClass("has-success");
									break;
								case 3:
									$(o.obj).parent().parent().removeClass(
											"has-success")
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
							postonce : false,
							datatype : {
							},
							usePlugin : {
							},
							beforeCheck : function(curform) {
								//在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
								//这里明确return false的话将不会继续执行验证操作;	
							},
							beforeSubmit : function(curform) {
								//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
								//这里明确return false的话表单将不会提交;	
							},
							callback : function(data) {
								//返回数据data是json对象，{"info":"demo info","status":"y"}
								//info: 输出提示信息;
								//status: 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
								//你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
								//ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**, readyState:**, responseText:** }；

								//这里执行回调操作;
								//注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return false，则表单不会提交，如果return true或没有return，则会提交表单。
								updateFriendLink();
								return false;
							}
						});
		//分页相关
		$(document).on("click","#jumpBtn",function(){
			var page = $(".jumpInput").val();
			changePage(page);
		});
		
		$(document).on("input", ".jumpInput", function() {
			var reg = /^[1-9][0-9]*$/;
			var _txt = $(this).val();
			if (reg.test(_txt)) {

			} else {
				$(this).val(1);
			}
			if(parseInt(_txt)>parseInt($(this)[0].dataset.sum))
				$(this).val($(this)[0].dataset.sum);
		});
		
		$(document).on("keydown",".jumpInput",function(e){
			if(e.keyCode==13){//按下回车
				var page = $(".jumpInput").val();
				changePage(page);
			}
				
		});
	});
	
	
	function setTop(guid, priority) {
		$.ajax({
			type : "post",
			url : "admin/theme/setTop",
			dataType : 'json',
			data : {
				guid : guid,
				priority : priority
			},
			success : function(data) {
				if (data.success){
					toastr.success(data.msg);
					setTimeout(function(){location.reload();},1000);
				}
				else
					toastr.error(data.msg);
			}
		});
	}

	function updateFriendLink() {

		var options = {
			url : "friendLink/add",
			type : 'post',
			dataType : 'json',
			data : {},
			success : function(data) {
				if (data.success) {
					dialog.alert("提交成功，请在您的网站上挂上本站链接，${userSession.user.nickName}将会尽快审核~~");
				} else
					dialog.alert(data.msg);
			},
			error : function(data) {
				if (data.status == 404)
					dialog.alert("请求地址不存在");
				else if (data.status == 500)
					dialog.alert("系统内部错误");
				else if (data.status == 200) {
					dialog.alert("登录超时，为保证您填写的内容不丢失，请勿刷新页面，并在新开页面中重新登录");
				} else
					dialog.alert("通信异常");
			}

		};
		$("#linkForm").ajaxSubmit(options);
	}

	function toTop() {
		$('html, body').animate({
			scrollTop : 0
		}, '500');
	}

	function toBottom() {
		var $w = $(window);
		$('html, body').animate({
			scrollTop : $(document).height() - $w.height()
		}, '500');
	}
</script>

<c:if test="${setting.duoshuoKey!=null}">
<!-- 多说公共JS代码 start (一个网页只需插入一次) -->
<script type="text/javascript">
	var duoshuoQuery = {
		short_name : "${setting.duoshuoKey}"
	};
	(function() {
		var ds = document.createElement('script');
		ds.type = 'text/javascript';
		ds.async = true;
		ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:')
				+ '//static.duoshuo.com/embed.js';
		ds.charset = 'UTF-8';
		(document.getElementsByTagName('head')[0] || document
				.getElementsByTagName('body')[0]).appendChild(ds);
	})();
</script>
<!-- 多说公共JS代码 end -->
</c:if>

<script>
	//var websocket;
	//if ('WebSocket' in window) {
		//websocket = new WebSocket("ws://${setting.appUrl}/systemMessageServer");
	//} else if ('MozWebSocket' in window) {
	//	websocket = new MozWebSocket(
	//			"ws://${setting.appUrl}/systemMessageServer");
	//} else {
	//	websocket = new SockJS(
	//			"http://${setting.appUrl}/sockjs/systemMessageServer");
	//}
	//websocket.onopen = function(evnt) {
	//};
	//websocket.onmessage = function(evnt) {
	//	$("#msgCount").html("(<font color='red'>" + evnt.data + "</font>)")
	//};
	//websocket.onerror = function(evnt) {
	//};
	//websocket.onclose = function(evnt) {
	//}
</script>