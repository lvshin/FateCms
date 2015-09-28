package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.FriendLink;

@Repository
public class FriendLinkDao extends BaseDao<FriendLink>{
	
	public FriendLink findByLink(String link){
		String hql = "from FriendLink f where f.url=:link";
		List<FriendLink> links = em.createQuery(hql, FriendLink.class).setParameter("link", link).getResultList();
		if(links.size()==0)
			return null;
		else
			return links.get(0);
	}
	
	public List<FriendLink> page(int state, int curPage, int pageSize){
		String hql = "from FriendLink f where f.state=:state";
		return em.createQuery(hql, FriendLink.class).setParameter("state", state).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(int state){
		String hql = "select count(*) from FriendLink f where f.state=:state";
		return em.createQuery(hql, Long.class).setParameter("state", state).getSingleResult();
	}
	
	public List<FriendLink> searchByState(int state){
		String hql = "from FriendLink f where f.state=:state";
		return em.createQuery(hql, FriendLink.class).setParameter("state", state).getResultList();
	}
}
