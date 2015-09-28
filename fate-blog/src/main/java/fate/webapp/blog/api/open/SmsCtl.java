package fate.webapp.blog.api.open;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.SecurityVerification;
import fate.webapp.blog.service.SecurityVerificationService;
import fate.webapp.blog.utils.JHUtils;
import fate.webapp.blog.utils.TokenUtil;

@Controller
@RequestMapping("/op")
public class SmsCtl {

	private Logger log = Logger.getLogger(SmsCtl.class);
	
	@Autowired
	private SecurityVerificationService securityVerificationService;
	
	@RequestMapping(value = "/sendVerificationCode", method = RequestMethod.POST)
	@ResponseBody
	public Object sendVerificationCode(String mobile,HttpServletRequest request, HttpSession session){
		Map<String,Object> map = new HashMap<String, Object>();
		if(mobile==null||mobile.equals("")){
			map.put("code", -1);
			map.put("msg", "手机号不能为空");
			return map;
		}
		
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		String private_key = globalSetting.getGeetestKey();
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
					map.put("msg", "滑动验证码错误");
					return map;
		default:
			break;
		}
		String smsCode = TokenUtil.getRandomString(6, 1);
		session.setAttribute("smsCode", smsCode);
		session.setAttribute("mobile", mobile);
		SecurityVerification securityVerification = new SecurityVerification();
		securityVerification.setCode(smsCode);
		securityVerification.setTimeout(Constants.MOBILE_TIMEOUT);
		securityVerification.setVerificationTime(new Date());
		securityVerification.setVerificationType(SecurityVerification.VERIFICATION_TYPE_MOBILE);
		securityVerificationService.save(securityVerification);
		session.setAttribute("security", securityVerification.getGuid());
		return JHUtils.sendSms(mobile, smsCode, Constants.MOBILE_TIMEOUT, 0);
	}
	
	
}
