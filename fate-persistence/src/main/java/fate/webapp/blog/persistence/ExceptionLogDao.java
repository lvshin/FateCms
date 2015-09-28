package fate.webapp.blog.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.ExceptionLog;

@Repository
public class ExceptionLogDao extends BaseDao<ExceptionLog>{

	public List<ExceptionLog> page(int status, int curPage, int pageSize){
		String hql = "from ExceptionLog e ";
		if(status!=0)
			hql += " where e.status=:status";
		hql += " order by e.id desc";
		TypedQuery<ExceptionLog> query = em.createQuery(hql, ExceptionLog.class);
		if(status!=0)
			query = query.setParameter("status", status);
		return query.setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(int status){
		String hql = "select count(*) from ExceptionLog e ";
		if(status!=0)
			hql += " where e.status=:status";
		TypedQuery<Long> query = em.createQuery(hql, Long.class);
		if(status!=0)
			query = query.setParameter("status", status);
		return query.getSingleResult();
	}
}
