package fate.webapp.blog.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 版块类，包含区域，版块，子版块3个类型
 * 原本是参照discuz的版块做的，现在直接用于博客的版块上
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_forum")
public class Forum implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6753675374406318506L;

	/**
	 * 分区
	 */
	public final static int TYPE_REGION = 1;
	
	/**
	 * 版块
	 */
	public final static int TYPE_FORUM = 2;
	
	/**
	 * 子版块
	 */
	public final static int TYPE_SUB = 3;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fid;
	
	/**
	 * 板块名称
	 */
	@Column(name = "forum_name")
	private String forumName;
	
	/**
	 * 板块图标，非区域的时候可用
	 */
	@Column(name = "forum_icon")
	private String forumIcon;
	
	/**
	 * 版块描述，可以写版规，版块介绍之类的内容
	 */
	@Column(name = "forum_desc")
	private String forumDesc;
	
	/**
	 * 版块显示顺序
	 */
	@Column(name = "forum_order")
	private int forumOrder;
	
	/**
	 * 图标宽度，非区域的时候可用
	 */
	@Column(name = "icon_width")
	private String iconWidth;
	
	/**
	 * 板块类型
	 */
	@Column
	private int type;
	
	/**
	 * 父版块
	 */
	@ManyToOne
	@JoinColumn(name = "parent_forum_id")
	@JsonIgnore
	private Forum parentForum;
	
	/**
	 * 子版块
	 */
	@OneToMany(mappedBy = "parentForum",fetch = FetchType.EAGER)
	@OrderBy(value = "forumOrder asc")
	@JsonIgnore
	private List<Forum> childForums;
	
	/**
	 * SEO:网页中的title
	 */
	@Column
	private String title;
	
	/**
	 * SEO:网页中的keywords
	 */
	@Column
	private String keywords;
	
	/**
	 * SEO:网页中的description
	 */
	@Column
	private String description;
	
	/**
	 * 版块下最后发布的文章，也就是最新文章
	 */
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "last_post")
	@JsonIgnore
	private Theme lastPost;

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getForumIcon() {
		return forumIcon;
	}

	public void setForumIcon(String forumIcon) {
		this.forumIcon = forumIcon;
	}

	public String getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(String iconWidth) {
		this.iconWidth = iconWidth;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Forum getParentForum() {
		return parentForum;
	}

	public void setParentForum(Forum parentForum) {
		this.parentForum = parentForum;
	}

	public List<Forum> getChildForums() {
		return childForums;
	}

	public void setChildForums(List<Forum> childForums) {
		this.childForums = childForums;
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

	public Theme getLastPost() {
		return lastPost;
	}

	public void setLastPost(Theme lastPost) {
		this.lastPost = lastPost;
	}

	public int getForumOrder() {
		return forumOrder;
	}

	public void setForumOrder(int forumOrder) {
		this.forumOrder = forumOrder;
	}

	public String getForumDesc() {
		return forumDesc;
	}

	public void setForumDesc(String forumDesc) {
		this.forumDesc = forumDesc;
	}
	
	
}
