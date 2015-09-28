package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.Advertisement;

public interface AdvertisementService {

	public Advertisement find(int id);
	
	public List<Advertisement> page(int curPage, int pageSize);
	
	public long count();
	
	public void save(Advertisement advertisement);
	
	public Advertisement update(Advertisement advertisement);
	
	public void delete(Advertisement advertisement);
	
	/**
	 * 根据类型获取最新的广告
	 * @param type
	 * @return
	 */
	public Advertisement findLastByType(int type);
}
