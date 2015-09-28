<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	//兼容其他参数的写法，不用再改URL
	function changePage(page) {
		if(page!=""){
			var pathname = location.pathname;
			pathname = pathname.substring(0,pathname.lastIndexOf("-")+1);
			var url = location.protocol + '//' + location.host + pathname + page + ".html";
			var args = location.search;
			location.href = url + args;
			}
	}
</script>
	<nav>
		<ul class="pagination ${pageStyle==0?"pagination-lg":""}${pageStyle==2?"pagination-sm":""}">
			<li ${page.curPage-1==0?"class='disabled'":""}><a href="javascript:changePage('${page.curPage-1>0?"1":""}')" rel="nofollow"><span aria-hidden="true">&laquo;</span><span class="sr-only">Previous</span></a></li>
			<c:if test="${page.curPage-1>0}">
				<li><a href="javascript:changePage('${page.curPage-1<=0?1:page.curPage-1}')" rel="nofollow"><span aria-hidden="true">&lsaquo;</span><span class="sr-only">Next</span></a></li>
			</c:if>
			<c:forEach
				begin="${page.curPage+page.middle>page.totalPage?(page.totalPage-page.middle*2>0?page.totalPage-page.middle*2:1):(page.curPage-page.middle>0?page.curPage-page.middle:1)}"
				end="${page.curPage+page.middle>page.totalPage?page.totalPage:(page.curPage-page.middle>0?(page.type%2==0?page.curPage+page.middle-1:page.curPage+page.middle):(page.totalPage>page.type?page.type:page.totalPage))}"
				var="i">
				<li ${page.curPage==i?"class='active'":""}><a href="javascript:changePage('${i}')" rel="nofollow">${i}</a></li>
			</c:forEach>
			<c:if test="${page.curPage<page.totalPage}">
				<li><a href="javascript:changePage('${page.curPage+1>page.totalPage?page.totalPage:page.curPage+1}')" rel="nofollow"><span aria-hidden="true">&rsaquo;</span><span class="sr-only">Next</span></a></li>
			</c:if>
			<li ${page.curPage==page.totalPage?"class='disabled'":""}><a href="javascript:changePage('${page.curPage<page.totalPage?page.totalPage:""}')" rel="nofollow"><span aria-hidden="true">&raquo;</span><span class="sr-only">Next</span></a></li>
		</ul>
	</nav>
