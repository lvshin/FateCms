package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.PingRecord;

@Repository
public class PingRecordDao extends BaseDao<PingRecord>{

	public List<PingRecord> findAll(int curPage, int pageSize){
		String hql = "from PingRecord p order by p.pingDate desc";
		return em.createQuery(hql, PingRecord.class).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(){
		String hql = "select count(*) from PingRecord p";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
	
	public PingRecord findByThemeGuid(String guid){
		String hql = "from PingRecord p where p.theme.guid=:guid";
		List<PingRecord> list = em.createQuery(hql, PingRecord.class).setParameter("guid", guid).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
