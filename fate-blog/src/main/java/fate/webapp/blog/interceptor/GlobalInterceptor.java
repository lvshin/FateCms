package fate.webapp.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.utils.Strings;

public class GlobalInterceptor implements HandlerInterceptor {

	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		// TODO Auto-generated method stub
		if(!request.getRequestURI().contains("install")){
			GlobalSetting globalSetting = GlobalSetting.getInstance();
			request.getSession().setAttribute("setting", globalSetting);
			if(Strings.isEmpty(globalSetting.getAppName())){
				response.sendRedirect("/install");
				return false;
			}
		}
		return true;
	}

}
