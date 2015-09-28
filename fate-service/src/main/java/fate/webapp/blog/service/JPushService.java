package fate.webapp.blog.service;

import java.util.Date;

/**
 * 极光推送服务
 * @author 幻幻
 *
 */
public interface JPushService {

	public boolean pushAlias(String title, String content, Date publishDate, String id, String alias);
	
	public boolean pushTag(String title, String content, Date publishDate, String id, String tag);
	
	public boolean pushAll(String title, String content, Date publishDate, String id);
}
