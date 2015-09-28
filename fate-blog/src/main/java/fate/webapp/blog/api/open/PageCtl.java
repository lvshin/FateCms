package fate.webapp.blog.api.open;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.model.Page;

@Controller
@RequestMapping("/op")
public class PageCtl {
	
	@RequestMapping("/page/{pageStyle}/{curPage}")
	public ModelAndView page(@PathVariable("curPage") int curPage, int pageSize, int count, @PathVariable("pageStyle") int pageStyle){
		Page page = new Page(curPage, pageSize, count);
		page.setType(8);
		ModelAndView mv = new ModelAndView("base/page");
		mv.addObject(page);
		mv.addObject("pageStyle",pageStyle);
		return mv;
	}
	
	@RequestMapping("/staticPage/{pageStyle}/{curPage}")
	public ModelAndView staticPage(@PathVariable("curPage") int curPage, int pageSize, int count, @PathVariable("pageStyle") int pageStyle){
		Page page = new Page(curPage, pageSize, count);
		page.setType(8);
		ModelAndView mv = new ModelAndView("base/staticPage");
		mv.addObject(page);
		mv.addObject("pageStyle",pageStyle);
		return mv;
	}
}
