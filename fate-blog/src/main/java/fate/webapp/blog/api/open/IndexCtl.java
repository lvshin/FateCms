package fate.webapp.blog.api.open;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Forum;
import fate.webapp.blog.model.FriendLink;
import fate.webapp.blog.model.Index;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.model.Theme;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.FriendLinkService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.service.ThemeService;
import fate.webapp.blog.utils.ClientInfo;
import fate.webapp.blog.utils.FilterHTMLTag;

@Controller
@RequestMapping("/")
public class IndexCtl {

	private static final Logger LOG = Logger.getLogger(IndexCtl.class);
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private FriendLinkService friendLinkService;
	
	@RequestMapping("/")
	public ModelAndView index(@RequestParam(defaultValue = "1")int curPage, HttpServletRequest request, HttpSession session){
		ModelAndView mv = new ModelAndView("index");
		LOG.info("IP："+ClientInfo.getIp(request)+" \""+request.getHeader("User-Agent")+"\" 进入了网站首页");
		Index index = Index.getInstance();
		if(index.getTitle()==null){
			//原本论坛首页的
			List<Forum> forums = forumService.searchRoot();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//			for (Forum f : forums) {
//				list.add(forumToJson(f));
//			}
			if(forums.size()>2)
			    list.add(forumToJson(forums.get(2)));
			index.setList(list);
			LOG.info("查询SEO");
			Param title = paramService.findByKey(Constants.SEO_INDEX_TITLE);
			Param keywords = paramService.findByKey(Constants.SEO_INDEX_KEYWORDS);
			Param description = paramService.findByKey(Constants.SEO_INDEX_DESCRIPTION);
			
			index.setTitle(title!=null?title.getTextValue():"");
			index.setKeywords(keywords!=null?keywords.getTextValue():"");
			index.setDescription(description!=null?description.getTextValue():"");
		}
		Map<Integer,List<Theme>> list = index.getThemes();
		if(list.get(curPage)==null){
		    LOG.info("查询文章列表");
			List<Theme> themes = themeService.pageByFid(0, Constants.INDEX_LIST_LENGTH, curPage, false, true, false, Theme.STATE_PUBLISH);
			
			for(Theme theme:themes){
				String c = FilterHTMLTag.delHTMLTag(theme.getContent());
				theme.setContent((c.length()>200?c.substring(0, 200):c)+"...");
				theme.setReplies(theme.getDuoShuos().size());
			}
			list.put(curPage, themes);
		}
		if(index.getCount()==0){
		    LOG.info("查询文章总数");
			index.setCount(themeService.count(0, false, Theme.STATE_PUBLISH));
		}
		if(index.getFriendLinks().isEmpty()){
			index.setFriendLinks(friendLinkService.searchByState(FriendLink.STATE_PASS));
		}
		
		mv.addObject("themes", index.getThemes().get(curPage));
		mv.addObject("curPage", curPage);
		mv.addObject("count", index.getCount());
		mv.addObject("pageSize", Constants.INDEX_LIST_LENGTH);
//		mv.addObject("forums", index.getList());
		mv.addObject("title", index.getTitle());
		mv.addObject("keywords", index.getKeywords());
		mv.addObject("description", index.getDescription());
		mv.addObject("friendLinks", index.getFriendLinks());
//		mv.addObject("sid", session.getId());
		return mv;
	}
	
	@RequestMapping("/nana")
	public ModelAndView nana(){
		ModelAndView mv = new ModelAndView("index2");
		Index index = Index.getInstance();
		mv.addObject("forums", index.getList());
		return mv;
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
		if (forum.getChildForums() != null && !forum.getChildForums().isEmpty()) {

			for (Forum f : forum.getChildForums()) {
				list.add(forumToJson(f));
			}
		}
		map.put("children", list);
		return map;
	}
	
	/**
	 * 获取文章内容中的图片地址
	 * @param content
	 * @return
	 */
	public String getImg(String content){
		content = content.substring(content.indexOf("<img")+4);
		if(content.indexOf("\"")==-1){
			return "";
		}
		content = content.substring(content.indexOf("src=")+5);
		if(content.indexOf("\"")==-1){
			return "";
		}
		content = content.substring(0,content.indexOf("\""));
		return content;
	}
	
}
