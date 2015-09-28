package fate.webapp.blog.persistence;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.SystemMessage;

@Repository
public class SystemMessageDao extends BaseDao<SystemMessage>{

	public Long getUnreadCount(int uid){
		String hql = "select count(*) from SystemMessage s where s.uid=:uid and isRead=false";
		return em.createQuery(hql, Long.class).setParameter("uid", uid).getSingleResult();
	}
}
