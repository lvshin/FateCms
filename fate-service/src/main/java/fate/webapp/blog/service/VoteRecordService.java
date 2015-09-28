package fate.webapp.blog.service;

import fate.webapp.blog.model.VoteRecord;

public interface VoteRecordService {

	public boolean exists(String sessionId,String guid, int type);
	
	public void save(VoteRecord commentVoteRecord);
}
