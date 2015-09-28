<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	//兼容其他参数的写法，不用再改URL
	function changePage(page) {
		if(page!=""){
			var url = location.protocol + '//' + location.host + location.pathname;
			var args = location.search;
    		var reg = new RegExp('([\?&]?)curPage=[^&]*[&$]?', 'gi');
    		args = args.replace(reg,'$1');
    		if (args == '' || args == null) {
       		 args += '?curPage=' + page;
    		} else if (args.substr(args.length - 1,1) == '?' || args.substr(args.length - 1,1) == '&') {
            		args += 'curPage=' + page;
    		} else {
            		args += '&curPage=' + page;
    		}
			location.href = url + args;
			}
	}
	
</script>
	<nav class="pull-left">
		<ul class="pagination ${pageStyle==0?"pagination-lg":""}${pageStyle==2?"pagination-sm":""} ">
			<li ${page.curPage-1==0?"class='disabled'":""}><a href="javascript:changePage('${page.curPage-1>0?"1":""}')" rel="nofollow"><span aria-hidden="true">&laquo;</span></a></li>
			<c:if test="${page.curPage-1>0}">
				<li><a href="javascript:changePage('${page.curPage-1<=0?1:page.curPage-1}')" rel="nofollow"><span aria-hidden="true">&lsaquo;</span></a></li>
			</c:if>
			<c:forEach
				begin="${page.curPage+page.middle>page.totalPage?(page.totalPage-page.middle*2>0?page.totalPage-page.middle*2:1):(page.curPage-page.middle>0?page.curPage-page.middle:1)}"
				end="${page.curPage+page.middle>page.totalPage?page.totalPage:(page.curPage-page.middle>0?(page.type%2==0?page.curPage+page.middle-1:page.curPage+page.middle):(page.totalPage>page.type?page.type:page.totalPage))}"
				var="i">
				<li ${page.curPage==i?"class='active'":""}><a href="javascript:changePage('${i}')" rel="nofollow">${i}</a></li>
			</c:forEach>
			<c:if test="${page.curPage<page.totalPage}">
				<li><a href="javascript:changePage('${page.curPage+1>page.totalPage?page.totalPage:page.curPage+1}')" rel="nofollow"><span aria-hidden="true">&rsaquo;</span></a></li>
			</c:if>
			<li ${page.curPage==page.totalPage?"class='disabled'":""}><a href="javascript:changePage('${page.curPage<page.totalPage?page.totalPage:""}')" rel="nofollow"><span aria-hidden="true">&raquo;</span></a></li>
		</ul>
		
	</nav>
	<div class="jump pull-right" style="display: inline-block;padding-top: 20px;">
		<label>共${page.totalPage}页,跳转至</label><input class="form-control jumpInput" style="width:50px;display: inline-block;" maxlength="3" data-sum="${page.totalPage}"/><label>页</label><button class="btn btn-default" id="jumpBtn" style="margin-top: -4px;">Go</button>
		</div>