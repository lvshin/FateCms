package fate.webapp.blog.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weibo4j.Account;
import weibo4j.model.WeiboException;
import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.persistence.ThirdPartyAccountDao;
import fate.webapp.blog.persistence.UserSessionDao;
import fate.webapp.blog.service.UserSessionService;
import fate.webapp.blog.utils.ClientInfo;
import fate.webapp.blog.utils.TokenUtil;

@Service
@Transactional
public class UserSessionServiceImpl implements UserSessionService {

	@Autowired
	private UserSessionDao userSessionDao;
	
	@Autowired
	private ThirdPartyAccountDao thirdPartyAccountDao;
	
	public void save(UserSession userSession) {
		userSessionDao.save(userSession);
	}

	public UserSession update(UserSession userSession) {
		return userSessionDao.update(userSession);
	}

	public UserSession findByUserId(int uid) {
		return userSessionDao.findByUserId(uid);
	}

	public UserSession find(String guid){
		return userSessionDao.find(guid);
	}
	
	public UserSession login(User user, HttpServletRequest request){
		HttpSession session = request.getSession();
		UserSession userSession = findByUserId(user.getUid());
		if(userSession==null){
			userSession = new UserSession();
		}
		else{
			userSession.setLastLoginDate(userSession.getLoginDate());
			userSession.setLastLoginIp(userSession.getLoginIp());
		}
		userSession.setLoginDate(new Date());
		userSession.setLoginIp(ClientInfo.getIp(request));
		String sessionId = TokenUtil.getRandomString(32, 2);
		userSession.setSessionId(sessionId);
		userSession.setUser(user);
		userSession.setType(0);
		userSession = update(userSession);
		session.setAttribute("userSession", userSession);
		session.setAttribute("bwSessionId", userSession.getSessionId());
		return userSession;
	}
	
	public void logout(String guid){
		UserSession session = find(guid);
		session.setSessionId(null);
		if(session.getType()==UserSession.TYPE_XINLANG){
			Account am = new Account(thirdPartyAccountDao.findByUidAndType(session.getUser().getUid(),UserSession.TYPE_XINLANG).getAccessToken());
			try {
				am.endSession();
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		update(session);
	}
	
	public UserSession loginByToken(int uid,String token){
		return userSessionDao.loginByToken(uid, token);
	}
	
	public UserSession findByToken(String token){
		return userSessionDao.findByToken(token);
	}
}
