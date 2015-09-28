package fate.webapp.blog.api.open;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import weibo4j.Account;
import weibo4j.Users;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;

import fate.qq4j.Oauth;
import fate.qq4j.qzone.UserInfo;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.ThirdPartyAccess;
import fate.webapp.blog.model.ThirdPartyAccount;
import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.service.TPAService;
import fate.webapp.blog.service.ThirdPartyAccessService;
import fate.webapp.blog.service.UserService;
import fate.webapp.blog.service.UserSessionService;
import fate.webapp.blog.utils.ClientInfo;
import fate.webapp.blog.utils.TokenUtil;

@Controller
@RequestMapping("/op/login")
public class LoginCtl {
	
	private Logger log = Logger.getLogger(LoginCtl.class);

	@Autowired
	private TPAService tpaService;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private UserService userService;

	@Autowired
	private ThirdPartyAccessService thirdPartyAccessService;

	/**
	 * 进入登录页面，将referer保存下来供登录成功后跳转
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/toLogin")
	public ModelAndView goLogin(HttpServletRequest request,
			HttpServletResponse response, String redirect_to)
			throws IOException {
		String callback = "";
		if (redirect_to == null || redirect_to.trim().equals(""))
			callback = request.getHeader("REFERER");
		else
			callback = redirect_to;
		if (request.getSession().getAttribute("callback") == null)
			request.getSession().setAttribute("callback", callback);
		UserSession userSession = (UserSession) request.getSession()
				.getAttribute("userSession");
		if (userSession != null) {
			response.sendRedirect(callback == null ? "/" : callback);
		}
		ModelAndView mv = new ModelAndView("login/login");
		mv.addObject("ip", ClientInfo.getIp(request));
		return mv;
	}

	/**
	 * 帐号密码登录表单提交
	 * 
	 * @param loginName
	 * @param pwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Object submit(String loginName, String pwd,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		HttpSession session = request.getSession();
		if (!userService.checkLoginName(loginName)) {
			map.put("success", false);
			map.put("msg", "用户名不存在");
			return map;
		}
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		String private_key = globalSetting.getGeetestKey();
		if(private_key!=null){
    		GeetestLib geetest = new GeetestLib(private_key);
    
    		String gtResult = "fail";
    		if (geetest.resquestIsLegal(request)) {
    			gtResult = geetest.enhencedValidateRequest(request);
    			System.out.println(gtResult);
    		} 
    		switch (gtResult) {
    		case "success":break;
    		case "forbidden":
    						map.put("success", false);
    						map.put("msg", "验证码错误");
    						return map;
    		case "fail":
    					map.put("success", false);
    					map.put("msg", "验证码错误");
    					return map;
    		default:
    			break;
    		}
		}
		User user = userService.login(loginName, pwd);
		// 没找到用户则直接返回错误0
		if (user == null) {
			map.put("success", false);
			map.put("msg", "密码错误");
			return map;
		} else {
			userSessionService.login(user, request);

			String callback = (String) session.getAttribute("callback");
			if (callback == null) {
				callback = request.getHeader("REFERER");
			}
			session.removeAttribute("callback");
			if (callback == null || callback.contains("/op/login"))
				callback = "/";
			map.put("callback", callback);
			map.put("success", true);

		}
		return map;
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Object logout(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 清除数据库中的登录状态
			String guid = ((UserSession) session.getAttribute("userSession"))
					.getGuid();
			userSessionService.logout(guid);
			// 清空当前session
			session.invalidate();
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}

	@RequestMapping("/toQQ")
	public void toQQ(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("text/html;charset=utf-8");
		try {
			if (request.getSession().getAttribute("callback") == null) {
				String callback = request.getHeader("REFERER");
				request.getSession().setAttribute("callback", callback);
			}
			ThirdPartyAccess qq = thirdPartyAccessService
					.findByType(ThirdPartyAccess.TYPE_QQ);
			GlobalSetting globalSetting = (GlobalSetting) request.getSession()
					.getAttribute("setting");
			response.sendRedirect(new Oauth().getAuthorizeURL(request,
					qq.getAccessKey(), "http://" + globalSetting.getAppUrl()
							+ "/op/login/QQLogin"));
		} catch (QQConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/QQLogin")
	public void QQLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			log.info("IP:"+ClientInfo.getIp(request)+" \""+request.getHeader("User-Agent")+"\" 使用了QQ登录");
			ThirdPartyAccess qq = thirdPartyAccessService
					.findByType(ThirdPartyAccess.TYPE_QQ);
			GlobalSetting globalSetting = (GlobalSetting) request.getSession()
					.getAttribute("setting");
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(
					request, qq.getAccessKey(), qq.getAccessSecret(), "http://"
							+ globalSetting.getAppUrl() + "/op/login/QQLogin");
			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;
			System.out.println("getin");
			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				System.out.print("没有获取到响应参数");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				request.getSession().setAttribute("token_expirein",
						String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();

				request.getSession().setAttribute("openId", openID);
				request.getSession().setAttribute("loginType",
						UserSession.TYPE_QQ);
				// 利用获取到的accessToken 去获取当前用户的openid --------- end
				// 为空代表首次登录，此处获取的信息尚未完全
				ThirdPartyAccount tpa = tpaService.findByOpenId(openID);
				if (tpa == null) {
					// 获取用户QQ空间的信息
					UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
					UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo(qq.getAccessKey());
					if (userInfoBean != null) {
						tpa = new ThirdPartyAccount();
						tpa.setOpenId(openID);
						tpa.setAccountType(UserSession.TYPE_QQ);
						tpa.setAccessToken(accessToken);
						User user = new User();
						user.setNickName(userInfoBean.getNickname());
						user.setActivateDate(new Date());
						user.setHeadIconSmall(userInfoBean.getAvatar()
								.getAvatarURL30());
						user.setHeadIconMid(userInfoBean.getAvatar()
								.getAvatarURL50());
						user.setHeadIconBig(userInfoBean.getAvatar()
								.getAvatarURL100());
						user.setHeadIconUsed(User.HEADICON_QQ);
						user = userService.update(user);
						tpa.setHeadIconBig(userInfoBean.getAvatar()
								.getAvatarURL100());
						tpa.setHeadIconMid(userInfoBean.getAvatar()
								.getAvatarURL50());
						tpa.setHeadIconSmall(userInfoBean.getAvatar()
								.getAvatarURL30());
						tpa.setUser(user);
						tpa = tpaService.update(tpa);
					}
				}

				fate.qq4j.weibo.UserInfo weiboUserInfo = new fate.qq4j.weibo.UserInfo(
						accessToken, openID);
				com.qq.connect.javabeans.weibo.UserInfoBean weiboUserInfoBean = weiboUserInfo
						.getUserInfo(qq.getAccessKey());
				response.sendRedirect("callback");
			}
		} catch (QQConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/toWeibo")
	public void toWeibo(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("text/html;charset=utf-8");
		try {
			if (request.getSession().getAttribute("callback") == null) {
				String callback = request.getHeader("REFERER");
				request.getSession().setAttribute("callback", callback);
			}
			ThirdPartyAccess xinlang = thirdPartyAccessService
					.findByType(ThirdPartyAccess.TYPE_XINLANG);
			GlobalSetting globalSetting = (GlobalSetting) request.getSession()
					.getAttribute("setting");
			response.sendRedirect(new weibo4j.Oauth().authorize("code",
					xinlang.getAccessKey(),
					"http://" + globalSetting.getAppUrl()
							+ "/op/login/weiboLogin"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/weiboLogin")
	public void weiboLogin(HttpServletRequest request,
			HttpServletResponse response, String code) {
		log.info("IP:"+ClientInfo.getIp(request)+" \""+request.getHeader("User-Agent")+"\" 使用了新浪微博登录");
		try {
			ThirdPartyAccess xinlang = thirdPartyAccessService
					.findByType(ThirdPartyAccess.TYPE_XINLANG);
			GlobalSetting globalSetting = (GlobalSetting) request.getSession()
					.getAttribute("setting");
			weibo4j.Oauth oauth = new weibo4j.Oauth();
			weibo4j.http.AccessToken accessTokenObj = oauth
					.getAccessTokenByCode(code, xinlang.getAccessKey(),
							xinlang.getAccessSecret(), "http://"
									+ globalSetting.getAppUrl()
									+ "/op/login/weiboLogin");
			String accessToken = null, uid = null, tokenExpireIn = null;
			System.out.println("getin");
			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				System.out.print("没有获取到响应参数");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				request.getSession().setAttribute("token_expirein",
						String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的uid -------- start
				Account am = new Account(accessToken);
				JSONObject uidObj = am.getUid();
				uid = uidObj.getString("uid");
				System.out.println(uid);
				request.getSession().setAttribute("openId", uid);
				request.getSession().setAttribute("loginType",
						UserSession.TYPE_XINLANG);
				// 利用获取到的accessToken 去获取当前用户的openid --------- end
				// 为空代表首次登录，此处获取的信息尚未完全
				ThirdPartyAccount tpa = tpaService.findByOpenId(uid);
				if (tpa == null) {
					// 获取新浪微博用户的信息
					Users um = new Users(accessToken);
					weibo4j.model.User wUser = um.showUserById(uid);
					tpa = new ThirdPartyAccount();
					tpa.setOpenId(uid);
					tpa.setAccountType(UserSession.TYPE_XINLANG);
					tpa.setAccessToken(accessToken);

					User user = new User();
					user.setNickName(wUser.getScreenName());
					user.setActivateDate(new Date());
					user.setHeadIconSmall(wUser.getProfileImageUrl());
					user.setHeadIconMid(wUser.getProfileImageUrl());
					user.setHeadIconBig(wUser.getAvatarLarge());
					user.setHeadIconHD(wUser.getAvatarHD());
					user.setHeadIconUsed(User.HEADICON_WEIBO);
					user = userService.update(user);
					tpa.setHeadIconHD(wUser.getAvatarHD());
					tpa.setHeadIconBig(wUser.getAvatarLarge());
					tpa.setHeadIconMid(wUser.getProfileImageUrl());
					tpa.setHeadIconSmall(wUser.getProfileImageUrl());
					tpa.setUser(user);
					tpa = tpaService.update(tpa);
				}
				response.sendRedirect("callback");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@RequestMapping("/goTPADealing")
//	public String goDealing(String expires_in, String access_token) {
//		System.out.println("login:" + expires_in + "," + access_token);
//		return "login/dealing";
//	}
//
//	@RequestMapping("/TPADealing")
//	public String dealing(String openId, String accessToken, int loginType,
//			String nickName, String headIconSmall, String headIconMid,
//			String headIconBig, HttpSession session)
//			throws UnsupportedEncodingException {
//		ThirdPartyAccount tpa = tpaService.findByOpenId(openId);
//		// 授权出错的情况
//		if (tpa != null && !tpa.getAccessToken().equals(accessToken))
//			return "";
//		session.setAttribute("openId", openId);
//		session.setAttribute("loginType", loginType);
//
//		if (tpa == null) {
//			tpa = new ThirdPartyAccount();
//			tpa.setOpenId(openId);
//			tpa.setAccountType(loginType);
//			tpa.setAccessToken(accessToken);
//
//			User user = new User();
//			user.setNickName(nickName);
//			user.setActivateDate(new Date());
//			user.setHeadIconSmall(headIconSmall);
//			user.setHeadIconMid(headIconMid);
//			user.setHeadIconBig(headIconBig);
//			user = userService.update(user);
//			tpa.setUser(user);
//			tpa = tpaService.update(tpa);
//		}
//		return "redirect:callback";
//	}
//
//	/**
//	 * 新浪微博登录，获取code
//	 * 
//	 * @param code
//	 * @param state
//	 * @return
//	 */
//	@RequestMapping("/xinlangCode")
//	public String xinlangCode(String code, String state) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("code", code);
//		map.put("state", state);
//		return "redirect:goXinlangLogin?code=" + code + "&state=" + state;
//	}
//
//	/**
//	 * 新浪微博登录，跳转到获取access_token的页面
//	 * 
//	 * @param code
//	 * @param state
//	 * @return
//	 */
//	@RequestMapping("/goXinlangLogin")
//	public ModelAndView goXinlangLogin(String code, String state) {
//		ModelAndView mv = new ModelAndView("login/xinlangLogin");
//		ThirdPartyAccess xinlang = thirdPartyAccessService
//				.findByType(ThirdPartyAccess.TYPE_XINLANG);
//		mv.addObject("xinlang", xinlang);
//		mv.addObject("code", code);
//		mv.addObject("state", state);
//		return mv;
//	}

