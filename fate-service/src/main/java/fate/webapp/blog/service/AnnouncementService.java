package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.Announcement;

public interface AnnouncementService {

	public List<Announcement> findAll();
	
	public Announcement find(int id);
	
	public void save(Announcement announcement);
	
	public Announcement update(Announcement announcement);
	
	public void delete(Announcement announcement);
	
	public List<Announcement> page(int curPage, int pageSize);
	
	public long count();
	
	/**
	 * 获取最新的公告
	 * @return
	 */
	public Announcement findLast();
}
