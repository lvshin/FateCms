package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.Navi;

@Repository
public class NaviDao extends BaseDao<Navi>{

	public List<Navi> searchRoot(){
		String hql = "from Navi n where n.parent is null order by n.order asc";
		return em.createQuery(hql, Navi.class).getResultList();
	}
}
