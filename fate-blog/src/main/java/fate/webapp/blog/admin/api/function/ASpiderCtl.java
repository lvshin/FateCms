package fate.webapp.blog.admin.api.function;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Spider;
import fate.webapp.blog.service.SpiderService;

@Controller
@RequestMapping("/admin")
public class ASpiderCtl {

    @Autowired
    private SpiderService spiderService;
    
    @RequestMapping("/spider")
    public ModelAndView spider(@RequestParam(defaultValue = "1")int curPage){
        ModelAndView mv = new ModelAndView("admin/function/spider/list");
        List<Spider> lists = spiderService.page(curPage, Constants.SPIDER_PAGE_SIZE);
        long count = spiderService.count();
        mv.addObject("count", count);
        mv.addObject("lists", lists);
        mv.addObject("curPage", curPage);
        mv.addObject("pageSize", Constants.SPIDER_PAGE_SIZE);
        return mv;
    }
}
