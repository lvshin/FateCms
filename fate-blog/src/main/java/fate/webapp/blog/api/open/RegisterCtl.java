package fate.webapp.blog.api.open;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.SecurityVerification;
import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.service.SecurityVerificationService;
import fate.webapp.blog.service.TPAService;
import fate.webapp.blog.service.UserService;
import fate.webapp.blog.service.UserSessionService;
import fate.webapp.blog.utils.Base64;
import fate.webapp.blog.utils.ClientInfo;
import fate.webapp.blog.utils.EncryptUtil;
import fate.webapp.blog.utils.TokenUtil;

@Controller
@RequestMapping("/op/register")
public class RegisterCtl {
	
	private Logger log = Logger.getLogger(RegisterCtl.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private TPAService tpaService;
	
	@Autowired
	private UserSessionService userSessionService;
	
	@Autowired
	private SecurityVerificationService securityVerificationService;
	
	@RequestMapping("/goRegister")
	public String goRegister(HttpServletRequest request,HttpServletResponse response){
		UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
		if(userSession!=null)
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(request.getSession().getAttribute("callback")==null){
			String callback = request.getHeader("REFERER");
			request.getSession().setAttribute("callback", callback);
		}
		request.getSession().setAttribute("loginType", UserSession.TYPE_LOCAL);
		return "register/register";
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public Object submit(String nickName, String password, String username, int type,String code, HttpSession session,HttpServletRequest request){
		log.info("IP:"+ClientInfo.getIp(request)+" \""+request.getHeader("User-Agent")+"\" 进行了注册。");
		Map<String,Object> map = new HashMap<String, Object>();
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		String private_key = globalSetting.getGeetestKey();
		if(private_key!=null){
    		GeetestLib geetest = new GeetestLib(private_key);
    
    		String gtResult = "fail";
    		if (geetest.resquestIsLegal(request)) {
    			gtResult = geetest.enhencedValidateRequest(request);
    		} 
    		switch (gtResult) {
    		case "success":break;
    		case "forbidden":
    		case "fail":
    					map.put("success", false);
    					map.put("msg", "验证码错误");
    					return map;
    		default:
    			break;
    		}
		}
		User user = new User();
		if(userService.checkLoginName(username)||userService.checkNickName(nickName)){
			map.put("msg", "请勿重复提交");
			map.put("success", false);
			return map;
		}
		try{
		user.setNickName(nickName);
		Date date = Calendar.getInstance().getTime();
		user.setPassword(EncryptUtil.pwd(date, password));
		if(type==1){
			user.setEmail(username);
		}
		else{
			user.setMobile(username);
		}
		
//		user.setSex(request.getParameter("sex").charAt(0));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		user.setBirthday(sdf.parse(request.getParameter("birthday")));
		user.setActivateDate(date);
		
		userService.save(user);
		String url = "";
		if(type==1&&globalSetting.getNeedEmailVerify()){
			sendEmail(user.getEmail(), user.getUid(), globalSetting);
			url = (String) session.getAttribute("callback");
			map.put("msg", "验证邮件已发送...");
			map.put("success", true);
		}else if(type==2){
			/*手机验证*/
			String smsCode = (String) session.getAttribute("smsCode");
			String mobile = (String) session.getAttribute("mobile");
			if(!mobile.equals(user.getMobile())){
				user.setMobile(mobile);
				userService.update(user);
				map.put("msg", "手机号与验证码不匹配，请至个人中心重新验证");
				map.put("success", true);
			}else{
				String guid = (String) session.getAttribute("security");
				SecurityVerification securityVerification = securityVerificationService.find(guid);
				Date now = new Date();
				long time = now.getTime() - securityVerification.getVerificationTime().getTime();
				if(time>securityVerification.getTimeout()*60*1000){
					map.put("msg", "验证码超时，请至个人中心重新验证");
					map.put("success", true);
				}else if(code.trim().equals(smsCode)){
					user.setMobileStatus(true);
					userService.update(user);
					securityVerificationService.delete(securityVerification);
					map.put("msg", "注册成功");
					map.put("success", true);
				}
			}
			session.removeAttribute("smsCode");
			session.removeAttribute("mobile");
		}
		else{
			url = (String) session.getAttribute("callback");
			map.put("msg", "注册成功");
			map.put("success", true);
		}
		session.removeAttribute("callback");
		userSessionService.login(user, request);
		map.put("url", url);
		
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "未知错误");
			map.put("success", false);
		}
		
		return map;
	}
		
	public void sendEmail(String toMails, int uid, GlobalSetting globalSetting) throws MessagingException, UnsupportedEncodingException{
		SecurityVerification securityVerification = securityVerificationService
				.findBySecurityVerificationAndType(uid, SecurityVerification.VERIFICATION_TYPE_EMAIL);
		if (securityVerification == null) {
			securityVerification = new SecurityVerification();
			securityVerification.setUser(userService.find(uid));
		}
		Date now = Calendar.getInstance().getTime();
		if(securityVerification.getCode()==null||now.getTime()
				- securityVerification.getVerificationTime().getTime() > securityVerification
				.getTimeout() * 60 * 1000){
		String code = TokenUtil.getRandomString(8, 2);
		securityVerification.setCode(code);
		securityVerification.setStatus(SecurityVerification.VERIFICATION_STATUS_FAIL);
		securityVerification.setTimeout(Constants.EMAIL_TIMEOUT);
		securityVerification.setVerificationType(SecurityVerification.VERIFICATION_TYPE_EMAIL);
		securityVerification.setVerificationTime(new Date());
		securityVerificationService.update(securityVerification);
		// 建立邮件消息
		MimeMessage mailMessage = globalSetting.getJavaMailSender().createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
		// 设置收件人，寄件人 用数组发送多个邮件
		messageHelper.setTo(toMails);
		String nick = javax.mail.internet.MimeUtility.encodeText(globalSetting.getAppName()); 
		messageHelper.setFrom(new InternetAddress(nick + " <"+globalSetting.getSmtpFrom()+">"));
		messageHelper.setSubject(globalSetting.getSiteName()+"邮箱验证（请勿回复此邮件）");
		
		messageHelper
				.setText(
						"<!doctype html>"
								+ "<html>"
								+ "<head>"
								+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>"
								+ "<title"+globalSetting.getSiteName()+"邮箱验证</title>"
								+ "</head>"
								+ "<body>"
								+ "<div style='margin:0 auto;width:650px;'>"
								+ "<h3>尊敬的用户：</h3>"
								+ "<p>请点击以下地址，完成邮箱验证：</p>"
								+ "<p><a href='http://"+globalSetting.getAppUrl()+"/op/security/verification/goVerifyEmail?uid="+uid+"&code="+URLEncoder.encode(Base64.encode(code.getBytes()),"UTF-8")+"'>http://"+globalSetting.getAppUrl()+"/op/security/verification/goVerifyEmail?uid="+uid+"&code="+URLEncoder.encode(Base64.encode(code.getBytes()),"UTF-8")+"</a></p>"
								+ "<p>此链接有效期为"+Constants.EMAIL_TIMEOUT/60+"小时<span style='color:#808080'>（如果您无法点击此链接，请将链接复制到浏览器地址栏后访问）</span>"
								+ "</p>" + "</div>"  + "</body>"
								+ "</html>", true);
		globalSetting.getJavaMailSender().send(mailMessage);
		}else{
			
		}
	}
	
//	@RequestMapping("/TPARegister")
//	public ModelAndView TPARegister(String nickName){
//		ModelAndView mv = new ModelAndView("register/ThirdPartyIndex");
//		mv.addObject("nickName", nickName);
//		return mv;
//	}
//	
//	@RequestMapping("/TPASubmit")
//	@ResponseBody
//	public Object TPASubmit(String nickName,String loginName, String pwd,HttpServletRequest request){
//		HttpSession session = request.getSession();
//		int loginType = (Integer) session.getAttribute("loginType");
//		GlobalSetting globalSetting = (GlobalSetting) session.getAttribute("setting");
//		ThirdPartyAccount tpa = null;
//		if(loginType!=0)
//		tpa = tpaService.findByUserGuidAndType((String) session.getAttribute("openId"), loginType);
//		
//		User user = new User();
//		user.setNickName(nickName);
//		String url = "/";
//		if(globalSetting.getNeedEmailVerify()){
//			if(loginName.contains("@")){
//				user.setEmail(loginName);
//				url = "op/security/verification/goEmail?email="+loginName;
//			}
////			else{
////				user.setMobile(loginName);
////				url = "op/security/verification/goMobile?mobile="+loginName;
////			}
//			url += "&userGuid="+user.getGuid();
//		}
//		Date date = new Date();
//		user.setPwd(EncryptUtil.pwd(date, request.getParameter("pwd")));
//		user.setActivateDate(date);
//		user = userService.update(user);
//		
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("url", url);
//		return map;
//	}
//	
	@RequestMapping("/check")
	@ResponseBody
	public Object check(String name, String param, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		if(name.equals("username")||name.equals("email")){
			boolean exist = userService.checkLoginName(param);
			String s = "";
			if(param.contains("@"))
				s = "邮箱";
			else
				s = "手机号";
			map.put("status", exist?"n":"y");
			map.put("info",exist?"该"+s+"已被注册！":"该"+s+"可以使用");
		}else if(name.equals("nickName")){
			boolean exist = userService.checkNickName(param);
			map.put("status", exist?"n":"y");
			map.put("info", exist?"昵称已存在":"该昵称可以使用");
		}else if(name.equals("mobile")){
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			if(param.equals(userSession.getUser().getMobile())){
				map.put("status", "y");
				map.put("info","该手机号可以使用");
				return map;
			}
			boolean exist = userService.checkLoginName(param);
			map.put("status", exist?"n":"y");
			map.put("info",exist?"该手机号已被注册！":"该手机号可以使用");
		}
		return map;
	}
}
