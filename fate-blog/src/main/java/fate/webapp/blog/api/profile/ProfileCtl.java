package fate.webapp.blog.api.profile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.model.SecurityVerification;
import fate.webapp.blog.model.ThirdPartyAccess;
import fate.webapp.blog.model.ThirdPartyAccount;
import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.service.OSSService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.service.SecurityVerificationService;
import fate.webapp.blog.service.SystemMessageService;
import fate.webapp.blog.service.TPAService;
import fate.webapp.blog.service.ThirdPartyAccessService;
import fate.webapp.blog.service.UserService;
import fate.webapp.blog.utils.Base64;
import fate.webapp.blog.utils.ImageUtils;
import fate.webapp.blog.utils.TokenUtil;
import fate.webapp.blog.websocket.SystemMessageWebSocketHandler;

@Controller
@RequestMapping("/profile")
public class ProfileCtl {

	private static final Logger log = Logger.getLogger(ProfileCtl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SystemMessageService systemMessageService;

	@Autowired
	private TPAService tpaService;

	@Autowired
	private OSSService ossService;

	@Autowired
	private SecurityVerificationService securityVerificationService;

	@Autowired
	private ThirdPartyAccessService thirdPartyAccessService;

	@Autowired
	private ParamService paramService;

	@RequestMapping("basicInfo")
	public ModelAndView basicInfo(HttpSession session) {
		ModelAndView mv = new ModelAndView("profile/basicInfo");
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		ThirdPartyAccount qq = tpaService.findByUidAndType(userSession
				.getUser().getUid(), ThirdPartyAccount.ACCOUNT_TYPE_QQ);
		ThirdPartyAccount weibo = tpaService.findByUidAndType(userSession
				.getUser().getUid(), ThirdPartyAccount.ACCOUNT_TYPE_XINLANG);
		mv.addObject("qq", qq == null ? "" : qq.getHeadIconBig());
		mv.addObject("weibo", weibo == null ? "" : weibo.getHeadIconBig());
		return mv;
	}

	@RequestMapping("/updateBasicInfo")
	@ResponseBody
	public Object updateBasicInfo(User u, int headIcon, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
        User user = userSession.getUser();
		try {
			user.setAddress(u.getAddress());
			user.setHeadIconUsed(headIcon);
			if (headIcon != 0) {
				ThirdPartyAccount tpa = tpaService.findByUidAndType(
						user.getUid(), headIcon);
				if (tpa != null)
					user.setHeadIconBig(tpa.getHeadIconBig());
				else
					user.setHeadIconBig(null);
			} else {
				user.setHeadIconBig(user.getHeadIconLocal());
			}
			user.setBirthday(u.getBirthday());
			user.setQq(u.getQq());
			user.setSex(u.getSex());
			userService.update(user);
			map.put("success", true);
			map.put("message", "保存成功");
		} catch (Exception e) {
			log.error("更新个人信息失败，用户ID："+user.getUid());
			map.put("success", false);
			map.put("message", "未知错误");
		}
		return map;
	}

	@RequestMapping("/headIcon")
	public ModelAndView headIcon(HttpSession session) {
		ModelAndView mv = new ModelAndView("profile/headIcon");

		return mv;
	}

	@RequestMapping("/uploadImg")
	@ResponseBody
	public Object uploadImg(@RequestParam("img") MultipartFile uploadFile,
			HttpServletRequest request) {
		log.info("头像上传");
		String dir = "headIcon";
		Map<String, Object> map = new HashMap<String, Object>();
		String filename = uploadFile.getOriginalFilename();// 获取文件名
		String type = filename.substring(filename.lastIndexOf("."));
		if (!(type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png") || type.equals(".gif"))) {
			map.put("status", "error");
			map.put("message", "图片格式错误");
			return map;
		}
		ServletContext context = request.getServletContext();
		String relpath = context.getRealPath("/").substring(0,
				context.getRealPath("/").indexOf(File.separator))
				+ File.separator;
		long cur = System.currentTimeMillis();
		String uppath = "download" + File.separator + dir + File.separator + cur + type;
		boolean flag = true;
		File dirs = new File(relpath + "download" + File.separator + dir);
		// 如果目录不存在，则创建目录
		if (!dirs.exists()) {
			flag = dirs.mkdirs();
		}

		File newFile = new File(relpath + uppath);
		while (newFile.exists()) {
			filename = filename.replace(".", "(1).");
			newFile = new File(relpath + "download" + File.separator + dir
					+ File.separator + filename);
		}
		if (flag) {
			try {
				uploadFile.transferTo(newFile);
				BufferedImage bi = ImageIO.read(newFile);

				map.put("url", request.getContextPath()
						+ "/profile/getfile/" + dir + "/" + cur + "."
						+ filename.substring(filename.lastIndexOf(".") + 1));
				map.put("status", "success");
				map.put("width", bi.getWidth());
				map.put("height", bi.getHeight());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("头像文件写入失败");
				map.put("status", "error");
				map.put("message", "系统内部错误");
			}

		}
		return map;
	}

	@RequestMapping("/cropImg")
	@ResponseBody
	public Object cropImg(HttpServletRequest request, String imgUrl,
			double imgInitW, double imgInitH, double imgW, double imgH,
			double imgY1, double imgX1, double cropH, double cropW) {
		Map<String, Object> map = new HashMap<String, Object>();
		ServletContext context = request.getServletContext();
		try {
			String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
			String relpath = context.getRealPath("/").substring(0,
					context.getRealPath("/").indexOf(File.separator))
					+ File.separator;
			String uppath = "download" + File.separator + "headIcon"
					+ File.separator + fileName;
			String filePath = relpath + uppath;
			File oraginal = new File(filePath);
			File tmp = new File(filePath + "tmp");
			File file = new File(filePath.replace(".", "1."));
			ImageUtils.resize(oraginal, tmp, (int) imgW, (int) imgH, 1.0f);
			ImageUtils.cut(tmp, file, (int) imgX1, (int) imgY1, (int) cropW,
					(int) cropH);
			oraginal.delete();
			tmp.delete();
			HttpSession session = request.getSession(false);
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
			String key = "user" + userSession.getUser().getUid() + ".png";
			ossService.headIconUpload(ossBucket.getTextValue(),
					file, "user/headIcon/", key);
			String url = imgUrl.replace(".", "1.");
			
			map.put("status", "success");
			map.put("url", url);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "error");
			map.put("message", "文件切割异常");
			log.error("文件切割异常:", e);
		}
		return map;
	}

	@RequestMapping("/getfile/{dir}/{filename}.{type}")
	public void fileOpen(@PathVariable("dir") String dir,
			@PathVariable("filename") String filename,
			@PathVariable("type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(filename);
		filename = filename + "." + type;
		ServletContext context = request.getServletContext();
		String relpath = context.getRealPath("/").substring(0,
				context.getRealPath("/").indexOf(File.separator))
				+ File.separator;
		File file = new File(relpath + "download" + File.separator + dir
				+ File.separator + filename);
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream objectContent = null;
		String contentType = "multipart/form-data";
		try {
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("gbk"), "ISO8859-1") + "");
			OutputStream out = response.getOutputStream();

			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Connection", "close");
			response.setHeader("Content-Length", "" + file.length());
			String range = request.getHeader("Range");
			long rangeStart = range == null ? 0 : Long.parseLong(range
					.substring(range.indexOf("=") + 1, range.indexOf("-")));
			long rangeEnd = range == null ? file.length() : range.substring(
					range.indexOf("-") + 1).equals("") ? file.length() : Long
					.parseLong(range.substring(range.indexOf("-") + 1));
			// if(rangeStart!=0)
			// response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			response.setHeader("Content-Range", "bytes " + rangeStart + "-"
					+ ((int) rangeEnd - 1) + "/" + (int) file.length());

			byte[] b = new byte[1024 * 1024 * 5];
			int off = (int) rangeStart;
			objectContent = new FileInputStream(file);
			objectContent.skip(rangeStart);
			while (off < rangeEnd) {
				int len = objectContent.read(b);
				if (len == -1)
					break;
				off += len;
				out.write(b, 0, len);
			}
			out.flush();
			out.close();
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		} finally {

			try {
				if (objectContent != null)
					objectContent.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/updateHeadIcon")
	@ResponseBody
	public Object updateHeadIcon(String url, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserSession userSession = (UserSession) request.getSession(false)
					.getAttribute("userSession");
			User user = userSession.getUser();
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			ServletContext context = request.getServletContext();
			String relpath = context.getRealPath("/").substring(0,
					context.getRealPath("/").indexOf(File.separator))
					+ File.separator;
			String uppath = "download" + File.separator + "headIcon"
					+ File.separator + fileName;
			String filePath = relpath + uppath;
			File file = new File(filePath);
			Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
			Param ossEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
			Param ossUrl = paramService.findByKey(Constants.OSS_URL);
			String key = "user" + userSession.getUser().getUid() + ".png";
			ossService.headIconUpload(ossBucket.getTextValue(),
					file, "user/headIcon/", key);
			String newUrl = "http://"
					+ (ossUrl == null||ossUrl.getTextValue()==null||ossUrl.equals("") ? ossBucket.getTextValue()
							+ "." + ossEndpoint.getTextValue() : ossUrl.getTextValue()) + "/user/headIcon/" + key;
			user.setHeadIconLocal(newUrl);
			if (user.getHeadIconUsed() == User.HEADICON_LOCAL) {
				user.setHeadIconBig(newUrl);
				user.setHeadIconMid(newUrl);
				user.setHeadIconSmall(newUrl);
			}
			file.delete();
			userService.update(user);
			map.put("success", true);
		} catch (Exception e) {
		    log.error("更新头像失败", e);
			map.put("success", false);
			map.put("message", "未知错误");
		}
		return map;
	}

	@RequestMapping("/security")
	public ModelAndView securuty() {
		ModelAndView mv = new ModelAndView("profile/security");
		return mv;
	}

	@RequestMapping("/mobile")
	public ModelAndView mobile(String mobile) {
		ModelAndView mv = new ModelAndView("profile/mobile");
		mv.addObject("mobile", mobile);
		return mv;
	}

	@RequestMapping("/updateMobile")
	@ResponseBody
	public Object updateMobile(String mobile, String code, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String m = (String) session.getAttribute("mobile");
			if (!m.equals(mobile)) {
				map.put("success", false);
				map.put("message", "验证失败，手机号不一致");
				return map;
			}
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			User user = userSession.getUser();
			String smsCode = (String) session.getAttribute("smsCode");
			String guid = (String) session.getAttribute("security");
			SecurityVerification securityVerification = securityVerificationService
					.find(guid);
			Date now = new Date();
			long time = now.getTime()
					- securityVerification.getVerificationTime().getTime();
			if (time > securityVerification.getTimeout() * 60 * 1000) {
				map.put("message", "验证码超时，请重新验证");
				map.put("success", true);
			} else if (code.trim().equals(smsCode)) {
				user.setMobile(mobile);
				user.setMobileStatus(true);
				userService.update(user);
				securityVerificationService.delete(securityVerification);
				map.put("message", "验证成功");
				map.put("success", true);
			}
			map.put("success", true);
		} catch (Exception e) {
			log.error("手机验证失败", e);
			map.put("success", false);
			map.put("message", "未知错误");
		}
		return map;
	}

