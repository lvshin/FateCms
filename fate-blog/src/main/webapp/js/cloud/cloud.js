	function open(url,title, classs, type) {
			var content;
			if(type==1)
				content = "<div id='a1'></div>";
			else
				content = url;
			console.log(type);
			if(type==1)
				play(url);
			else{
				if(type==0)
					content = "<audio src='"+content+"' controls='controls' preload='none'>您的浏览器不支持 audio 标签。</audio>"
				var d = dialog({
					title : "<span class='file-icon-big icon-"+classs+"'></span><span class='spanFileName'>"+decodeURIComponent(title).replace(/\+/g, " ").replace(/\*/g, "+")+"</span>",
					content : content
				});
				d.addEventListener("show",function(){
					if(type==1)
						play(url);
				});
				
				d.addEventListener("focus",function(){
				  d.reset();
				});
				
				d.showModal();
				$("#img").load(function(){
					d.reset();
				});
			}
			
	}
	
	function deleteObject(bucketName,key,type){
		var d = dialog({
		    title: (type==0?'提示':'警告'),
		    content: (type==0?'<strong>确认删除？</strong>':'<strong>文件夹下包含的文件将会被一并删除，确认删除吗？</strong>'),
		    width: (type==0?'200px':'400px'),
		    okValue: '确定',
		    ok: function () {
		    	 $.ajax({
		              url : "file/deleteObject",
		              type : 'post',
		              dataType : 'json',
		              data : {bucketName : bucketName,key : key},
		              success : function(data){
		            	 if(data.success){
		            		 toastr.success(data.msg);
		            		 location.reload();
		            	 }else{
		            		 toastr.error(data.msg);
		            	 }
		            		
		              },
		              error:function(data){
		            	  toastr.error('网络连接异常');
		              }
		    	 });   
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		});
		d.showModal();
	}
	
	function getOutUrl(bucketName,key){
		var txt = "<div class='formgroup'><label style='float:left;'>文件：</label><div  style='width:400px;float:left;'><span><strong>"+key+"</strong></span></div></div><br>"
		         +"<div class='formgroup'><label style='float:left;'>地址：</label><div  style='width:400px;float:left;'><span id='urlRes'>请输入链接有效时间：<input id='timeout' value='3600' class='common_input'/>"
		         +"<select id='timeType' class='select'>"
		         +"<option value='0' selected>秒</option>"
		         +"<option value='1'>分钟</option>"
		         +"<option value='2'>小时</option>"
		         +"<option value='3'>天</option>"
		         +"</select></span></div></div>";
		var d = dialog({
		    title: '获取外链地址',
		    content: txt,
		    width: '500px',
		    okValue: '获取外链',
		    ok: function () {
		    	 $.ajax({
		              url : "file/getFileTempUrl",
		              type : 'post',
		              dataType : 'json',
		              data : {bucketName : bucketName,key : key,timeout:$("#timeout").val(),timeType:$("#timeType").val()},
		              success : function(data){
		            	  $("#urlRes").html(data.url);	
		            	  $("#urlRes").parent().append("<button id='d_clip_button' class='my_clip_button button_js' data-clipboard-target='urlRes' style='width:150px;margin-top:10px;'><b>复制外链到剪贴板</b></button>");
		            	  if (window.clipboardData) 
		                  {
		          		    window.clipboardData.setData("Text", txt);
		                  }
		            	  else{
		            	  // 定义一个新的复制对象
		            		var client = new ZeroClipboard( document.getElementById("d_clip_button"));

		            		client.on( "ready", function( readyEvent ) {
		            			  // alert( "ZeroClipboard SWF is ready!" );

		            			  client.on( "aftercopy", function( event ) {
		            			    // `this` === `client`
		            			    // `event.target` === the element that was clicked
		            			    toastr.info("复制成功");
		            			  } );
		            			} );
		            	  }
		              },
		              error:function(data){
		            	  alert('网络连接异常');
		              }
		    	 }); 
		    	 return false;
		    }
		});
		d.showModal();
	}
	
	function upload(){
		alert($("#curDir").val()=="");
	}
	
	function search(){
		if($("#keyword").val()!="")
		window.location.href="admin/aliyun/filelist?dir="+$("#curFolder").val()+"&keyword="+$("#keyword").val();
	}
	
	function newDir(){
		var txt = "<form class='form-horizontal' onsubmit='return false;'><div class='form-group'><label for='bucketName' class='col-sm-3 control-label'>文件夹名：</label><div class='col-sm-9'><div class='row'>"
			     +"<div class='col-sm-7'><input type='text' class='form-control' id='folderName' ng-minlength='1' ng-maxlength='254' ng-pattern='/^[\u4E00-\u9FA5\uF900-\uFA2D\u3040-\u309F\u30A0-\u30FF\u3100-\u312F\u31F0-\u31FF\uAC00-\uD7AFa-zA-Z0-9]{1}[\u4E00-\u9FA5\uF900-\uFA2D\u3040-\u309F\u30A0-\u30FF\u3100-\u312F\u31F0-\u31FF\uAC00-\uD7AFa-zA-Z0-9\._\-]{0,253}$/' maxlength='254' autofocus>"
			     +"</div><div class='col-sm-5'><div class='help-block ng-hide' >"
			     +"<span class='error text-danger div-folderName'  style='display:none;'>文件夹名不能为空</span> <span class='error text-danger div-length' style='display:none;'>长度限制在1-254之间</span>"
			     +"<span class='error text-danger div-pattern'  style='display:none;'>文件夹名格式错误</span></div></div></div><div class='row'>"
			     +"<div class='col-sm-12'><p class='help-block'>文件夹命名规范：<br>» 1. 只能包含字母，数字，中文，下划线（_）和短横线（-）,小数点（.）<br>» 2. 只能以字母、数字或者中文开头<br>» 3. 文件夹名的长度限制在1-254之间<br>» 4. Object总长度必须在1-1023之间</p></div></div></div></div></form>";
		var d = dialog({
		    title: '新建文件夹',
		    content: txt,
		    width: '600px',
		    okValue: '提交',
		    ok: function () {
		    	 $.ajax({
		              url : "file/newFolder",
		              type : 'post',
		              dataType : 'json',
		              data : {folderName : $("#folderName").val(),curFolder:$("#curFolder").val(),bucketName:$("#bucketName").val()},
		              success : function(data){
		            	 if(data.success)
		            	 {
		            		 toastr.success("创建文件夹成功");
	            			 setTimeout(function () {
	            			     location.reload();
	            			 }, 2000);
		            	 }
		            	 else
		            	 {
		            		 toastr.error("创建文件夹失败");
		            	 }
		              }
		    	 }); 
		    }
		});
		d.showModal();
	}
	
	function batchDeleteObject(bucketName){
			var d = dialog({
			    title: '提示',
			    content: '<strong>确认删除选中文件吗？</strong>',
			    width: '200px',
			    okValue: '确定',
			    ok: function () {
			    	 $.ajax({
			              url : "file/batchDeleteObject",
			              type : 'post',
			              dataType : 'json',
			              data : {bucketName : bucketName,keys : $("#selectedFiles").val()},
			              success : function(data){
			            	  var d2 = dialog({
			            		    content: (data==0?'删除成功':(data==1?'删除失败，请重试':'请求超时'))
			            		});
			            	  
			            		  d2.show();
				            		setTimeout(function () {
				            		    d2.close().remove();
				            		    if(data==0)
				            		    location.reload();
				            		}, 1500);
			            		
			              },
			              error:function(data){
			            	  alert('网络连接异常');
			              }
			    	 });   
			    },
			    cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
	}
	
	function unSelectAll(){
		$(".fileList input:checkbox").each(function(){
           $(this).removeAttr("checked");			
		});
		$(".pull-left").children().attr("disabled","disabled");
		$("#selectedFiles").val("");
	}
	
	$(function(){
		
		$(document).on("keyup change","#folderName",function(){
			var len = $(this).val().replace(/[\u0391-\uFFE5]/g,"aaa").length;
			if(len>254)
			{
				$(".div-length").show();
				$(".ui-dialog-autofocus").attr("disabled","disabled");
				return ;
			}
			else{
				$(".div-length").hide();
				$(".ui-dialog-autofocus").removeAttr("disabled");
			}
			if($(this).val()==""){
				$(".div-folderName").show();
				$(".ui-dialog-autofocus").attr("disabled","disabled");
				return ;
			}
			else{
				$(".div-folderName").hide();
				$(".ui-dialog-autofocus").removeAttr("disabled");
			}
			
			var pattern = eval($(this).attr("ng-pattern"));
			var str = $(this).val();
			if(str.match(pattern)!=null){
				$(".div-pattern").hide();
				$(".ui-dialog-autofocus").removeAttr("disabled");
			}
			else{
				$(".div-pattern").show();
				$(".ui-dialog-autofocus").attr("disabled","disabled");
				return ;
			}
		});
		
		$(document).on("keydown","#folderName",function(e){
			if(e.keyCode==13)
				$(".ui-dialog-autofocus").click();
		});
		
		$(".fileList tbody input:checkbox").click(function(){
			$("#selectedFiles").val("");
			$(".fileList tbody input:checked").each(function(){
				$("#selectedFiles").val($("#selectedFiles").val()+$(this).val()+"*");
			});
			if($(".fileList tbody input:checked").length>0){
				$(".pull-left").children().removeAttr("disabled");
			}
			else{
				$(".pull-left").children().attr("disabled","disabled");
			}
		});
		
		$("#selectedAll").click(function(){
			$(".fileList tbody input").each(function(){
				if($("#selectedAll")[0].checked!=$(this)[0].checked)
				    $(this).click();
			});
			$(".fileList tbody input:checked").each(function(){
				$("#selectedFiles").val($("#selectedFiles").val()+$(this).val()+"*");
			});
			if($(".fileList tbody input:checked").length>0){
				$(".pull-left").children().removeAttr("disabled");
			}
			else{
				$(".pull-left").children().attr("disabled","disabled");
			}
		});
		
		$("#keyword").keydown(function(e){
			if(e.keyCode==13)
				search();
		});
		
		//显示Tips
		$('[data-toggle="tooltip"]').tooltip();
	});
	