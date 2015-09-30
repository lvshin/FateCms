package fate.webapp.blog.api.open;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Forum;
import fate.webapp.blog.model.Theme;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.ThemeService;

@Controller
public class ForumCtl {

	private static final Logger LOG = Logger.getLogger(ForumCtl.class);
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ThemeService themeService;
	
	/**
	 * 版块的主题列表页
	 * @param fid 版块id
	 * @param curPage 当前页码
	 * @return
	 */
	@RequestMapping("/op/forum-{fid}-{curpage}.html")
	public ModelAndView list(@PathVariable("fid")int fid,@PathVariable("curpage")int curPage){
		ModelAndView mv = new ModelAndView("forum/list");
		Forum forum = forumService.find(fid);
		mv.addObject("forum", forum);
		List<Theme> themes = themeService.pageByFid(fid, Constants.THEME_LIST_LENGTH, curPage, false, true, true, Theme.STATE_PUBLISH);
		mv.addObject("themes", themes);
		long count = themeService.count(fid, false, Theme.STATE_PUBLISH);
		mv.addObject("count", count);
		mv.addObject("pageSize", Constants.THEME_LIST_LENGTH);
		mv.addObject("curPage", curPage);
		return mv;
	}
	
	/**
	 * 我的主题
	 * @param uid
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/myTheme-{uid}-{curpage}.html")
	public ModelAndView myTheme(@PathVariable("uid")int uid,@PathVariable("curpage")int curPage){
		ModelAndView mv = new ModelAndView("forum/list");
		List<Theme> themes = themeService.pageByUid(uid, Constants.THEME_LIST_LENGTH, curPage, false, true, 0);
		mv.addObject("themes", themes);
		long count = themeService.countByUid(uid, false, 0);
		mv.addObject("count", count);
		mv.addObject("pageSize", Constants.THEME_LIST_LENGTH);
		mv.addObject("curPage", curPage);
		return mv;
	}
	
}
