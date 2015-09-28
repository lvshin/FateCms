package fate.webapp.blog.admin.api.function;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Forum;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Index;
import fate.webapp.blog.model.Media;
import fate.webapp.blog.model.PingRecord;
import fate.webapp.blog.model.Theme;
import fate.webapp.blog.model.ThemeTag;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.MediaService;
import fate.webapp.blog.service.PingRecordService;
import fate.webapp.blog.service.ThemeService;
import fate.webapp.blog.service.ThemeTagService;
import fate.webapp.blog.service.UserSessionService;
import fate.webapp.blog.utils.DateUtil;
import fate.webapp.blog.utils.FilterHTMLTag;
import fate.webapp.blog.utils.PingUtils;

@Controller
@RequestMapping("/admin/theme")
public class AThemeCtl {

	private static Logger log = Logger.getLogger(AThemeCtl.class.getName());
	
	@Autowired
	private ThemeService themeService;

	@Autowired
	private UserSessionService userSessionService;


	@Autowired
	private ThemeTagService themeTagService;
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private PingRecordService pingRecordService;
	
	@Autowired
	private MediaService mediaService;

	/**
	 * 发布主题，暂定为管理员功能
	 * @param theme
	 * @param state
	 * @param fid
	 * @param session
	 * @return
	 */
	@RequestMapping("/addTheme")
	@ResponseBody
	public Object addTheme(Theme theme,int state, int fid,String[] files, String[] titles, String[] singers, String[] times, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			if(userSession==null){
				map.put("success", false);
				map.put("msg", "登录超时，请重新登录");
				return map;
			}
			GlobalSetting globalSetting = (GlobalSetting) session.getAttribute("setting");
			Forum forum = forumService.find(fid);
			if(forum==null){
				map.put("success", false);
				map.put("msg", "版块不存在");
				return map;
			}
			theme.setForum(forum);
			theme.setAuthor(userSession.getUser().getNickName());
			theme.setAuthorId(userSession.getUser().getUid());
			theme.setState(state);
			
			String tag2 = "";
			/* 把文章的标签存入数据库以便下次直接点击 */
			if (theme.getTags() != null
					&& !theme.getTags().trim().equals("")) {
				String[] tags = theme.getTags().split(",");
				Set<String> set = new HashSet<String>();
				/* 遍历标签，如果已存在则更新时间；不存在则创建 */
				for (String tag : tags) {
					if (!themeTagService.exits(tag, userSession.getUser().getUid())) {
						ThemeTag themeTag = new ThemeTag();
						themeTag.setTagName(tag.trim());
						themeTag.setLastUsed(new Date());
						themeTag.setUser(userSession.getUser());
						themeTagService.save(themeTag);
					} else {
						ThemeTag themeTag = themeTagService
								.findByNameAndUser(tag, userSession.getUser()
										.getUid());
						themeTag.setLastUsed(new Date());
						themeTagService.update(themeTag);
					}
					set.add(tag);
				}
				/* 通过Set删除重复的标签 */
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					tag2 += it.next() + ",";
				}
				tag2 = tag2.substring(0, tag2.lastIndexOf(","));
				theme.setTags(tag2);
			}
				theme.setTitle(theme.getTitle().trim());
				/* 从前台传的数据相当于新建了一个文章，会覆盖旧数据，所以得从数据库中取 */
				if (theme.getGuid() != null && !theme.getGuid().equals("")) {
					Theme a = themeService.find(theme.getGuid());
					a.setTitle(theme.getTitle());
					a.setContent(theme.getContent());
//					a.setThemeType(theme.getThemeType());
					a.setPublishDate(theme.getPublishDate());
					a.setLastModify(new Date());
					a.setType(theme.getType());
					a.setState(state);
					a.setTags(tag2);
					theme = a;
				}
				
				if(theme.getPublishDate()==null)
				    theme.setPublishDate(new Date());
				
