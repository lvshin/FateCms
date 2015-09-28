package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.ThirdPartyAccount;

@Repository
public class ThirdPartyAccountDao extends BaseDao<ThirdPartyAccount>{

	public List<ThirdPartyAccount> findByUid(int uid){
		String hql = "from ThirdPartyAccount t where t.user.uid =:uid";
		return em.createQuery(hql, ThirdPartyAccount.class).setParameter("uid", uid).getResultList();
	}
	
	public ThirdPartyAccount findByUidAndType(int uid,int type){
		String hql = "from ThirdPartyAccount t where t.user.uid =:uid and t.accountType=:type";
		List<ThirdPartyAccount> list = em.createQuery(hql, ThirdPartyAccount.class).setParameter("uid", uid).setParameter("type", type).getResultList();
	    if(list.size()==0)
	    	return null;
	    else
	    	return list.get(0);
	}
	
	public ThirdPartyAccount findByOpenId(String openId){
		String hql = "from ThirdPartyAccount t where t.openId=:openId";
		List<ThirdPartyAccount> list = em.createQuery(hql, ThirdPartyAccount.class).setParameter("openId", openId).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
