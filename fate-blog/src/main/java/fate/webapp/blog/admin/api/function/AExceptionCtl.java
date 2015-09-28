package fate.webapp.blog.admin.api.function;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.ExceptionLog;
import fate.webapp.blog.service.ExceptionLogService;

@Controller
@RequestMapping("/admin")
public class AExceptionCtl {

    @Autowired
    private ExceptionLogService exceptionLogService;
    
    @RequestMapping("/exception")
    public ModelAndView exception(@RequestParam(defaultValue = "0")int status, @RequestParam(defaultValue = "1")int curPage){
        ModelAndView mv = new ModelAndView("admin/function/exception/list");
        List<ExceptionLog> lists = exceptionLogService.page(status, curPage, Constants.EXCEPTION_LOG_PAGE_SIZE);
        long count = exceptionLogService.count(status);
        mv.addObject("count", count);
        mv.addObject("exceptions", lists);
        mv.addObject("curPage", curPage);
        mv.addObject("pageSize", Constants.EXCEPTION_LOG_PAGE_SIZE);
        return mv;
    }
}