				String year = DateUtil.format(theme.getPublishDate(),
						"yyyy");
				String month = DateUtil.format(theme.getPublishDate(),
						"MM");
				String day = DateUtil.format(theme.getPublishDate(),
						"dd");
				theme.setUrl("http://"+globalSetting.getAppUrl() + "/" + year + "/" + month + "/"
						+ day + "/" + URLEncoder.encode(theme.getTitle(),"utf-8").replace("+", "%20") + ".html");
			theme = themeService.update(theme);
			if(theme.getType()==Theme.TYPE_VIDEO&&files!=null)
			for(String media:files){
				Media m = mediaService.findByUrl(media);
				if(m==null)
					m = new Media();
				m.setTheme(theme);
				m.setType(theme.getType());
				m.setUrl(media);
				mediaService.update(m);
			}else if(theme.getType()==Theme.TYPE_AUDIO&&files!=null)
				for(int i=0;i<files.length;i++){
					Media m = mediaService.findByUrl(files[i]);
					if(m==null)
						m = new Media();
					m.setTheme(theme);
					m.setType(theme.getType());
					m.setUrl(files[i]);
					m.setLastTime(times[i]);
					m.setSinger(singers[i]);
					m.setTitle(titles[i]);
					mediaService.update(m);
				}
			if(state==Theme.STATE_PUBLISH){
				//ping百度
				boolean baidu = PingUtils.ping(Constants.BAIDU_PING, theme.getTitle(), "http://"+globalSetting.getAppUrl()+"/", theme.getUrl(), null);
				PingRecord pingRecord = pingRecordService.findByThemeGuid(theme.getGuid());
				if(pingRecord==null){
					pingRecord = new PingRecord();
					pingRecord.setTheme(theme);
				}
				pingRecord.setBaidu(baidu);
				pingRecord.setPingDate(new Date());
				pingRecordService.update(pingRecord);
			}
			
