package fate.webapp.blog.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 多说的同步评论到本地
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_duoshuo")
public class DuoShuo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4889763483155613705L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "log_id")
	private long logId;
	
	@Column(name = "post_id")
	private long postId;//多说的评论id
	
	@Column(name = "thread_id")
	private long threadId;//文章在多说的的id
	
	@ManyToOne
	@JoinColumn(name = "thread_key")
	private Theme theme;
	
	@Column(name = "author_id")
	private int authorId;//作者在多说的id
	
	@Column(name = "author_name")
	private String authorNmae;//作者在多说的显示名
	
	@Column(name = "author_email")
	private String authorEmail;
	
	@Column(name = "author_url")
	private String authorUrl;//作者的网站
	
	@Column(name = "author_key")
	private String authorKey;//作者在原站中对应的id
	
	@Column
	private String ip;//作者的ip
	
	@Column(name = "created_at")
	private Date createdAt;//评论创建日期
	
	@Column
	private String message;
	
	@Column
	private String status;//评论状态。创建评论时，可能的状态：approved：已经通过；pending：待审核；spam：垃圾评论。
	
	@Column(name = "parent_id")
	private long parentId;
	
	@Column
	private String type;//类型。现在均为空。
	
	@Column(name = "last_modify")
	private long lastModify;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorNmae() {
		return authorNmae;
	}

	public void setAuthorNmae(String authorNmae) {
		this.authorNmae = authorNmae;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public String getAuthorKey() {
		return authorKey;
	}

	public void setAuthorKey(String authorKey) {
		this.authorKey = authorKey;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getLastModify() {
		return lastModify;
	}

	public void setLastModify(long lastModify) {
		this.lastModify = lastModify;
	}

	@Override
	public String toString() {
		return "DuoShuo [id=" + id + ", logId=" + logId + ", postId=" + postId
				+ ", threadId=" + threadId + ", threadKey=" + theme.getGuid()
				+ ", authorId=" + authorId + ", authorNmae=" + authorNmae
				+ ", authorEmail=" + authorEmail + ", authorUrl=" + authorUrl
				+ ", authorKey=" + authorKey + ", ip=" + ip + ", createdAt="
				+ createdAt + ", message=" + message + ", status=" + status
				+ ", parentId=" + parentId + ", type=" + type + ", lastModify="
				+ lastModify + "]";
	}
	
	
}
