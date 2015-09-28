package fate.webapp.blog.service;

import fate.webapp.blog.model.DuoShuo;


public interface DuoShuoService {

	public void save(DuoShuo duoShuo);
	
	public DuoShuo update(DuoShuo duoShuo);
	
	public void delete(DuoShuo duoShuo);
	
	public DuoShuo find(int id);
	
	/**
	 * 统计评论总数
	 * @return
	 */
	public long count();
	
	public void update(String ids, String action, long logId);
	
	public long findLastLogId();
	
	public void delete(String ids);
}
