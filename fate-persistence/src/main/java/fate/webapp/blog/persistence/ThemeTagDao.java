package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.ThemeTag;

@Repository
public class ThemeTagDao extends BaseDao<ThemeTag>{

	public ThemeTag findByNameAndUser(String tagName, int uid){
		String hql = "from ThemeTag t where t.tagName=:tagName and t.user.uid=:uid";
		List<ThemeTag> tags = em.createQuery(hql, ThemeTag.class).setParameter("tagName", tagName).setParameter("uid", uid).getResultList();
		if(tags.size()==0)
			return null;
		else
			return tags.get(0);
	}
	
	public List<ThemeTag> pageByDateAndUser(int curPage, int pageSize, int uid){
		String hql = "from ThemeTag t where t.user.uid=:uid order by t.lastUsed";
		return em.createQuery(hql, ThemeTag.class).setParameter("uid", uid).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public List<ThemeTag> random(int pageSize){
		String sql = "SELECT t1.* FROM reinforce_theme_tag t1 JOIN(SELECT ROUND(RAND() * (SELECT MAX(id) FROM reinforce_theme_tag)) id) t2 WHERE t1.id>=t2.id ORDER BY t1.id ASC LIMIT :pageSize";
		return em.createNativeQuery(sql, ThemeTag.class).setParameter("pageSize", pageSize).getResultList();
	}
}
