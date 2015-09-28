package fate.webapp.blog.admin.api.statistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fate.webapp.blog.model.Forum;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.ThemeService;
import fate.webapp.blog.utils.DateUtil;

@Controller
@RequestMapping("/admin/statistics/")
public class StatisticsCtl {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ForumService forumService;

    @RequestMapping("/theme")
    public String index() {
        return "admin/statistics/theme";
    }

    @RequestMapping("/getThemeData")
    @ResponseBody
    public Object getThemeData(int dateType) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> legendList = new ArrayList<String>();
        List<String> xAxisList = new ArrayList<String>();

        String[] week = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        String[] month = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        
        List<Forum> forums = forumService.searchChildPoint();
        
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (dateType == 0)
            for (int i = 0; i < 7; i++) {
                if (day > 6){
                    day = 0;
                }
                xAxisList.add(week[day++]);
            }
        else {
            for (int i = 0; i < 12; i++) {
                xAxisList.add(month[i]);
            }
        }
        map.put("xAxis", xAxisList);
        long start = System.currentTimeMillis();
        List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
        for (Forum forum : forums) {
            Map<String, Object> m = new HashMap<String, Object>();

            legendList.add(forum.getForumName());
            List<Long> seriesList = new ArrayList<Long>();
            if (dateType == 0) {
                for (int i = 6; i >= 0; i--)
                    seriesList.add(themeService.statistics(forum.getFid(), dateType, "" + i));
            } else {
                DecimalFormat df1 = new DecimalFormat("00");
                for (int i1 = 1; i1 <= 12; i1++)
                    seriesList.add(themeService.statistics(forum.getFid(), dateType,
                            DateUtil.format(new Date(), "yyyy-") + df1.format(i1)));
            }
            m.put("name", forum.getForumName());
            m.put("type", "bar");
            m.put("data", seriesList);
            series.add(m);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        map.put("legend", legendList);
        map.put("series", series);

        return map;
    }
}
