package fate.webapp.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 文章的标签类，主要用于SEO，相当于定义keyword
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_theme_tag")
public class ThemeTag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * 标签名称（内容）
	 */
	@Column(name = "tag_name")
	private String tagName;
	
	/**
	 * 最后使用标签的时间
	 */
	@Column(name = "last_used")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUsed;
	
	/**
	 * 标签属于哪个用户,因为现在的博客程序只能博主发文，所以该字段没有实质影响
	 */
	@OneToOne
	@JoinColumn(name = "uid")
	private User user;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
