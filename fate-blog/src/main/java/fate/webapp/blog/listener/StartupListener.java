package fate.webapp.blog.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Advertisement;
import fate.webapp.blog.model.Aliyun;
import fate.webapp.blog.model.Forum;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Index;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.model.SecurityVerification;
import fate.webapp.blog.model.Theme;
import fate.webapp.blog.model.ThirdPartyAccess;
import fate.webapp.blog.model.User;
import fate.webapp.blog.service.AdvertisementService;
import fate.webapp.blog.service.AnnouncementService;
import fate.webapp.blog.service.CommentsService;
import fate.webapp.blog.service.DuoShuoService;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.service.SecurityVerificationService;
import fate.webapp.blog.service.ThemeService;
import fate.webapp.blog.service.ThirdPartyAccessService;
import fate.webapp.blog.service.UserService;
import fate.webapp.blog.utils.DateUtil;
import fate.webapp.blog.utils.FilterHTMLTag;
import fate.webapp.blog.utils.FriendLinkUtil;

/**
 * spring加载完成时执行的方法，用于初始化一些参数，启动线程等等
 * @author 幻幻
 *
 */
@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentsService commentsService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private SecurityVerificationService securityVerificationService;
	
	@Autowired
	private AnnouncementService announcementService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private ThirdPartyAccessService thirdPartyAccessService;
	
	@Autowired
	private DuoShuoService duoShuoService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		// TODO Auto-generated method stub
		if (evt instanceof ContextRefreshedEvent) {
            createSitemap();
            startStatistics();
            cleanSecurity();
            initGlobalSetting();
            FriendLinkCheck();
            initOthers();
            Param aliyunUsed = paramService.findByKey(Constants.ALIYUN_USED);
    		if(aliyunUsed!=null&&aliyunUsed.getIntValue()==1)
            	initOSS();
        }
	}
	
	/**
	 * 网站的基础信息在启动的时候导入内存中，只有在基础信息修改的时候才从数据库重新加载。
	 */
	public void initGlobalSetting(){
		Param siteName = paramService.findByKey(Constants.SITE_NAME);
		if(siteName==null)
			siteName = new Param();
		Param appName = paramService.findByKey(Constants.APP_NAME);
		if(appName==null)
			appName = new Param();
		Param appEnName = paramService.findByKey(Constants.APP_EN_NAME);
		if(appEnName==null)
			appEnName = new Param();
		Param appUrl = paramService.findByKey(Constants.APP_URL);
		if(appUrl==null)
			appUrl = new Param();
		Param adminEmail = paramService.findByKey(Constants.ADMIN_EMAIL);
		if(adminEmail==null)
			adminEmail = new Param();
		Param icp = paramService.findByKey(Constants.ICP);
		if(icp==null)
			icp = new Param();
		Param statistics = paramService.findByKey(Constants.STATISTICS);
		if(statistics==null)
			statistics = new Param();
		Param statisticsHead = paramService.findByKey(Constants.STATISTICSHEAD);
		if(statisticsHead==null)
			statisticsHead = new Param();
		Param aliyunUsed = paramService.findByKey(Constants.ALIYUN_USED);
		if(aliyunUsed==null)
			aliyunUsed = new Param();
		Param regAllow = paramService.findByKey(Constants.REG_ALLOW);
		if(regAllow ==null)
			regAllow = new Param();
		Param needEmailVerify = paramService.findByKey(Constants.NEED_EMAIL_VERIFY);
		if(needEmailVerify==null)
			needEmailVerify = new Param();
		Param qq = paramService.findByKey(Constants.QQ);
		if(qq==null)
			qq = new Param();
		Param weibo = paramService.findByKey(Constants.WEIBO);
		if(weibo==null)
			weibo = new Param();
		Param smtp_server = paramService.findByKey(Constants.SMTP_SERVER);
		if(smtp_server==null)
			smtp_server = new Param();
		Param smtp_from = paramService.findByKey(Constants.SMTP_FROM);
		if(smtp_from==null)
			smtp_from = new Param();
		Param smtp_username = paramService.findByKey(Constants.SMTP_USERNAME);
		if(smtp_username==null)
			smtp_username = new Param();
		Param smtp_password = paramService.findByKey(Constants.SMTP_PASSWORD);
		if(smtp_password==null)
			smtp_password = new Param();
		Param smtp_timeout = paramService.findByKey(Constants.SMTP_TIMEOUT);
		if(smtp_timeout==null)
			smtp_timeout = new Param();
		Param siteMode = paramService.findByKey(Constants.SITE_MODE);
		if(siteMode==null)
			siteMode = new Param();
		Param geetestId = paramService.findByKey(Constants.GEETEST_ID);
		if(geetestId==null)
			geetestId = new Param();
		Param geetestKey = paramService.findByKey(Constants.GEETEST_KEY);
		if(geetestKey==null)
			geetestKey = new Param();
		Param duoshuoKey = paramService.findByKey(Constants.DUOSHUO_KEY);
        if(duoshuoKey==null)
            duoshuoKey = new Param();
        Param duoshuoSecret = paramService.findByKey(Constants.DUOSHUO_SECRET);
        if(duoshuoSecret==null)
            duoshuoSecret = new Param();
        Param txAppKey = paramService.findByKey(Constants.TX_APP_KEY);
        if(txAppKey==null)
            txAppKey = new Param();
        
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		globalSetting.setSiteName(siteName.getTextValue());
		globalSetting.setAppName(appName.getTextValue());
		globalSetting.setAppEnName(appEnName.getTextValue());
		globalSetting.setAppUrl(appUrl.getTextValue());
		globalSetting.setAdminEmail(adminEmail.getTextValue());
		globalSetting.setIcp(icp.getTextValue());
		globalSetting.setStatistics(statistics.getTextValue());
		globalSetting.setRegAllow(regAllow.getIntValue()==1);
		globalSetting.setAliyunUsed(aliyunUsed.getIntValue()==1);
		globalSetting.setNeedEmailVerify(needEmailVerify.getIntValue()==1);
		globalSetting.setQq(qq.getIntValue()==1);
		globalSetting.setWeibo(weibo.getIntValue()==1);
		globalSetting.setStatisticsHead(statisticsHead.getTextValue());
		globalSetting.setGeetestId(geetestId.getTextValue());
		globalSetting.setGeetestKey(geetestKey.getTextValue());
		globalSetting.setQqAccess(thirdPartyAccessService.findByType(ThirdPartyAccess.TYPE_QQ));
		globalSetting.setWeiboAccess(thirdPartyAccessService.findByType(ThirdPartyAccess.TYPE_XINLANG));
		globalSetting.setDuoshuoKey(duoshuoKey.getTextValue());
		globalSetting.setDuoshuoSecret(duoshuoSecret.getTextValue());
		globalSetting.setTxAppKey(txAppKey.getTextValue());
		
		//邮件服务
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(smtp_server.getTextValue());
		javaMailSender.setUsername(smtp_username.getTextValue());
		javaMailSender.setPassword(smtp_password.getTextValue());
		javaMailSender.setDefaultEncoding("UTF-8");
		Properties p = new Properties();
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.timeout", smtp_timeout.getTextValue()==null?"":smtp_timeout.getTextValue());
		
		javaMailSender.setJavaMailProperties(p);
		globalSetting.setJavaMailSender(javaMailSender);
		globalSetting.setSmtpFrom(smtp_from.getTextValue());
	}
	
	public void initOthers(){
		Index index = Index.getInstance();
		index.setAnnouncement(announcementService.findLast());//公告
		index.setAdvRight(advertisementService.findLastByType(Advertisement.TYPE_RIGHT));//右侧广告
		index.setAdvBottom(advertisementService.findLastByType(Advertisement.TYPE_BOTTOM));//底部广告
		Param logId= paramService.findByKey(Constants.LOG_ID);
		if(logId==null){
			logId = new Param();
			logId.setType(Param.TYPE_TEXT);
			logId.setKey(Constants.LOG_ID);
			logId.setTextValue("0");
			logId = paramService.update(logId);
		}
		
		index.setLogId(logId);
	}

	/**
	 * 初始化阿里云的oss
	 */
	public void initOSS(){
		Param aliyunUsed = paramService.findByKey(Constants.ALIYUN_USED);
		if(aliyunUsed.getIntValue()==1){
			Param accessKeyId = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_ID);
			Param accessKeySecret = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_SECRET);
			Aliyun aliyun = Aliyun.getInstance();
			aliyun.init(accessKeyId.getTextValue(), accessKeySecret.getTextValue());
			Param ossEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
			Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
			Param ossUrl = paramService.findByKey(Constants.OSS_URL);
			if(ossEndpoint!=null&&!ossEndpoint.getTextValue().equals(""))
				aliyun.initOSS(ossEndpoint.getTextValue(), ossUrl.getTextValue(), ossBucket.getTextValue(), ossEndpoint.getTextValue());
			Param openSearchEndpoint = paramService.findByKey(Constants.OPENSEARCH_ENDPOINT);
			Param openSearchAppName = paramService.findByKey(Constants.OPENSEARCH_APPNAME);
			if(openSearchEndpoint!=null&&!openSearchEndpoint.getTextValue().equals(""))
				aliyun.initOpenSearch(openSearchEndpoint.getTextValue(), openSearchAppName.getTextValue());
		}
	}
	
	/**
	 * 清理无效的验证码
	 */
	public void cleanSecurity(){
		Timer timer = new Timer("cleanSecurity", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	List<SecurityVerification> list = securityVerificationService.findAll();
            	for(SecurityVerification sv:list){
            		Date now = new Date();
            		long time = now.getTime() - sv.getVerificationTime().getTime();
            		if(time>sv.getTimeout()*60*1000){
            			if(sv.getUser()!=null){
            				User user = sv.getUser();
            				user.setPasswordLock(true);
            				userService.update(user);
            			}
            			securityVerificationService.delete(sv);
            		}
            	}

            }
        }, 1000, 1800000);//每30分钟清理无效的验证码
	}
	
	/**
	 * 每天检测一遍友链
	 */
	public void FriendLinkCheck(){
		Timer timer = new Timer("friendLinkCheck", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	GlobalSetting globalSetting = GlobalSetting.getInstance();
            	Index index = Index.getInstance();
            	if(globalSetting.getAppUrl()!=null){
            	    index.setFriendLinkCheck(FriendLinkUtil.checkLink("http://"+globalSetting.getAppUrl()));
            	}
            }
        }, 60000, 24*3600000);//每天更新互链情况
	}
	
	/**
	 * 每隔一段时间对网站数据做次统计
	 */
	private void startStatistics(){
		Timer timer = new Timer("startStatistics", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	SiteStatistics.setTheme(themeService.count(0, false, Theme.STATE_PUBLISH));
                SiteStatistics.setUser(userService.count());
                SiteStatistics.setComment(duoShuoService.count());
                SiteStatistics.setViews(themeService.countViews(0, false, Theme.STATE_PUBLISH));
                Param param = paramService.findByKey(Constants.HISTORY_ONLINE);
                if(param==null){
                	param = new Param();
                	param.setKey(Constants.HISTORY_ONLINE);
                	param.setType(Param.TYPE_INT);
                }
                if(SiteStatistics.getOnline()>param.getIntValue()){
                	param.setIntValue((int) SiteStatistics.getOnline());
                	paramService.update(param);
                }
                SiteStatistics.setHistrry_online(param.getIntValue());
                SiteStatistics.setUpdateTime(new Date());
                Param search = paramService.findByKey(Constants.SEARCH_COUNT);
                if(search!=null)
                	SiteStatistics.setSearch(search.getIntValue());
                else{
                	search = new Param();
                	search.setKey(Constants.SEARCH_COUNT);
                	search.setIntValue(0);
                	search.setType(Param.TYPE_INT);
                	paramService.update(search);
                }
                //最热的主题
                Index index = Index.getInstance();
        		index.setHot(themeService.pageHot(5, 1, false, Theme.STATE_PUBLISH));
        		index.setSearchHot(themeService.pageSearchHot(5, 1, false, Theme.STATE_PUBLISH));
        		//更新首页文章信息
        		Map<Integer,List<Theme>> list1 = new HashMap<Integer, List<Theme>>();
        		for(int i=1;i<=5;i++){
        			List<Theme> themes = themeService.pageByFid(0, Constants.INDEX_LIST_LENGTH, i, false, true, false, Theme.STATE_PUBLISH);
        			for(Theme theme:themes){
        				String c = FilterHTMLTag.delHTMLTag(theme.getContent());
        				theme.setContent((c.length()>200?c.substring(0, 200):c)+"...");
        				theme.setReplies(theme.getDuoShuos().size());
        			}
        			list1.put(i, themes);
        		}
        		index.setThemes(list1);
        		
            }
        }, 1000, 180000);//每3分钟对网站数据做次统计
	}
	
	private void createSitemap() {
        Timer timer = new Timer("createSitemap", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("--->Create sitemap...");
                //创建网站地图
                try {
                	Resource r = new ClassPathResource("log4j.properties");  
                
					File f = r.getFile().getParentFile().getParentFile()  
					        .getParentFile();
					File file = new File(f.getAbsolutePath() + File.separator + "sitemap.xml");
	                System.out.println(file.getAbsolutePath());
	                if(!file.exists()){
	        			file.createNewFile();
	        		}
	                FileWriter fw = new FileWriter(file);
        			fw.write(sitemapEncode());
        			fw.flush();
        			fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
                System.out.println("--->Success create sitemap...");
            }
        }, 1000, 1800000);//每30分钟生成一次网站地图
    }
	
	/**
	 * 生成网站的sitemap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String sitemapEncode() throws UnsupportedEncodingException{
		Document document = DocumentHelper.createDocument();
		Element urlset = document.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
		
		urlset.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		urlset.addAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");
		List<Forum> forums = forumService.searchChildPoint();
		Param appUrl = paramService.findByKey(Constants.APP_URL);
		for(Forum f:forums){
			Element url = urlset.addElement("url");
			url.addElement("loc").addText("http://"+appUrl.getTextValue()+"/op/forum/list?fid="+f.getFid());
			url.addElement("lastmod").addText(f.getLastPost()!=null?DateUtil.format(f.getLastPost().getPublishDate(),"yyyy-MM-dd"):"");
			url.addElement("changefreq").addText("always");
			url.addElement("priority").addText(f.getType()==Forum.TYPE_FORUM?"0.9":"0.8");
		}
		List<Theme> themes = themeService.findAll(false);
		for(Theme t:themes){
			if(t.getState()==Theme.STATE_PUBLISH){
				Element url = urlset.addElement("url");
				url.addElement("loc").addText(t.getUrl());
				url.addElement("lastmod").addText(DateUtil.format(t.getLastModify(),"yyyy-MM-dd"));
				url.addElement("changefreq").addText("hourly");
				url.addElement("priority").addText("0.7");
			}
		}
		return document.asXML();
	}
}
