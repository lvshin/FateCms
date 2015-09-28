<%@ page pageEncoding="utf-8"%>
<div class="reinforce-sidebar" data-left="${param.index-1}">
	<!-- List group -->
	<ul class="reinforce-menu">
		<li>
			<a href="admin/forum/forumList" class="list-title" rel="nofollow"><i class="fa fa-th-list"></i>&nbsp;版块管理</a>
		</li>
		<li>
			<a href="javascript:;" class="list-title" rel="nofollow"><i class="fa fa-files-o"></i>&nbsp;主题管理</a>
			<ul>
				<li><a href="admin/theme/themeList?fid=2" class="list-title" rel="nofollow"><i class="fa fa-th-list"></i>&nbsp;主题列表</a></li>
				<li><a href="admin/theme/seoPing" class="list-title" rel="nofollow"><i class="fa fa-heartbeat"></i>&nbsp;SEO Ping</a></li>
				<li><a href="admin/theme/recycleBin" class="list-title" rel="nofollow"><i class="fa fa-trash"></i>&nbsp;回收站</a></li>
			</ul>
		</li>
		<li><a href="admin/user/userList" class="list-title" rel="nofollow"><i class="fa fa-users"></i>&nbsp;用户列表</a></li>
		<li><a href="admin/announcement/list" class="list-title" rel="nofollow"><i class="fa fa-volume-up"></i>&nbsp;公告列表</a></li>
		<li><a href="javascript:dialog.alert('优化中');" class="list-title" rel="nofollow"><i class="fa fa-info-circle"></i>&nbsp;广告列表</a></li>
		<li>
			<a href="javascript:;" class="list-title" rel="nofollow"><i class="fa fa-link"></i>&nbsp;友链管理</a>
			<ul>
				<li><a href="admin/friendLink/list?state=0" class="list-title" rel="nofollow"><i class="fa fa-question"></i>&nbsp;待处理</a></li>
				<li><a href="admin/friendLink/list?state=1" class="list-title" rel="nofollow"><i class="fa fa-check"></i>&nbsp;已通过</a></li>
				<li><a href="admin/friendLink/list?state=2" class="list-title" rel="nofollow"><i class="fa fa-trash"></i>&nbsp;回收站</a></li>
				<li><a href="admin/friendLink/check" class="list-title" rel="nofollow"><i class="fa fa-exchange"></i>&nbsp;互链检测</a></li>
			</ul>
		</li>
		<li><a href="admin/spider" class="list-title" rel="nofollow"><i class="fa fa-bug"></i>&nbsp;蜘蛛记录</a></li>
		<li><a href="admin/exception" class="list-title" rel="nofollow"><i class="fa fa-minus-circle"></i>&nbsp;异常记录</a></li>
	</ul>
</div>
