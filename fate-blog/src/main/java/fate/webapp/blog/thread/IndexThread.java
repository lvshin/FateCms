package fate.webapp.blog.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Forum;
import fate.webapp.blog.model.Index;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.model.Theme;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.service.ThemeService;

@Service
public class IndexThread implements Runnable{

	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ParamService paramService;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Index index = Index.getInstance();
		List<Forum> forums = forumService.searchRoot();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Forum f : forums) {
			list.add(forumToJson(f));
		}
		index.setList(list);
		
		Param title = paramService.findByKey(Constants.SEO_INDEX_TITLE);
		Param keywords = paramService.findByKey(Constants.SEO_INDEX_KEYWORDS);
		Param description = paramService.findByKey(Constants.SEO_INDEX_DESCRIPTION);
		
		index.setTitle(title!=null?title.getTextValue():"");
		index.setKeywords(keywords!=null?keywords.getTextValue():"");
		index.setDescription(description!=null?description.getTextValue():"");
	}
	
	public Map<String, Object> forumToJson(Forum forum) {
		Map<String, Object> map = new HashMap<String, Object>();
		long start = System.currentTimeMillis();
		map.put("forumName", forum.getForumName());
		map.put("type", forum.getType());
		map.put("fid", forum.getFid());
		map.put("forumOrder", forum.getForumOrder());
		map.put("parentFid", forum.getParentForum()==null?0:forum.getParentForum().getFid());
		map.put("forumIcon", forum.getForumIcon());
		map.put("iconWidth", forum.getIconWidth());
		if(forum.getType()!=Forum.TYPE_REGION){
			long count = themeService.count(forum.getFid(), false, Theme.STATE_PUBLISH);
			Theme theme = themeService.getLastestTheme(forum.getFid());
			map.put("theme", count);
			if(theme!=null){
				map.put("title", theme.getTitle());
				map.put("url", theme.getUrl());
				map.put("author", theme.getAuthor());
				map.put("publishDate", theme.getPublishDate());
				map.put("img", getImg(theme.getContent()));
			}
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (forum.getChildForums() != null && forum.getChildForums().size() > 0) {

			for (Forum f : forum.getChildForums()) {
				list.add(forumToJson(f));
			}
		}
		map.put("children", list);
		long end = System.currentTimeMillis();
		return map;
	}
	
	public String getImg(String content){
		content = content.substring(content.indexOf("<img")+4);
		if(content.indexOf("\"")==-1)
			return "";
		content = content.substring(content.indexOf("src=")+5);
		if(content.indexOf("\"")==-1)
			return "";
		content = content.substring(0,content.indexOf("\""));
		return content;
	}

}
