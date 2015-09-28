package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.Param;

@Repository
public class ParamDao extends BaseDao<Param>{

	public Param findByKey(String key){
		String hql = "from Param p where p.key=:key";
		List<Param> list = em.createQuery(hql, Param.class).setParameter("key", key).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
