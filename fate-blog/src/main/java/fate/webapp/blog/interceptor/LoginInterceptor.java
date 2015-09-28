package fate.webapp.blog.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.service.UserSessionService;

public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	UserSessionService userSessionService;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		String bwSessionId = (String) session.getAttribute("bwSessionId");
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String base = request.getContextPath();
		GlobalSetting globalSetting = (GlobalSetting) session.getAttribute("setting");
		boolean flag = false;
//		base = base.substring(0,base.lastIndexOf("/"));
		if(userSession!=null&&bwSessionId!=null && bwSessionId.equals(userSession.getSessionId())){
			//防止在后台url泄露的情况下，用户直接输入地址进入后台，需验证是否有管理员权限
			if(request.getServletPath().contains("/admin")&&userSession.getUser().getUserType()!=User.USER_TYPE_ADMIN)
				response.sendRedirect(base+"/");
			else
				flag = true;
		}
		else
		{
			request.getSession().setAttribute("callback", request.getHeader("REFERER"));
			response.sendRedirect(base+"/");
		}
		if(flag&&userSession.getType()==UserSession.TYPE_LOCAL&&!request.getServletPath().contains("/profile")){
			User user = userSession.getUser();
			if(user.getUserType()!=User.USER_TYPE_ADMIN&&globalSetting.getNeedEmailVerify()&&user.getEmail()!=null&&!user.getEmailStatus()){
				String msg = "您的帐号处于未验证状态，请先验证邮箱！";
				response.sendRedirect(base+"/op/accessDenied?message="+URLEncoder.encode(msg, "utf-8"));
				flag = false;
			}
//			else if(user.getMobile()!=null&&!user.getMobileStatus()){
//				String msg = "您的帐号处于未验证状态，请先验证手机！";
//				response.sendRedirect(base+"/op/accessDenied?message="+URLEncoder.encode(msg, "utf-8"));
//				flag = false;
//			}
		}
		return flag;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
