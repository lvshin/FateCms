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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 旧版原来本地的评论，现在已改用多说
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_comments")
public class Comments implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7673925758273124286L;

	@Id
	@GeneratedValue(generator = "commentGuid")     
	@GenericGenerator(name = "commentGuid", strategy = "uuid")   
    @Column(name = "guid", length=32, nullable=false)
	private String guid;
	
	/**
	 * 评论内容
	 */
	@Column(name = "comment_content")
	private String commentContent;
	
	/**
	 * 哪篇文章的评论
	 */
	@ManyToOne
	@JoinColumn(name = "tid")
	private Theme theme;
	
	/**
	 * 评论时间
	 */
	@Column(name = "comment_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentDate = new Date();
	
	/**
	 * 评论者
	 */
	@OneToOne
	@JoinColumn(name = "uid")
	private User user;
	
	/**
	 * 被回复人
	 */
	@OneToOne
	@JoinColumn(name = "to_uid")
	private User to;
	
	/**
	 * 赞
	 */
	@Column(name = "up", nullable=false)
	private int up;
	
	/**
	 * 踩
	 */
	@Column(name = "down", nullable=false)
	private int down;
	
	/**
	 * 可能有回复评论的情况
	 * parentId为空则表示该评论是回复文章的
	 * 不为空则表示该评论是回复其他评论的
	 */
	@ManyToOne
	@JoinColumn(name = "comment_parent")
	private Comments commentParent;
	
	/**
	 * 本条评论的被回复
	 */
	@OneToMany(mappedBy = "commentParent",fetch = FetchType.EAGER)
	@OrderBy(value = "comment_date desc")
	private List<Comments> comments;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comments getCommentParent() {
		return commentParent;
	}

	public void setCommentParent(Comments commentParent) {
		this.commentParent = commentParent;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
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
	
	
}
