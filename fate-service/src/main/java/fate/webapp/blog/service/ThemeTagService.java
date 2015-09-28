package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.ThemeTag;

public interface ThemeTagService {

	/**
	 * 通过标签名称查找某个用户下的标签
	 * @param tagName
	 * @param uid
	 * @return
	 */
	public ThemeTag findByNameAndUser(String tagName, int uid);
	
	/**
	 * 对某个用户下的标签按最后使用时间降序排列，分页
	 * @param curPage
	 * @param pageSize
	 * @param uid
	 * @return
	 */
	public List<ThemeTag> pageByDateAndUser(int curPage, int pageSize, int uid);
	
	public void  save(ThemeTag themeTag);
	
	/**
	 * 判断某个用户下是否存在某个标签
	 * @param tagName
	 * @param uid
	 * @return
	 */
	public boolean exits(String tagName, int uid);
	
	public ThemeTag update(ThemeTag themeTag);
	
	/**
	 * 随机选取tag
	 * @param pageSize
	 * @return
	 */
	public List<ThemeTag> random(int pageSize);
}
