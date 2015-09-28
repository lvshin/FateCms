package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.Spider;

public interface SpiderService {

	public void save(Spider spider);
	
	public void delete(Spider spider);
	
	public List<Spider> page(int curPage, int pageSize);
	
	public long count();
}
