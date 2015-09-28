package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.Spider;

@Repository
public class SpiderDao extends BaseDao<Spider>{
	
	public List<Spider> page(int curPage, int pageSize){
		String hql = "from Spider s order by s.id desc";
		return em.createQuery(hql, Spider.class).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(){
		String hql = "select count(*) from Spider s";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
}
