package fate.webapp.blog.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.SystemMessage;
import fate.webapp.blog.persistence.SystemMessageDao;
import fate.webapp.blog.service.SystemMessageService;

@Service
@Transactional
public class SystemMessageServiceImpl implements SystemMessageService {

	@Autowired
	private SystemMessageDao systemMessageDao;
	
	@Override
	public Long getUnreadCount(int uid) {
		return systemMessageDao.getUnreadCount(uid);
	}

	@Override
	public void save(SystemMessage systemMessage) {
		systemMessageDao.save(systemMessage);
	}

}
