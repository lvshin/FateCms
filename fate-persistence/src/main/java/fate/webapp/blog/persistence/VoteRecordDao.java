package fate.webapp.blog.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.VoteRecord;

@Repository
public class VoteRecordDao extends BaseDao<VoteRecord>{

	public VoteRecord findBySessionAndTheme(String sessionId,String guid) {
		String hql = "select * from reinforce_vote_record where session_id=:sessionId and theme_guid=:guid";
		List<VoteRecord> list = em.createNativeQuery(hql, VoteRecord.class).setParameter("sessionId", sessionId).setParameter("guid", guid).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
	
	public VoteRecord findBySessionAndComment(String sessionId,String commentGuid) {
		String hql = "select * from reinforce_vote_record where session_id=:sessionId and comments_guid=:commentGuid";
		List<VoteRecord> list = em.createNativeQuery(hql, VoteRecord.class).setParameter("sessionId", sessionId).setParameter("commentGuid", commentGuid).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
