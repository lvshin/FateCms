package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.ExceptionLog;

public interface ExceptionLogService {

	public List<ExceptionLog> page(int status, int curPage, int pageSize);
	
	public long count(int status);
	
	public void save(int status, String url, String source, String agent, String remark);
}
