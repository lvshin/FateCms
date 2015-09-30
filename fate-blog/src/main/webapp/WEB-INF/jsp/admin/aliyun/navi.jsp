<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="reinforce-sidebar" data-left="${param.index-1}">
	<!-- List group -->
	<ul class="reinforce-menu">
		<li><a href="admin/aliyun/set" class="list-title" rel="nofollow"><i class="fa fa-cog"></i>&nbsp;&nbsp;基础设置</a></li>
		<!--<li><a href="admin/aliyun/ecs/index" class="list-title" rel="nofollow"><i class="ali-icon icon-ecs"></i>&nbsp;&nbsp;ECS监控</a></li>-->
		<li>
			<a href="javascript:;" class="list-title" rel="nofollow"><i class="ali-icon icon-oss-small"></i>&nbsp;&nbsp;OSS</a>
			<ul>
				<li><a href="admin/aliyun/ossSet" class="list-title" rel="nofollow">配置</a></li>
				<li><a href="admin/aliyun/filelist" class="list-title" rel="nofollow">文件列表</a></li>
				<li><a href="admin/aliyun/debrislist" class="list-title" rel="nofollow">碎片整理</a></li>
			</ul>
		</li>
		<li><a href="admin/aliyun/openSearchSet" class="list-title" rel="nofollow"><i class="ali-icon icon-opensearch-small"></i>&nbsp;&nbsp;OpenSearch设置</a></li>
	</ul>
</div>
