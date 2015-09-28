package fate.webapp.blog.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.VoteRecord;
import fate.webapp.blog.persistence.VoteRecordDao;
import fate.webapp.blog.service.VoteRecordService;

@Service
@Transactional
public class VoteRecordServiceImpl implements VoteRecordService {

	@Autowired
	private VoteRecordDao voteRecordDao;
	
	public boolean exists(String sessionId,String guid, int type) {
		if(type==1)
		return voteRecordDao.findBySessionAndTheme(sessionId, guid)==null?false:true;
		else
		return voteRecordDao.findBySessionAndComment(sessionId, guid)==null?false:true;
	}
	
	public void save(VoteRecord commentVoteRecord) {
		voteRecordDao.save(commentVoteRecord);
	}

}
