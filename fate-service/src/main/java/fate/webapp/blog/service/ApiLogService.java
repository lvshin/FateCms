package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.ApiLog;

public interface ApiLogService {
	public List<ApiLog> page(int curPage, int pageSize);

	public void save(ApiLog apiLog);

}
