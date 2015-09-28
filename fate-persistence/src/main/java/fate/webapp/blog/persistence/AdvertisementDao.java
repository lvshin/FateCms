package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.Advertisement;

@Repository
public class AdvertisementDao extends BaseDao<Advertisement>{

	public List<Advertisement> page(int curPage, int pageSize){
		String hql = "from reinforce_advertisement a order by id desc";
		return em.createQuery(hql, Advertisement.class).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(){
		String hql = "select count(*) from reinforce_advertisement";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
	
	public Advertisement findLastByType(int type){
		String hql = "from reinforce_advertisement where type=:type and active=true order by id desc";
		List<Advertisement> list = em.createQuery(hql, Advertisement.class).setParameter("type", type).setFirstResult(0).setMaxResults(1).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
