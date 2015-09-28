package fate.webapp.blog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页信息存为单例，减少数据库查询
 * @author Fate
 *
 */
public class Index {

	private static Index index = new Index();
	private String title;
	private String keywords;
	private String description;
	private List<Map<String, Object>> list;
	private Map<Integer,List<Theme>> themes;
	private List<Map<String, Object>> navis;
	private long count;
	private List<Theme> hot;
	private List<Theme> searchHot;
	private Announcement announcement;
	private Advertisement advRight;
	private Advertisement advBottom;
	private List<FriendLink> friendLinks;//友链
	private List<Map<String, Object>> friendLinkCheck;//友链检查
	
	private Param logId;//多说的log_id
	
	private Index() {
		super();
		// TODO Auto-generated constructor stub
		themes = new HashMap<Integer, List<Theme>>();
		navis = new ArrayList<Map<String,Object>>();
		friendLinks = new ArrayList<FriendLink>();
	}
	public static Index getInstance() {
		return index;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	public Map<Integer, List<Theme>> getThemes() {
		return themes;
	}
	public void setThemes(Map<Integer, List<Theme>> themes) {
		this.themes = themes;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<Theme> getHot() {
		return hot;
	}
	public void setHot(List<Theme> hot) {
		this.hot = hot;
	}
	public List<Theme> getSearchHot() {
		return searchHot;
	}
	public void setSearchHot(List<Theme> searchHot) {
		this.searchHot = searchHot;
	}
	public Announcement getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}
	public Advertisement getAdvRight() {
		return advRight;
	}
	public void setAdvRight(Advertisement advRight) {
		this.advRight = advRight;
	}
	public List<Map<String, Object>> getNavis() {
		return navis;
	}
	public void setNavis(List<Map<String, Object>> navis) {
		this.navis = navis;
	}
	public Advertisement getAdvBottom() {
		return advBottom;
	}
	public void setAdvBottom(Advertisement advBottom) {
		this.advBottom = advBottom;
	}
	public List<FriendLink> getFriendLinks() {
		return friendLinks;
	}
	public void setFriendLinks(List<FriendLink> friendLinks) {
		this.friendLinks = friendLinks;
	}
	public List<Map<String, Object>> getFriendLinkCheck() {
		return friendLinkCheck;
	}
	public void setFriendLinkCheck(List<Map<String, Object>> friendLinkCheck) {
		this.friendLinkCheck = friendLinkCheck;
	}
	public Param getLogId() {
		return logId;
	}
	public void setLogId(Param logId) {
		this.logId = logId;
	}
	
}
