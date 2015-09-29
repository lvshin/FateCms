package fate.webapp.blog.api.open;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Advertisement;
import fate.webapp.blog.model.Comments;
import fate.webapp.blog.model.Forum;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Theme;
import fate.webapp.blog.model.ThirdPartyAccess;
import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.model.VoteRecord;
import fate.webapp.blog.service.AdvertisementService;
import fate.webapp.blog.service.CommentsService;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.ThemeService;
import fate.webapp.blog.service.ThemeTagService;
import fate.webapp.blog.service.ThirdPartyAccessService;
import fate.webapp.blog.service.UserService;
import fate.webapp.blog.service.VoteRecordService;
import fate.webapp.blog.utils.FilterHTMLTag;

@Controller
@RequestMapping("")
public class ThemeCtl {

	private Logger log = Logger.getLogger(ThemeCtl.class);
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private CommentsService commentsService;
	
	@Autowired
	private VoteRecordService voteRecordService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ThemeTagService themeTagService;
	
	@Autowired
	private AdvertisementService advertisementService; 
	
	@Autowired
    private ThirdPartyAccessService thirdPartyAccessService;
	
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
			"Alibaba",//阿里云云盾对机子的安全扫描
			"AhrefsBot",
			"Renren Share Slurp",//人人分享
			"SinaWeiboBot",//新浪微博分享
			"masscan"//Masscan 扫描器
			
	};
	
	/**
	 * 判断是否是蜘蛛来访
	 * @param request
	 * @return
	 */
	public boolean isSpider(HttpServletRequest request){
		String agent = request.getHeader("User-Agent");
		int i = 0;
		boolean flag = false;
		if(agent==null)
			return true;
		for(i=0;i<spider_key.length;i++){
			if(agent.trim().toLowerCase().contains(spider_key[i].toLowerCase()))
				break;
		}
		if(i<spider_key.length){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 左边为新url，右边为了让搜索引擎也能访问到
	 * @param year
	 * @param month
	 * @param date
	 * @param title
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({"/{year}/{month}/{date}/{title}.html","/op/theme/{year}/{month}/{date}/{title}.html"})
	public ModelAndView detail(@PathVariable("year") String year, @PathVariable("month") String month,@PathVariable("date") String date, @PathVariable("title") String title, HttpServletRequest request, HttpServletResponse response){
		String referer = request.getHeader("Referer");
		String day = year+"-"+month+"-"+date;
		 GlobalSetting globalSetting = GlobalSetting.getInstance();
		Theme theme = themeService.findByDateAndTitle(day, title, globalSetting.getRedisOpen());
		if(theme==null){
			try {
				response.sendError(404);//发送404
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		String jsp = null;
		switch (theme.getType()) {
		case 0:jsp = "theme/theme";break;
		case 1:jsp = "theme/audioTheme";break;
		case 2:jsp = "theme/videoTheme";break;

		default:jsp = "theme/theme";break;
		}
		ModelAndView mv = new ModelAndView(jsp);
		if(referer!=null&&referer.contains("op/search")){
			theme.setSearch(theme.getSearch()+1);
		}
		UserSession userSession = (UserSession) request.getSession(false).getAttribute("userSession");
		//主题不是草稿状态并且当前用户不是管理员
		if(!isSpider(request)&&theme.getState()!=Theme.STATE_EDIT&&(userSession==null||(userSession!=null&&userSession.getUser().getUserType()==User.USER_TYPE_NORMAL)))
			theme.setViews(theme.getViews()+1);
		theme.setReplies(theme.getDuoShuos().size());
		theme = themeService.update(theme, globalSetting.getRedisOpen());
		
		Advertisement advertisement = advertisementService.findLastByType(Advertisement.TYPE_INSIDE);
		
		ThirdPartyAccess weibo = thirdPartyAccessService.findByType(ThirdPartyAccess.TYPE_XINLANG);
        mv.addObject("weibo", weibo==null?"":weibo.getAccessKey());
		
		mv.addObject("theme", theme);
		mv.addObject("desc", FilterHTMLTag.delHTMLTag(theme.getContent()));
		mv.addObject("sid", request.getSession(false).getId());
		mv.addObject("adv", advertisement);
		return mv;
	}
	
	@RequestMapping("/theme/addComment")
	@ResponseBody
	public Object addComment(String commentContent,String themeGuid,String commentGuid,Integer toUid, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		Comments comments = new Comments();
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		try{
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			if(!(userSession.getUser().getEmailStatus()||userSession.getUser().getMobileStatus())&&userSession.getType()==0){
				map.put("success", false);
				map.put("msg", "您的帐号处于未验证状态，请先验证您的邮箱/手机！");
				return map;
			}
			if(themeGuid!=null)
				comments.setTheme(themeService.find(themeGuid, globalSetting.getRedisOpen()));
			else if(commentGuid!=null){
				comments.setCommentParent(commentsService.find(commentGuid));
				if(toUid!=0)
					comments.setTo(userService.find(toUid));
			}
			else{
				map.put("success", false);
				map.put("msg", "数据异常");
				return map;
			}
			comments.setCommentContent(commentContent);
			comments.setUser(userSession.getUser());
			commentsService.save(comments);
			themeService.update(themeService.find(themeGuid, globalSetting.getRedisOpen()), globalSetting.getRedisOpen());
			map.put("success", true);
			map.put("msg", "评论成功");
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "未知错误");
		}
		
		return map;
	}
	
	@RequestMapping("/theme/addTheme")
	public ModelAndView addTheme(@RequestParam(defaultValue = "0")int fid,String tid,HttpSession session){
		ModelAndView mv = new ModelAndView("theme/addTheme");
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		 GlobalSetting globalSetting = GlobalSetting.getInstance();
		Forum forum = null;
		if(tid!=null){
			Theme theme = themeService.find(tid, globalSetting.getRedisOpen());
			if(theme!=null){
				if(theme.getAuthorId()!=userSession.getUser().getUid()){
					mv.addObject("success", false);
					mv.addObject("msg", "您没有编辑该主题的权限");
					return mv;
				}
				forum = theme.getForum();
				mv.addObject("theme", theme);
			}
			else
				forum = forumService.find(fid);
		}
		else
			forum = forumService.find(fid);
		if(forum==null||forum.getType()==Forum.TYPE_REGION){
			mv.addObject("success", false);
			mv.addObject("msg", "所选版块不存在");
		}else{
			mv.addObject("success", true);
			mv.addObject("forum", forum);
		}
		return mv;
	}
	
	@RequestMapping("/op/theme/comment/vote")
	@ResponseBody
	public Object vote(int type,String guid, int voteType, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		 GlobalSetting globalSetting = GlobalSetting.getInstance();
		try{
			if((type!=1&&type!=2)||(guid==null||guid.trim().equals(""))){
				map.put("success", false);
				map.put("msg", "数据异常");
				return map;
			}
			if(!voteRecordService.exists(session.getId(), guid, type)){
				VoteRecord voteRecord = new VoteRecord();
				voteRecord.setSessionId(session.getId());
				int up = 0;
				int down = 0;
				/*投票*/
				if(type==1){
					Theme theme = themeService.find(guid, globalSetting.getRedisOpen());
					voteRecord.setTheme(theme);
					if(voteType==1)
						theme.setUp(theme.getUp()+1);
					else if(voteType==2)
						theme.setDown(theme.getDown()+1);
					up = theme.getUp();
					down = theme.getDown();
					themeService.update(theme, globalSetting.getRedisOpen());
				}
				else if(type==2){
					Comments comments = commentsService.find(guid);
					voteRecord.setComments(comments);
					if(voteType==1)
						comments.setUp(comments.getUp()+1);
					else if(voteType==2)
						comments.setDown(comments.getDown()+1);
					up = comments.getUp();
					down = comments.getDown();
					commentsService.update(comments);
				}
				voteRecordService.save(voteRecord);
				map.put("success", true);
				map.put("up", up);
				map.put("down", down);
			}else{
				map.put("success", false);
				map.put("msg", "请勿重复投票");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "未知错误");
		}
		return map;
	}
	
	@RequestMapping("/tag/{tagName}")
	public ModelAndView tags(@PathVariable("tagName")String tagName, @RequestParam(defaultValue = "1")int curPage){
		ModelAndView mv = new ModelAndView("tag/list");
		List<Theme> themes = themeService.pageByTag(tagName, Constants.THEME_LIST_LENGTH, curPage, false);
		long count = themeService.countByTag(tagName, false);
		mv.addObject("themes", themes);
		mv.addObject("count", count);
		mv.addObject("tagName", tagName);
		mv.addObject("curPage", curPage);
		mv.addObject("tag", tagName);
		return mv;
	}
}
