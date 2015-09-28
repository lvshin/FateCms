package fate.webapp.blog.persistence;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.Comments;

@Repository
public class CommentsDao extends BaseDao<Comments>{

	public long count(){
		String hql = "select count(*) from Comments c";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
}
