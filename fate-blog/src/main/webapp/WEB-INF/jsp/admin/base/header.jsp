<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" data-header="${param.header_index-1}">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/" rel="nofollow"><img src="images/logo.png" width="40" height="40" alt="祝福之风"></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-main">
      	<li class="dropdown">
          <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" rel="nofollow"><span class="fa fa-globe"></span>&nbsp;站点配置<span class="caret"></span></a>
          <ul class="dropdown-menu animated fadeInDown" role="menu">
            <li><a href="admin/siteSet/siteInfo">基础配置</a></li>
            <li><a href="admin/aliyun/set">阿里云</a></li>
          </ul>
        </li>
        <li>
          <a href="admin/forum/forumList" rel="nofollow"><span class="fa fa-cogs"></span>&nbsp;站点功能 </a>
        </li>
        <li>
          <a href="admin/statistics/theme" rel="nofollow"><span class="fa fa-bar-chart"></span>&nbsp;统计 </a>
        </li>
      </ul>
      <form class="navbar-form navbar-left" role="search" style="position: relative;">
        <div class="form-group">
          <input type="text" class="form-control search" placeholder="Search">
        </div>
        <a class="searchBtn" href="javascript:;"><span class="fa fa-search"></span></a>
        
      </form>
      <ul class="nav navbar-nav navbar-right">
        <!-- <li><img src="images/2.png" style="margin-top:10px;height:30px;border-radius:100px;"/></li> -->
        <li>
        	<a href="javascript:;" class="user-info">
        		<img src="${userSession.user.headIconBig==null?" images/default_headicon.png":userSession.user.headIconBig}" class="img-circle" width=28 alt="${userSession.user.nickName}" />
				${userSession.user.nickName}
        	</a>
        </li>
        <li><a href="javascript:logout()">退出</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</div>
