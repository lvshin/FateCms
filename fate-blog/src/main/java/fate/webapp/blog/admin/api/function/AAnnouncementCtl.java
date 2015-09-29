package fate.webapp.blog.admin.api.function;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import fate.webapp.blog.model.Announcement;
import fate.webapp.blog.model.Index;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.service.AnnouncementService;

/**
 * 公告
 * @author Fate
 *
 */
@Controller
@RequestMapping("/admin/announcement")
public class AAnnouncementCtl {

    private static final Logger log = Logger.getLogger(AAnnouncementCtl.class);
    
	@Autowired
	private AnnouncementService announcementService;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue = "1") int curPage) {
		ModelAndView mv = new ModelAndView("admin/function/announcement/list");
		List<Announcement> list = announcementService.page(curPage, Constants.ANNOUNCEMENT_PAGE_SIZE);
		long count = announcementService.count();
		mv.addObject("list", list);
		mv.addObject("curPage", curPage);
		mv.addObject("count", count);
		mv.addObject("pageSize", Constants.ANNOUNCEMENT_PAGE_SIZE);
		return mv;
	}

	@RequestMapping("/new")
	public ModelAndView newAnnouncement(@RequestParam(defaultValue = "0")int id) {
		ModelAndView mv = new ModelAndView("admin/function/announcement/new");
		if (id != 0) {
			Announcement announcement = announcementService.find(id);
			mv.addObject("announcement", announcement);
			mv.addObject("operation", "编辑");
			return mv;
		}
		mv.addObject("operation", "新增");
		return mv;
	}

	@RequestMapping("/submit")
	@ResponseBody
	public Object submit(Announcement announcement,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Announcement a = null;
			if (announcement.getId() != 0) {
				a = announcementService.find(announcement.getId());
				a.setDisplayOrder(announcement.getDisplayOrder());
				a.setTitle(announcement.getTitle());
				a.setStartTime(announcement.getStartTime());
				a.setEndTime(announcement.getEndTime());
				a.setContent(announcement.getContent());
				a.setDisplayOrder(announcement.getDisplayOrder());
				announcementService.update(a);
			} else {
				UserSession userSession = (UserSession) session.getAttribute("userSession");
				announcement.setAnthor(userSession.getUser());
				announcementService.save(announcement);
			}
			Index index = Index.getInstance();
			index.setAnnouncement(announcementService.findLast());
			map.put("success", true);
			map.put("msg", "保存成功");
		} catch (Exception e) {
			log.error("公告保存失败", e);
			map.put("success", false);
			map.put("msg", "保存失败");
		}
		return map;
	}
	
	@RequestMapping("/del")
    @ResponseBody
    public Object del(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Announcement announcement = announcementService.find(id);
            announcementService.delete(announcement);
            Index index = Index.getInstance();
            index.setAnnouncement(announcementService.findLast());
            map.put("success", true);
            map.put("msg", "保存成功");
        } catch (Exception e) {
            log.error("公告删除失败", e);
            map.put("success", false);
            map.put("msg", "保存失败");
        }
        return map;
    }

	/**
	 * 表单提交日期绑定
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
}
