package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.ApiLog;

@Repository
public class ApiLogDao extends BaseDao<ApiLog>{

	public List<ApiLog> page(int curPage, int pageSize){
		String hql = "from ApiLog";
		return em.createQuery(hql, ApiLog.class).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
}
