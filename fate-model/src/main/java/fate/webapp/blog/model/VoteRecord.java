package fate.webapp.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 记录投票，防止刷投票
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_vote_record")
public class VoteRecord {

	/**
	 * 浏览器的sessionId
	 */
	@Id
	@Column(name = "session_id")
	private String sessionId;
	
	/**
	 * 对应哪条评论
	 */
	@OneToOne
	@JoinColumn(name = "comments_guid")
	private Comments comments;
	
	/**
	 * 文章和评论二者必有其一
	 */
	@OneToOne
	@JoinColumn(name = "theme_guid")
	private Theme theme;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

}
