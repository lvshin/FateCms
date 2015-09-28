var intervalID;
	function getMultipartProgress() {
		$("#res2").html("start");
		$.ajax({
			type : "post",
			url : "file/getUploadProgress",
			dataType : 'json',
			data : {
				bucketName : $("#bucketName").val()
			},
			success : function(data) {
				if (data != null) {
					if (data == "100") {
						$("#res").html(data);
						clearInterval(intervalID);
					}
					else
						$("#res").html(data);
				}
				else
					$("#res").html("�ϴ��ļ�����");
			},
			error : function() {
				alert("ͨ�Ŵ���");
				clearInterval(intervalID);
			}
		});
	}

	function doMultipartUpload() {
		function callback(data) {
			alert(data.success);
		}
//		$.ajax({
//			type : "post",
//			url : "file/uploadFileName",
//			dataType : 'json',
//			data : {
//				fileName : $("#uploadButton").val()
//			},
//			success : function(data) {
//				$(".uploadNum").html("("+data.listAll+")");
				var options = {
					url : "file/upload",
					success : callback,
					type : 'post',
					dataType : 'json',
					data : {folder:$("#curFolder").val(),
						bucketName : $("#bucketName").val()
					}
				};
				$("#multipartUploadForm").ajaxSubmit(options);
//			},
//			error : function() {
//				alert("ͨ系统错误");
//			}
//		});
	}