package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.Media;

@Repository
public class MediaDao extends BaseDao<Media>{

	public Media findByUrl(String url){
		String hql = "from Media m where m.url=:url";
		List<Media> list = em.createQuery(hql, Media.class).setParameter("url", url).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