			refresh();
			map.put("url", theme.getUrl());
			map.put("success", true);
			map.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			System.out.println(e.getMessage());
			map.put("success", false);
			map.put("msg", "保存失败");
		}

		return map;
	}
	
	/**
	 * 刷新单例中的数据
	 */
	public void refresh(){
		Index index = Index.getInstance();
		List<Forum> forums = forumService.searchRoot();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		for (Forum f : forums) {
//			list.add(forumToJson(f));
//		}
		list.add(forumToJson(forums.get(2)));
		index.setList(list);
		index.setHot(themeService.pageHot(5, 1, false, Theme.STATE_PUBLISH));
		index.setSearchHot(themeService.pageSearchHot(5, 1, false, Theme.STATE_PUBLISH));
		
		Map<Integer,List<Theme>> list1 = new HashMap<Integer, List<Theme>>();
		List<Theme> themes = themeService.pageByFid(0, Constants.INDEX_LIST_LENGTH, 1, false, true, false, Theme.STATE_PUBLISH);
		for(Theme theme:themes){
			String c = FilterHTMLTag.delHTMLTag(theme.getContent());
			theme.setContent((c.length()>200?c.substring(0, 200):c)+"...");
		}
		list1.put(1, themes);
		index.setThemes(list1);
		index.setCount(themeService.count(0, false, Theme.STATE_PUBLISH));
	}
	
	public Map<String, Object> forumToJson(Forum forum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("forumName", forum.getForumName());
		map.put("type", forum.getType());
		map.put("fid", forum.getFid());
		map.put("forumOrder", forum.getForumOrder());
		map.put("parentFid", forum.getParentForum()==null?0:forum.getParentForum().getFid());
		map.put("forumIcon", forum.getForumIcon());
		map.put("iconWidth", forum.getIconWidth());
		if(forum.getType()!=Forum.TYPE_REGION){
			long count = themeService.count(forum.getFid(), false, Theme.STATE_PUBLISH);
			Theme theme = themeService.getLastestTheme(forum.getFid());
			map.put("theme", count);
			if(theme!=null){
				map.put("title", theme.getTitle());
				map.put("url", theme.getUrl());
				map.put("author", theme.getAuthor());
				map.put("publishDate", theme.getPublishDate());
				map.put("img", getImg(theme.getContent()));
			}
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (forum.getChildForums() != null && forum.getChildForums().size() > 0) {

			for (Forum f : forum.getChildForums()) {
				list.add(forumToJson(f));
			}
		}
		map.put("children", list);
		return map;
	}
	
	public String getImg(String content){
		content = content.substring(content.indexOf("<img")+4);
		if(content.indexOf("\"")==-1)
			return "";
		content = content.substring(content.indexOf("src=")+5);
		if(content.indexOf("\"")==-1)
			return "";
		content = content.substring(0,content.indexOf("\""));
		return content;
	}
	
	@RequestMapping("/updateAll")
	@ResponseBody
	public Object updateAll(HttpSession session) throws IllegalStateException, IOException, DocumentException{
		List<Theme> themes = themeService.findAll(false);
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		for(Theme theme : themes){
			String year = DateUtil.format(theme.getPublishDate(),
					"yyyy");
			String month = DateUtil.format(theme.getPublishDate(),
					"MM");
			String day = DateUtil.format(theme.getPublishDate(),
					"dd");
			theme.setUrl("http://"+globalSetting.getAppUrl() + "/" + year + "/" + month + "/"
					+ day + "/" + URLEncoder.encode(theme.getTitle(),"utf-8").replace("+", "%20") + ".html");
			theme = themeService.update(theme);
			if(theme.getState()==Theme.STATE_PUBLISH){
				//ping百度
				boolean baidu = PingUtils.ping(Constants.BAIDU_PING, theme.getTitle(), "http://"+globalSetting.getAppUrl()+"/", theme.getUrl(), null);
				PingRecord pingRecord = pingRecordService.findByThemeGuid(theme.getGuid());
				if(pingRecord==null){
					pingRecord = new PingRecord();
					pingRecord.setTheme(theme);
				}
				pingRecord.setBaidu(baidu);
				pingRecord.setPingDate(new Date());
				pingRecordService.update(pingRecord);
			}
		}
		refresh();
		return true;
	}

	@RequestMapping("/toEditTheme")
	public ModelAndView toEditTheme(String guid) {
		ModelAndView mv = new ModelAndView("admin/function/theme/editTheme");
		Theme theme = themeService.find(guid);
		mv.addObject("theme", theme);
		return mv;
	}

	@RequestMapping("/editTheme")
	@ResponseBody
	public Object editTheme(String guid, String content, String title,
			int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Theme theme = themeService.find(guid);
//			theme.setThemeType(themeTypeService.find(type));
			theme.setContent(content);
			theme.setTitle(title);
			themeService.update(theme);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "保存失败");
		}
		return map;
	}

	@RequestMapping("/themeList")
	public ModelAndView themeList(@RequestParam(defaultValue = "0") int fid,
			@RequestParam(defaultValue = "1") int curPage) {
		ModelAndView mv = new ModelAndView("admin/function/theme/themeList");
		Forum forum = forumService.find(fid);
		List<Theme> themes = themeService.pageByFid(fid,
				Constants.THEME_PER, curPage, false, true, false, 0);
		List<Forum> forums = forumService.searchChildPoint();
        mv.addObject("forums", forums);
		mv.addObject("themes", themes);
		mv.addObject("forum",forum);
		mv.addObject("curPage", curPage);
		mv.addObject("pageSize", Constants.THEME_PER);
		mv.addObject("count", themeService.count(fid, false, 0));
		return mv;
	}
	
	@RequestMapping("/seoPing")
	public ModelAndView seoPing(@RequestParam(defaultValue = "1")int curPage){
		ModelAndView mv = new ModelAndView("admin/function/theme/seoPing");
		List<PingRecord> list = pingRecordService.findAll(curPage, Constants.PING_PAGE_SIZE);
		long count = pingRecordService.count();
		mv.addObject("pings", list);
		mv.addObject("count", count);
		mv.addObject("pageSize", Constants.PING_PAGE_SIZE);
		mv.addObject("curPage", curPage);
		return mv;
	}
	
	@RequestMapping("/rePing")
	@ResponseBody
	public Object rePing(int id, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PingRecord pingRecord = pingRecordService.find(id);
			GlobalSetting globalSetting = (GlobalSetting) session.getAttribute("setting");
			boolean baidu = PingUtils.ping(Constants.BAIDU_PING, pingRecord.getTheme().getTitle(), "http://"+globalSetting.getAppUrl()+"/", pingRecord.getTheme().getUrl(), null);
			pingRecord.setBaidu(baidu);
			pingRecord.setPingDate(new Date());
			pingRecordService.update(pingRecord);
			map.put("success", baidu);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}

	@RequestMapping("/themeTypeList")
	public ModelAndView themeTypeList() {
		ModelAndView mv = new ModelAndView("admin/function/theme/themeTypeList");
		List<Forum> forums = forumService.searchChildPoint();
		mv.addObject("forums", forums);
//		List<ThemeType> types = themeTypeService.orderByPriority();
//		mv.addObject("types", types);
		return mv;
	}

