package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.Forum;

public interface ForumService {

	/**
	 * 搜索根节点
	 * @return
	 */
	public List<Forum> searchRoot();
	
	public void save(Forum forum);
	
	public Forum update(Forum forum);
	
	public void delete(Forum forum);
	
	public Forum find(int id);
	
	/**
	 * 检查版块名称是否存在
	 * @param forumName
	 * @return
	 */
	public boolean checkForumName(String forumName);
	
	/**
	 * 搜索所有子节点
	 * @return
	 */
	public List<Forum> searchChildPoint();
}