	/**
	 * 第三方帐号登录成功后的回调函数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/callback")
	public ModelAndView callback(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String openId = (String) session.getAttribute("openId");
		ThirdPartyAccount tpa = tpaService.findByOpenId(openId);
		UserSession userSession = userSessionService.findByUserId(tpa.getUser()
				.getUid());
		String sessionId = TokenUtil.getRandomString(32, 2);
		if (userSession == null) {
			userSession = new UserSession();
			userSession.setUser(tpa.getUser());
		} else {
			userSession.setLastLoginDate(userSession.getLoginDate());
			userSession.setLastLoginIp(userSession.getLoginIp());
		}
		userSession.setLoginDate(new Date());
		userSession.setLoginIp(ClientInfo.getIp(request));
		userSession.setSessionId(sessionId);
		userSession.setType((Integer) session.getAttribute("loginType"));
		userSession = userSessionService.update(userSession);
		session.setAttribute("userSession", userSession);
		session.setAttribute("bwSessionId", sessionId);
		String callback = (String) session.getAttribute("callback");
		if (callback == null || callback.contains("/op/login"))
			callback = "/";
		session.removeAttribute("callback");
		ModelAndView mv = new ModelAndView("redirect:" + callback);
		mv.addObject("userSession", userSession);
		return mv;
	}

	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "login/accessDenied";
	}

}
