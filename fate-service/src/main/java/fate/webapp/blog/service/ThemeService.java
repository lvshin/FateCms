package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.Theme;

public interface ThemeService {

	public Theme find(String guid);

	public void save(Theme theme);

	public void crush(Theme theme);
	
	public void crush(String guid);

	public Theme update(Theme theme);
	
	
	/**
	 * 按版块统计总数
	 * @param type
	 * @param isDelete
	 * @param state
	 * @return
	 */
	public long count(int fid, boolean isDelete, int state);
	
	/**
	 * 分页获取版块主题列表
	 * @param fid
	 * @param per
	 * @param curPage
	 * @param isDelete
	 * @return
	 */
	public List<Theme> pageByFid(int fid, int per, int curPage, int state, boolean isDelete);
	
	/**
	 * 获取版块的最新主题
	 * @param fid
	 * @return
	 */
	public Theme getLastestTheme(int fid);
	
	
	/**
	 * 获取某个类型的文章的统计数据
	 * @param fid 文章类型
	 * @param datetype 时间类型，是按天还是按月
	 * @param day 日期的字符串格式
	 * @return
	 */
	public long statistics(int fid,int datetype,String day);
	
	public Theme findByDateAndTitle(String date, String title);
	
	public List<Theme> pageByFid(int fid, int per, int curPage, boolean isDelete ,boolean timeOrder, boolean priority, int state);
	
	public List<Theme> findAll(boolean isDelete);
	
	/**
	 * 分页获取最热主题
	 * @param per
	 * @param curPage
	 * @param isDelete
	 * @param state
	 * @return
	 */
	public List<Theme> pageHot(int per, int curPage, boolean isDelete, int state);
	
	/**
	 * 分页获取搜索最多的主题
	 * @param per
	 * @param curPage
	 * @param isDelete
	 * @param state
	 * @return
	 */
	public List<Theme> pageSearchHot(int per, int curPage, boolean isDelete, int state);
	
	/**
	 * 统计站点主题浏览次数
	 * @param fid
	 * @param isDelete
	 * @param state
	 * @return
	 */
	public long countViews(int fid, boolean isDelete, int state);
	
	public List<Theme> pageByUid(int uid, int per, int curPage, boolean isDelete ,boolean order, int state);
	
	public long countByUid(int uid, boolean isDelete, int state);
	
	public List<Theme> pageByTag(String tag, int pageSize, int curPage, boolean isDelete);
	
	public long countByTag(String tag, boolean isDelete);
	
	/**
	 * 批量逻辑删除
	 * @param guids
	 */
	public void multiDelete(List<String> guids);

}
