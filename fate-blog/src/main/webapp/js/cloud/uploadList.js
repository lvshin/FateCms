$(function(){
	setInterval("getLits()", 1000);
	$(".fileList tr td").poshytip();
});

function getLits(){
	$.ajax({
        url : "admin/file/getLists",
        type : 'post',
        dataType : 'json',
        data : {bucketName:$("#bucketName").val()},
        success : function(data){
        	$(".fileList tbody").children().remove();
        	var content = "";
        	for(var i=0;i<data.length;i++){
        		var status;
        		switch (data[i].state) {
        		case 1:status="正在上传至服务器";break;
				case 2:status="正在移动至OSS";break;
				case 3:status="上传完成";break;
				case 4:status="暂停";break;
				default:
					break;
				}
        		
        		content += "<tr>"+
						      "<td><span title="+data[i].fileName+" >"+data[i].fileName+"</span></td>"+
						      "<td>"+data[i].size+"</td>"+
						      "<td>" +
						      "<div><strong>"+status+"</strong></div>"+
						      "<div class='progress'>"+
									"<div class='progress-bar progress-bar-warning progress-bar-striped' role='progressbar' aria-valuenow='"+data[i].percentDone+"' "+
										"aria-valuemin='0' aria-valuemax='100' style='width: "+data[i].percentDone+"%;'>"+
										data[i].percentDone+"%</div>"+
								"</div></td>"+
							  "<td align='right'>";
        		if(data[i].state!=3)
        			content += "<span style='margin-left:6px;'><a href='javascript:void(0);'>取消上传</a></span>";
        		content += "</td></tr>";
        		
        	}
        	$(".fileList tbody").append(content);
        },
        error:function(data){
        }
	 }); 
}