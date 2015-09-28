package fate.webapp.blog.service;

import fate.webapp.blog.model.SystemMessage;

public interface SystemMessageService {

	public Long getUnreadCount(int uid);
	
	public void save(SystemMessage systemMessage);
}
