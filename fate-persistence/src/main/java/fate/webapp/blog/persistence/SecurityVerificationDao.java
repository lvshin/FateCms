package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.SecurityVerification;

@Repository
public class SecurityVerificationDao extends BaseDao<SecurityVerification>{

	public SecurityVerification loadByUserAndType(int uid, int type){
		String hql = "from SecurityVerification s where s.user.uid=:uid and s.verificationType=:type";
		List<SecurityVerification> list =  em.createQuery(hql, SecurityVerification.class).setParameter("uid", uid).setParameter("type", type).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
	
	public SecurityVerification loadByLoginNameAndType(String loginName, int type){
		String hql = "from SecurityVerification s where (s.user.email=:loginName or s.user.mobile=:loginName) and s.verificationType=:type";
		List<SecurityVerification> list =  em.createQuery(hql, SecurityVerification.class).setParameter("loginName", loginName).setParameter("type", type).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
	
	public SecurityVerification findByGuidAndType(String guid, int type){
		String hql = "from SecurityVerification s where s.guid=:guid and s.verificationType=:type";
		List<SecurityVerification> list =  em.createQuery(hql, SecurityVerification.class).setParameter("guid", guid).setParameter("type", type).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
