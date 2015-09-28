package fate.webapp.blog.admin.api.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Advertisement;
import fate.webapp.blog.model.Index;
import fate.webapp.blog.service.AdvertisementService;

@Controller
@RequestMapping("/admin/advertisement")
public class AAdvertisementCtl {

	@Autowired
	private AdvertisementService advertisementService;
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue = "1")int curPage){
		ModelAndView mv = new ModelAndView("admin/advertisement/list");
		List<Advertisement> list = advertisementService.page(curPage, Constants.ADVERTISEMENT_PAGE_SIZE);
		String[] advNames = {"页面底部广告","主题页右侧导航广告","主题文内广告","页面两侧空隙的悬浮广告"};
		long count = advertisementService.count();
		mv.addObject("list", list);
		mv.addObject("curPage", curPage);
		mv.addObject("count", count);
		mv.addObject("pageSize", Constants.ADVERTISEMENT_PAGE_SIZE);
		mv.addObject("advNames", advNames);
		return mv;
	}
	
	@RequestMapping("/new")
	public ModelAndView newAnnouncement(@RequestParam(defaultValue = "0")int id) {
		ModelAndView mv = new ModelAndView("admin/advertisement/new");
		if (id != 0) {
			Advertisement advertisement = advertisementService.find(id);
			mv.addObject("advertisement", advertisement);
			mv.addObject("operation", "编辑");
			return mv;
		}
		mv.addObject("operation", "新增");
		return mv;
	}

	@RequestMapping("/submit")
	@ResponseBody
	public Object submit(Advertisement advertisement) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Advertisement a = null;
			if (advertisement.getId() != 0) {
				a = advertisementService.find(advertisement.getId());
				a.setName(advertisement.getName());
				a.setCode(advertisement.getCode());
				a.setType(advertisement.getType());
				a.setActive(advertisement.getActive());
				advertisementService.update(a);
			} else {
				advertisementService.save(advertisement);
			}
			Index index = Index.getInstance();
			index.setAdvRight(advertisementService.findLastByType(Advertisement.TYPE_RIGHT));
			index.setAdvBottom(advertisementService.findLastByType(Advertisement.TYPE_BOTTOM));
			map.put("success", true);
			map.put("message", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "保存失败");
		}

		return map;
	}
	
}
