package fate.webapp.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 存储友链
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_friendlink")
public class FriendLink {

	public final static int STATE_APPLY = 0;
	
	public final static int STATE_PASS = 1;
	
	public final static int STATE_DENIED = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String url;
	
	@Column(name = "site_name")
	private String siteName;
	
	@Column
	private String email;//站长邮箱，用于提示处理结果
	
	@Column
	private Date applyDate = new Date();//申请时间
	
	@Column
	private int state;//状态
	
	@Column
	private String reason;//意见

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	
}