//	@RequestMapping("/deleteThemeType")
//	@ResponseBody
//	public Object deleteThemeType(int id) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			List<Theme> themes = themeService.findByType(id);
//			for (Theme theme : themes) {
//				theme.setThemeType(null);
//				themeService.update(theme);
//			}
//			ThemeType themeType = themeTypeService.find(id);
//			themeTypeService.delete(themeType);
//			map.put("msg", "删除成功");
//			map.put("success", true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			map.put("success", false);
//			map.put("msg", "删除失败");
//		}
//		return map;
//	}

//	@RequestMapping("/addThemeType")
//	@ResponseBody
//	public Object addThemeType(String typeName,int fid) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		ThemeType themeType = new ThemeType();
//		try {
//			Forum forum = forumService.find(fid);
//			if(forum==null){
//				map.put("success", false);
//				map.put("msg", "所选版块不存在");
//			}
//			themeType.setTypeName(typeName);
//			themeType.setForum(forum);
//			themeTypeService.save(themeType);
//			map.put("themeType", themeType);
//			map.put("success", true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			map.put("success", false);
//			map.put("msg", "新增消息类型失败");
//		}
//		return map;
//	}

//	@RequestMapping("/updateThemeType")
//	@ResponseBody
//	public Object updateThemeType(int[] id, String[] typeName,
//			boolean[] visible, int[] priority) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			for (int i = 0; i < id.length; i++) {
//				ThemeType themeType = new ThemeType();
//				themeType.setId(id[i]);
//				themeType.setTypeName(typeName[i]);
//				themeType.setPriority(priority[i]);
//				themeTypeService.update(themeType);
//			}
//			map.put("msg", "保存成功");
//			map.put("success", true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			map.put("success", false);
//			map.put("msg", "新增消息类型失败");
//		}
//		return map;
//	}

	@RequestMapping("/deleteTheme")
	@ResponseBody
	public Object deleteTheme(String guid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Theme theme = themeService.find(guid);
			theme.setIsDelete(true);
			themeService.update(theme);
			map.put("msg", "删除成功");
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		refresh();
		return map;
	}
	
	@RequestMapping("/multiDeleteTheme")
    @ResponseBody
    public Object multiDeleteTheme(String guids) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            
            List<String> guid = new ArrayList<String>();
            String[] ids = guids.split(",");
            for(String id:ids){
                guid.add(id);
            }
            
            themeService.multiDelete(guid);
            
            map.put("msg", "删除成功");
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "删除失败");
        }
        refresh();
        return map;
    }
	
	@RequestMapping("/crushTheme")
	@ResponseBody
	public Object crushTheme(String guid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Theme theme = themeService.find(guid);
			themeService.crush(theme);
			map.put("msg", "删除成功");
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		refresh();
		return map;
	}

	@RequestMapping("/recycleBin")
	public ModelAndView recycleBin(@RequestParam(defaultValue = "0") int fid,
			@RequestParam(defaultValue = "1") int curPage) {
		ModelAndView mv = new ModelAndView("admin/function/theme/recycleBin");
		List<Theme> themes = themeService.pageByFid(fid,
				Constants.THEME_PER, curPage, true, true, false, 0);
		mv.addObject("themes", themes);
		mv.addObject("curPage", curPage);
        mv.addObject("pageSize", Constants.THEME_PER);
        mv.addObject("count", themeService.count(fid, true, 0));
		return mv;
	}

	@RequestMapping("/restoreTheme")
	@ResponseBody
	public Object restoreTheme(String guid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Theme theme = themeService.find(guid);
			theme.setIsDelete(false);
			themeService.update(theme);
			map.put("msg", "恢复成功");
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "恢复失败");
		}
		return map;
	}

	/**
	 * 置顶
	 * @param guid 主题的id
	 * @param priority 优先级
	 * @return
	 */
	@RequestMapping("/setTop")
	@ResponseBody
	public Object setTop(String guid, int priority){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println(priority);
			Theme theme = themeService.find(guid);
			theme.setPriority(priority);
			themeService.update(theme);
			map.put("msg", (priority==0?"取消":"")+"置顶成功");
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", (priority==0?"取消":"")+"置顶失败");
		}
		return map;
	}
	
	/**
	 * 表单提交日期绑定
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 写上你要的日期格式
//		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

}
