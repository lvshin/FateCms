更名为FateBlog，FateCMS另有他用

幻幻博客地址：http://www.reinforce.cn 

**注意：原本这套体系这是给自己用的，所以符合我自己的操作习惯，亲们使用的时候难免会有些bug。借这次阿里云编程马拉松的机会，把它放出来，一些能用阿里云的地方，只能使用阿里云，并不能像开关一样切换，比如文件存储放在OSS上，不能切换回本地。**

原以为用了那么多阿里云的API，以后会派不上用场，结果机会来了(*^▽^*)

光是一个博客前台的话，看上去其实和WordPress差不多，换个主题就能长得一样。主要是管理后台的功能很多。当初主要考虑到程序和数据分离，才使用的OSS，同时数据库使用了RDS，这样的话，服务器就算出问题，随便回滚也没问题；而博客主要以图片为主，这样也不会造成太大的费用，服务器也省资源了，1核1G内存1M带宽的ECS，配合CDN足矣（当然，新站没名气，访问的人少，人一多，可能就要升级配置了）

**使用说明：**

1.试用前，请先安装jdk1.8和tomcat8

2.这里贴出的是源码，试用请下载war包,http://open.reinforce.cn/FateCms.7z

3.将ROOT.war解压到tomcat中的webapps文件夹下（里面原来的文件全删了），找到ROOT-->WEB-INFO-->classes-->datasource.properties标准配置，配置好数据库，删掉文件名中的最后四个字,**注意数据库格式必须是utf8**

4.运行tomcat

5.开始配置你的博客

6.删除ROOT-->WEB-INFO-->classes-->fate-->webapp-->blog-->api中的installCtl.class

7.OpenSearch模版：http://open.reinforce.cn/FateCms_OpenSearch模版.txt


**后台主要功能介绍：**

一、基础

1.可自定义网站名称，副标题；设置网站的URL，非此网址而又解析到本站的自动301跳转；备案号设置；head和body前可以分别插入js代码，像百度统计新版的代码就要放在head前；redis开启设置，默认关闭，配置好redis后，可以开启。

2.站点首页的SEO，可另外设定首页的title,keywords,drscription

3.导航设置，就是博客首页顶部的导航条，可自定义导航到哪里，导航名称支持Bootstrap和Font Awesome中的图标

4.邮件SMTP，当初为了注册设计的，现在不推荐开启注册

5.极验验证的id和key设置，设置成功就会在登录注册时出现极验验证的验证码

6.QQ登录设置，可选是否开启QQ登录，设置QQ登录的accessKey和accessSecret

7.新浪微博登录，同上

8.百度一键分享中的key设置，新浪的同登录key，腾讯的是微博的key

9.多说设置，填写完应用名和密钥，就可开启文章评论，本系统只将文章ID传给多说。

10.版块设置方法参考了Discuz，第一级是分区，可以理解是大分类，第二级才来细分版块。

11.主题列表基本就只有删除功能，修改都在前台

12.所有文章发布都都是会响百度发送Ping的，谷歌被墙了，就放在那里，没做实质功能，在ping列表里可以重ping

13.回收站，在主题列表里的删除只是逻辑删除，会进回收站，在回收站里删了就真的找不到了(ﾟｰﾟ)

14.用户列表，只是看看。。。

15.在前台的页面底部有个“申请友链”按钮，其他站长可以填写本站信息，提交审核，然后他的网站就会出现在友链的待处理列表中，审核就看站长自己了；审核通过的会检测是否有互链。

16.搜索引擎的蜘蛛来访记录，凡是来过的蜘蛛都会被记录下来，看看你的网站受蜘蛛们欢迎不(～￣▽￣)～

17.异常记录，好像目前只能看见404，没见过500- -，为开发设计的，一方面可以看也没上的js和css有没有失效的，另一方面可以根据它手动生成一份死链列表。（自动的过段时间做╮(╯▽╰)╭）

二、阿里云相关

1.所有阿里云相关功能使用的前提是设置好Access Key ID和Access Key Secret

2.OSS请开启，否则无法存放文件，理由上面说过；选好节点和bucket后即可使用OSS，域名绑定可选；防盗链功能和阿里云官网上的一样

3.不建议在文件列表页上传大文件，虽然没做限制，最好只是用于查看和删除；大文件上传请使用官方的OSS上传工具

4.首页导航栏的搜索框，必须设置完OpenSearch才能使用，小博客一般免费版的OpenSearch就够用了^O^


--------------------------------------------华丽丽的分割线，博客前台的特点----------------------------------------------

1.版块和文章使用伪静态URL，更易于搜索引擎收录，支持中文链接，无需转码。

2.主页上显示的一些文章列表和网站的配置信息，直接存入单例（当然也会存数据库，在程序启动时，从数据库读取到单例；有改动时，两边同时更新）

3.整合了OpenSearch来全文检索

4.使用多说评论框，本地的登录只留作管理员登录用

5.页面右侧，按访问量/搜索次数展示最热门的主题；随机展示标签；展示站点的简单统计数据

6.页面底部的“网站地图”，可查看sitemap，sitemap每30分钟更新一次

7.QQ和新浪微博账号可直接注册登录


