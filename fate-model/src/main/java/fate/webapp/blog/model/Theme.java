package fate.webapp.blog.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Where;
/**
 * 文章类，用于存放像新闻等需要通过富文本编辑器编辑的内容，展示的时候通常是连格式一起输出到页面
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_theme")
public class Theme implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6482387751112597160L;

	public final static int TYPE_NORMAL = 0;
	
	public final static int TYPE_AUDIO = 1;
	
	public final static int TYPE_VIDEO = 2;

	/**
	 * 草稿状态
	 */
	public final static int STATE_EDIT = 1;
	
	/**
	 * 发布状态
	 */
	public final static int STATE_PUBLISH = 2;
	
	/**
	 * 文章的tid
	 */
	@Id
	@GeneratedValue(generator = "themeGuid")     
	@GenericGenerator(name = "themeGuid", strategy = "uuid")   
    @Column(name = "guid", length=32, nullable=false)
	private String guid;
	
	/**
	 * 文章标题
	 */
	@Column(nullable=false)
	private String title;
	
	/**
	 * 文章主要内容
	 */
	@Column(columnDefinition="longtext")
	private String content;
	
	/**
	 * 文章发布时间
	 */
	@Column(name = "publish_date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishDate;
	
	@Column(name = "last_modify", columnDefinition="timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModify;
	
	/**
	 * 作者昵称
	 */
	@Column(nullable=false)
	private String author;
	
	/**
	 * 作者Id,查看作者信息用的，暂时保留
	 */
	@Column(name = "author_id", nullable=false)
	private int authorId;
	
	/**
	 * 逻辑删除，供回收站使用
	 */
	@Column(name = "is_delete")
	private boolean isDelete;
	
	/**
	 * 文章是草稿还是发布状态
	 */
	@Column(nullable = false)
	private int state;
	
	/**
	 * 主题类型
	 */
	@Column(nullable = false)
	private int type;
	
	/**
	 * 文章对应的url
	 */
	@Column(name = "url", length=500)
	private String url;
	
	/**
	 * 浏览次数
	 */
	@Column(nullable=false)
	private int views;
	
	/**
	 * 评论数
	 */
	@Column(nullable=false)
	private int replies;
	
	/**
	 * 搜索次数
	 */
	@Column(nullable = false)
	private int search;
	
	/**
	 * 优先级
	 */
	@Column(nullable=false)
	private int priority;
	
	/**
	 * 文章的标签，主要做SEO用
	 */
	@Column
	private String tags;
	
	/**
	 * 赞
	 */
	@Column(nullable=false)
	private int up;
	
	/**
	 * 踩
	 */
	@Column(nullable=false)
	private int down;
	
	@OneToMany(mappedBy = "theme",fetch = FetchType.EAGER)
	@OrderBy(value = "comment_date desc")
	private List<Comments> comments;//文章下的本地评论
	
	@OneToMany(mappedBy = "theme",fetch = FetchType.EAGER)
	@OrderBy(value = "post_id desc")
	@Where(clause = "status = 'approved'")
	private List<DuoShuo> duoShuos;//文章下的多说评论
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "forum_id")
	private Forum forum;//所属版块
	
	@OneToMany(mappedBy = "theme",fetch = FetchType.EAGER)
	private List<Media> medias;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	
	public List<DuoShuo> getDuoShuos() {
		return duoShuos;
	}

	public void setDuoShuos(List<DuoShuo> duoShuos) {
		this.duoShuos = duoShuos;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public int getSearch() {
		return search;
	}

	public void setSearch(int search) {
		this.search = search;
	}

	public List<Media> getMedias() {
		return medias;
	}

	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
