package fate.webapp.blog.service;

import fate.webapp.blog.model.Comments;

public interface CommentsService {

	public void save(Comments comments);
	
	public Comments update(Comments comments);
	
	public void delete(Comments comments);
	
	public Comments find(String commemtsGuid);
	
	/**
	 * 统计评论总数
	 * @return
	 */
	public long count();
}