	@RequestMapping("/email")
	public ModelAndView email(String email) {
		ModelAndView mv = new ModelAndView("profile/email");
		mv.addObject("email", email);
		return mv;
	}

	@RequestMapping("/sendEmail")
	@ResponseBody
	public Object sendEmial(String email, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			sendEmail(email, userSession.getUser());
			map.put("success", true);
		} catch (Exception e) {
			log.error("邮件发送失败", e);
			map.put("success", false);
			map.put("message", "未知错误");
		}
		return map;
	}

	public void sendEmail(String toMails, User user)
			throws MessagingException, UnsupportedEncodingException {
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		SecurityVerification securityVerification = securityVerificationService
				.findBySecurityVerificationAndType(user.getUid(),
						SecurityVerification.VERIFICATION_TYPE_EMAIL);
		if (securityVerification == null) {
			securityVerification = new SecurityVerification();
			securityVerification.setUser(user);
		}
		Date now = Calendar.getInstance().getTime();
		if (securityVerification.getCode() == null
				|| now.getTime()
						- securityVerification.getVerificationTime().getTime() > securityVerification
						.getTimeout() * 60 * 1000) {
			String code = TokenUtil.getRandomString(8, 2);
			securityVerification.setValue(toMails);
			securityVerification.setCode(code);
			securityVerification
					.setStatus(SecurityVerification.VERIFICATION_STATUS_FAIL);
			securityVerification.setTimeout(Constants.EMAIL_TIMEOUT);
			securityVerification
					.setVerificationType(SecurityVerification.VERIFICATION_TYPE_EMAIL);
			securityVerification.setVerificationTime(new Date());
			securityVerificationService.update(securityVerification);
			GlobalSetting setting = GlobalSetting.getInstance();
			// 建立邮件消息
			MimeMessage mailMessage = setting.getJavaMailSender().createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
			// 设置收件人，寄件人 用数组发送多个邮件
			messageHelper.setTo(toMails);
			String nick = javax.mail.internet.MimeUtility
					.encodeText(globalSetting.getAppName());
			messageHelper.setFrom(new InternetAddress(nick + " <" + setting.getSmtpFrom()
					+ ">"));
			messageHelper.setSubject(globalSetting.getSiteName()
					+ "邮箱验证（请勿回复此邮件）");

			messageHelper
					.setText(
							"<!doctype html>"
									+ "<html>"
									+ "<head>"
									+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>"
									+ "<title>祝福之风邮箱验证</title>"
									+ "</head>"
									+ "<body>"
									+ "<div style='margin:0 auto;width:650px;'>"
									+ "<h3>尊敬的用户：</h3>"
									+ "<p>请点击以下地址，完成邮箱验证：</p>"
									+ "<p><a href='http://"
									+ globalSetting.getAppUrl()
									+ "/op/security/verification/goVerifyEmail?uid="
									+ user.getUid()
									+ "&code="
									+ URLEncoder.encode(
											Base64.encode(code.getBytes()),
											"UTF-8")
									+ "'>http://"
									+ globalSetting.getAppUrl()
									+ "/op/security/verification/goVerifyEmail?uid="
									+ user.getUid()
									+ "&code="
									+ URLEncoder.encode(
											Base64.encode(code.getBytes()),
											"UTF-8")
									+ "</a></p>"
									+ "<p>此链接有效期为"
									+ Constants.EMAIL_TIMEOUT
									/ 60
									+ "小时<span style='color:#808080'>（如果您无法点击此链接，请将链接复制到浏览器地址栏后访问）</span>"
									+ "</p>" + "</div>" + "</body>" + "</html>",
							true);
			setting.getJavaMailSender().send(mailMessage);
		} 
	}

	@RequestMapping("/thirdParty")
	public ModelAndView thirdParty(HttpSession session) {
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		ModelAndView mv = new ModelAndView("profile/thirdParty");
		ThirdPartyAccount qq = tpaService.findByUidAndType(userSession
				.getUser().getUid(), ThirdPartyAccount.ACCOUNT_TYPE_QQ);
		mv.addObject("qq", qq);
		ThirdPartyAccount weibo = tpaService.findByUidAndType(userSession
				.getUser().getUid(), ThirdPartyAccount.ACCOUNT_TYPE_XINLANG);
		mv.addObject("weibo", weibo);
		return mv;
	}

	@RequestMapping("/toBindQQ")
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
							+ "/profile/bindQQ"));
		} catch (QQConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/bindQQ")
	public void bindQQ(HttpServletRequest request, HttpServletResponse response) {
		try {
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
					UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo(qq
							.getAccessKey());
					if (userInfoBean != null) {
						tpa = new ThirdPartyAccount();
						tpa.setOpenId(openID);
						tpa.setAccountType(UserSession.TYPE_QQ);
						tpa.setAccessToken(accessToken);
						tpa.setHeadIconBig(userInfoBean.getAvatar()
								.getAvatarURL100());
						tpa.setHeadIconMid(userInfoBean.getAvatar()
								.getAvatarURL50());
						tpa.setHeadIconSmall(userInfoBean.getAvatar()
								.getAvatarURL30());
						tpa = tpaService.update(tpa);
					}
				}

				UserSession userSession = (UserSession) request.getSession(
						false).getAttribute("userSession");
				tpa.setUser(userSession.getUser());
				tpaService.update(tpa);

				fate.qq4j.weibo.UserInfo weiboUserInfo = new fate.qq4j.weibo.UserInfo(
						accessToken, openID);
				com.qq.connect.javabeans.weibo.UserInfoBean weiboUserInfoBean = weiboUserInfo
						.getUserInfo(qq.getAccessKey());
				response.sendRedirect("thirdParty");
			}
		} catch (QQConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/unbindQQ", method = RequestMethod.POST)
	@ResponseBody
	public Object unbindQQ(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			ThirdPartyAccount tpa = tpaService.findByUidAndType(userSession
					.getUser().getUid(), ThirdPartyAccount.ACCOUNT_TYPE_QQ);
			tpa.setUser(null);
			tpaService.update(tpa);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "未知错误");
		}
		return map;
	}
	
	@RequestMapping("/toBindWeibo")
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
							+ "/profile/bindWeibo"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/bindWeibo")
	public void weiboLogin(HttpServletRequest request,
			HttpServletResponse response, String code) {
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
									+ "/profile/bindWeibo");
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

					tpa.setHeadIconHD(wUser.getAvatarHD());
					tpa.setHeadIconBig(wUser.getAvatarLarge());
					tpa.setHeadIconMid(wUser.getProfileImageUrl());
					tpa.setHeadIconSmall(wUser.getProfileImageUrl());
					tpa = tpaService.update(tpa);
				}
				UserSession userSession = (UserSession) request.getSession(
						false).getAttribute("userSession");
				tpa.setUser(userSession.getUser());
				tpaService.update(tpa);
				response.sendRedirect("thirdParty");
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
	
	@RequestMapping(value = "/unbindWeibo", method = RequestMethod.POST)
	@ResponseBody
	public Object unbindWeibo(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			ThirdPartyAccount tpa = tpaService.findByUidAndType(userSession
					.getUser().getUid(), ThirdPartyAccount.ACCOUNT_TYPE_XINLANG);
			tpa.setUser(null);
			tpaService.update(tpa);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "未知错误");
		}
		return map;
	}

	/**
	 * 表单提交日期绑定
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 写上你要的日期格式
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@Bean
	public SystemMessageWebSocketHandler systemMessageWebSocketHandler() {
		return new SystemMessageWebSocketHandler();
	}

}
