package fate.webapp.blog.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.listener.SiteStatistics;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Spider;
import fate.webapp.blog.service.ExceptionLogService;
import fate.webapp.blog.service.SpiderService;
import fate.webapp.blog.utils.ClientInfo;

public class SpiderInterceptor implements HandlerInterceptor {

    private static final Logger LOG = Logger.getLogger(SpiderInterceptor.class);
    
	@Autowired
	private SpiderService spiderService;
	
	@Autowired
	private ExceptionLogService exceptionLogService;
	
	private String[] spider_key = {
			"googlebot",
			"mediapartners-google",
			"feedfetcher-Google",
			"ia_archiver",
			"iaarchiver",
			"sqworm",
			"baiduspider",
			"msnbot",
			"yodaobot",
			"yahoo! slurp;",
			"yahoo! slurp china;",
			"yahoo",
			"iaskspider",
			"sogou spider",
			"sogou web spider",
			"sogou push spider",
			"sogou orion spider",
			"sogou-test-spider",
			"sogou+head+spider",
			"sohu",
			"sohu-search",
			"Sosospider",
			"Sosoimagespider",
			"JikeSpider",
			"360spider",
			"qihoobot",
			"tomato bot",
			"bingbot",
			"youdaobot",
			"askjeeves/reoma",
			"manbot",
			"robozilla",
			"MJ12bot",
			"HuaweiSymantecSpider",
			"Scooter",
			"Infoseek",
			"ArchitextSpider",
			"Grabber",
			"Fast",
			"ArchitextSpider",
			"Gulliver",
			"Lycos",
			"YisouSpider",
			"YYSpider",
			"360JK",//360监控
			"Baidu-YunGuanCe-Bot",//百度云观测
			"Baidu-YunGuanCe-SLABot",
			"Alibaba",//阿里云云盾对机子的安全扫描
			"AhrefsBot",
			"Renren Share Slurp",//人人分享
			"SinaWeiboBot",//新浪微博分享
			"masscan"//Masscan 扫描器
			
	};
	private String[] spider_name = {
			"Google",
			"Google Adsense",
			"Google",
			"Alexa",
			"Alexa",
			"AOL",
			"Baidu",
			"MSN",
			"Yodao",
			"Yahoo!",
			"Yahoo! China",
			"Yahoo!",
			"Iask",
			"Sogou",
			"Sogou",
			"Sogou",
			"Sogou",
			"Sogou",
			"Sogou",
			"Sohu",
			"Sohu",
			"SoSo",
			"SoSo",
			"Jike",
			"360",
			"360",
			"Tomato",
			"Bing",
			"Yodao",
			"Ask",
			"Bing",
			"Dmoz",
			"MJ12",
			"HuaweiSymantec",
			"AltaVista",
			"Infoseek",
			"Excite",
			"DirectHit",
			"Fast",
			"WebCrawler",
			"NorthernLight",
			"Lycos",
			"宜搜搜索",
			"YYSpider"
	};
	
	/**
	 * 判断是否是蜘蛛来访
	 * @param request
	 * @return
	 */
	public String isSpider(HttpServletRequest request){
		String agent = request.getHeader("User-Agent");
		int i = 0;
		String spiderName = null;
//		System.out.println(agent);
		if(agent==null)
			return "模拟访问";
		for(i=0;i<spider_key.length;i++){
			if(agent.trim().toLowerCase().contains(spider_key[i].toLowerCase()))
				break;
		}
		if(i<spider_name.length){
			if(!request.getServletPath().contains("WEB-INF")&&!request.getServletPath().contains("js")&&!request.getServletPath().contains("css")){
				Spider spider = new Spider();
				spider.setSpiderIp(ClientInfo.getIp(request));
				spider.setSpiderName(spider_name[i]);
				spider.setSpiderTime(new Date());
				spider.setSpiderUrl(request.getScheme()+"://"+request.getServerName()+request.getContextPath() + request.getServletPath());
				spiderService.save(spider);
			}
			spiderName = spider_name[i];
		}else if(i<spider_key.length){
			spiderName = spider_key[i];
		}
		return spiderName;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception e)
			throws Exception {
		// TODO Auto-generated method stub
		String spider = isSpider(request);
		if(spider!=null){
			HttpSession session = request.getSession(false);
			if(session!=null&&session.getAttribute("checked")==null){
				SiteStatistics.reduce();
				session.setAttribute("checked", "checked");
			}
		}else{
//			System.out.println(request.getHeader("User-Agent"));
		}
		if(e!=null)
		    LOG.info(e.getMessage());
		String agent = request.getHeader("User-Agent");
		//判断是不是404，是则做记录
		switch (response.getStatus()) {
		case 400:
		case 404:
			exceptionLogService.save(response.getStatus(), request.getRequestURL()+(request.getQueryString()!=null?("?"+request.getQueryString()):""), spider==null?"用户访问":spider, agent, "");
			break;
		case 500:
		case 502:
		case 503:
		case 504:
			exceptionLogService.save(response.getStatus(), request.getRequestURL()+(request.getQueryString()!=null?("?"+request.getQueryString()):""), spider==null?"用户访问":spider, agent, e.getMessage());
			break;

		default:
			break;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		String appUrl = globalSetting.getAppUrl();
//		String referer = request.getHeader("Referer");
		if(appUrl!=null&&!request.getServletPath().contains("WEB-INF")&&!request.getServerName().equals(appUrl)){
			response.setStatus(301);
			String url = "http://"+appUrl+request.getRequestURI();
			if(request.getQueryString()!=null)
				url += "?"+request.getQueryString();
			response.setHeader("Location", url);
			response.setHeader( "Connection", "close" );
			return false;
		}
		//为了百度上除掉死链
		if(request.getQueryString()!=null&&(request.getQueryString().contains("p=")||request.getQueryString().contains("s=")||request.getQueryString().contains("null"))){
			response.setStatus(404);
			response.setHeader( "Connection", "close" ); 
			return false;
		}
		return true;
	}

}
