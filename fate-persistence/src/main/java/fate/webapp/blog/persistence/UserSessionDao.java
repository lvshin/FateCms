package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.User;
import fate.webapp.blog.model.UserSession;
import fate.webapp.blog.utils.EncryptUtil;

@Repository
public class UserSessionDao extends BaseDao<UserSession>{

	public UserSession findByUserId(int uid){
		String hql = "from UserSession u where u.user.uid=:uid";
		List<UserSession> list = em.createQuery(hql, UserSession.class).setParameter("uid", uid).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
	
	public UserSession loginByToken(int uid,String token){
		String hql = "from UserSession u where u.user.uid=:uid and u.sessionId=:token";
		List<UserSession> userSessions = em.createQuery(hql, UserSession.class).setParameter("uid", uid).setParameter("token", token).getResultList();
		if(userSessions.size()!=0)
			return userSessions.get(0);
		else
			return null;
	}
	
	public UserSession findByToken(String token){
		String hql = "from UserSession u where u.sessionId=:token";
		List<UserSession> list = em.createQuery(hql, UserSession.class).setParameter("token", token).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
