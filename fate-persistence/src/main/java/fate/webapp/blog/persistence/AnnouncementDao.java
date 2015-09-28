package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.Announcement;

@Repository
public class AnnouncementDao extends BaseDao<Announcement>{

	public List<Announcement> page(int curPage, int pageSize){
		String hql = "from Announcement a";
		return em.createQuery(hql, Announcement.class).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(){
		String hql = "select count(*) from Announcement a";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
	
	public Announcement findLast(){
		String hql = "from Announcement a where (now()>a.startTime and now()<a.endTime) or a.startTime is null order by a.displayOrder asc,a.createTime desc";
		List<Announcement> list = em.createQuery(hql, Announcement.class).setFirstResult(0).setMaxResults(1).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
