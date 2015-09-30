package fate.webapp.blog.api.security;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.SecurityVerification;
import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.service.SecurityVerificationService;
import fate.webapp.blog.service.UserService;
import fate.webapp.blog.service.UserSessionService;
import fate.webapp.blog.utils.Base64;
import fate.webapp.blog.utils.JHUtils;
import fate.webapp.blog.utils.TokenUtil;

@Controller
@RequestMapping("/op/security/verification")
public class SecurityVerificationCtl {

	@Autowired
	private SecurityVerificationService securityVerificationService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSessionService userSessionService;

	@RequestMapping("/goMobile")
	public ModelAndView goMobile(String mobile, int uid) {
		ModelAndView mv = new ModelAndView("security/verification_mobile");
		mv.addObject("mobile", mobile);
		mv.addObject("uid", uid);
		return mv;
	}

	@RequestMapping("/sendSms")
	@ResponseBody
	public Object sendSms(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SecurityVerification securityVerification =  new SecurityVerification();
			String code = TokenUtil.getRandomString(6, 1);
			securityVerification.setCode(code);
			securityVerification.setTimeout(Constants.MOBILE_TIMEOUT);
			securityVerification
					.setVerificationType(SecurityVerification.VERIFICATION_TYPE_MOBILE);
			securityVerification.setVerificationTime(new Date());
			securityVerificationService.update(securityVerification);
			map.put("success", true);
			map.put("res", JHUtils.sendSms(mobile, code, Constants.MOBILE_TIMEOUT, 0));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "短信发送失败");
		}
		return map;
	}

	@RequestMapping("/verifySms")
	@ResponseBody
	public Object verifySms(String code, int uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		SecurityVerification securityVerification = securityVerificationService
				.findBySecurityVerificationAndType(uid, SecurityVerification.VERIFICATION_TYPE_MOBILE);
		Date now = Calendar.getInstance().getTime();
		if (securityVerification.getCode() == null) {
			map.put("success", false);
			map.put("error_code", 3);
			return map;
		} else if (now.getTime()
				- securityVerification.getVerificationTime().getTime() > securityVerification
				.getTimeout() * 60 * 1000) {
			map.put("success", false);
			map.put("error_code", 1);
			return map;
		} else if (code.equals(securityVerification.getCode())) {
			map.put("success", true);
			securityVerification
					.setStatus(SecurityVerification.VERIFICATION_STATUS_SUCCESS);
			securityVerification.setCode(null);
			securityVerification.getUser().setMobileStatus(true);
			securityVerificationService.update(securityVerification);
		} else {
			map.put("success", false);
			map.put("error_code", 2);
		}
		return map;
	}

	@RequestMapping("/goEmail")
	public ModelAndView goEmail(String email, int uid) {
		ModelAndView mv = new ModelAndView("security/verification_email");
		mv.addObject("email", email);
		mv.addObject("uid", uid);
		return mv;
	}

	@RequestMapping("/sendEmail")
	@ResponseBody
	public Object sendMail(String toMails, int uid, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
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
			GlobalSetting setting = GlobalSetting.getInstance();
			// 建立邮件消息
			MimeMessage mailMessage = setting.getJavaMailSender().createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
			// 设置收件人，寄件人 用数组发送多个邮件
			messageHelper.setTo(toMails);
			messageHelper.setFrom(setting.getSmtpFrom());
			GlobalSetting globalSetting = (GlobalSetting) session.getAttribute("setting");
			messageHelper.setSubject(globalSetting.getSiteName()+"邮箱验证（请勿回复此邮件）");
			
			messageHelper
					.setText(
							"<!doctype html>"
									+ "<html>"
									+ "<head>"
									+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>"
									+ "<title>"+globalSetting.getSiteName()+"邮箱验证</title>"
									+ "</head>"
									+ "<body>"
									+ "<div style='margin:0 auto;width:650px;'>"
									+ "<h3>尊敬的用户：</h3>"
									+ "<p>请点击以下地址，完成邮箱验证：</p>"
									+ "<p><a href='http://"+globalSetting.getAppUrl()+"/op/security/verification/goVerifyEmail?uid="+uid+"&code="+URLEncoder.encode(Base64.encode(code.getBytes()),"UTF-8")+"'>"+globalSetting.getAppUrl()+"op/security/verification/goVerifyEmail?uid="+uid+"&code="+URLEncoder.encode(Base64.encode(code.getBytes()),"UTF-8")+"</a></p>"
									+ "<p>此链接有效期为"+Constants.EMAIL_TIMEOUT/60+"小时<span style='color:#808080'>（如果您无法点击此链接，请将链接复制到浏览器地址栏后访问）</span>"
									+ "</p>" + "</div>"  + "</body>"
									+ "</html>", true);
			setting.getJavaMailSender().send(mailMessage);
			map.put("success", true);
			}
			else{
				map.put("success", false);
				map.put("error_code", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("error_code", 0);
		}
		return map;
	}
	
	@RequestMapping("/goVerifyEmail")
	public ModelAndView goVerifyEmail(String code, int uid){
		ModelAndView mv = new ModelAndView("security/go_verify_email");
		mv.addObject("code", code);
		mv.addObject("uid", uid);
		return mv;
	}
	
	@RequestMapping("/verifyEmail")
	@ResponseBody
	public Object verifyEmail(String code, int uid, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			code = new String(Base64.decode(code));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("error_code", 2);
			return map;
		}
		SecurityVerification securityVerification = securityVerificationService
				.findBySecurityVerificationAndType(uid, SecurityVerification.VERIFICATION_TYPE_EMAIL);
		Date now = Calendar.getInstance().getTime();
		if (securityVerification==null||securityVerification.getCode() == null) {
			map.put("success", false);
			map.put("error_code", 3);
			return map;
		} else if (now.getTime()
				- securityVerification.getVerificationTime().getTime() > securityVerification
				.getTimeout() * 60 * 1000) {
			map.put("success", false);
			map.put("error_code", 1);
			return map;
		} else if (code.equals(securityVerification.getCode())) {
			map.put("success", true);
			securityVerification
					.setStatus(SecurityVerification.VERIFICATION_STATUS_SUCCESS);
			securityVerification.setCode(null);
			securityVerification.getUser().setEmailStatus(true);
			userService.update(securityVerification.getUser());
			UserSession userSession = userSessionService.findByUserId(securityVerification.getUser().getUid());
			User user = securityVerification.getUser();
			user.setEmailStatus(true);
			userService.update(user);
			session.setAttribute("userSession", userSession);
			securityVerificationService.delete(securityVerification);
		} else {
			map.put("success", false);
			map.put("error_code", 2);
		}
		return map;
	}
}
