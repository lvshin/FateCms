package fate.webapp.blog.admin.api.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.service.UserService;
import fate.webapp.blog.service.UserSessionService;
import fate.webapp.blog.utils.EncryptUtil;

@Controller
@RequestMapping("/admin/user")
public class AUserCtl {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSessionService userSessionService;
	
	@RequestMapping("/userList")
	public ModelAndView userList(@RequestParam(defaultValue = "1")int curPage){
		ModelAndView mv = new ModelAndView("admin/function/user/userList");
		List<User> users = userService.page(curPage, 10);
		long count = userService.count();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(User user:users){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("nickName", user.getNickName());
			map.put("activateDate", user.getActivateDate());
			map.put("state", user.getState());
			UserSession userSession = userSessionService.findByUserId(user.getUid());
			map.put("loginDate", userSession.getLoginDate());
			map.put("loginIp", userSession.getLoginIp());
			map.put("headIconUsed", user.getHeadIconUsed());
			list.add(map);
		}
		mv.addObject("users", list);
		mv.addObject("count", count);
		mv.addObject("curPage", curPage);
		mv.addObject("pageSize", 10);
		return mv;
	}
	
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Object resetPwd(int uid){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User user = userService.find(uid);
			user.setPassword(EncryptUtil.pwd(user.getActivateDate(), "e10adc3949ba59abbe56e057f20f883e"));
			userService.update(user);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}
}
