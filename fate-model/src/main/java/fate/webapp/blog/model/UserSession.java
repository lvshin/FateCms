package fate.webapp.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 记录用户的登录状态
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_user_session")
public class UserSession {

	public final static int TYPE_LOCAL = 0;
	
	public final static int TYPE_QQ = 1;
	
	public final static int TYPE_XINLANG = 2;
	
	@Id
	@GeneratedValue(generator = "userSessionGuid")     
	@GenericGenerator(name = "userSessionGuid", strategy = "uuid")   
    @Column(name = "guid", length=32)
	private String guid;
	
	/**
	 * 属于哪个用户
	 */
	@OneToOne
	@JoinColumn(name = "uid")
	private User user;
	
	/**
	 * 用户当前登录状态下的sessionId，随机生成的
	 */
    @Column(name = "session_id")
	private String sessionId;
	
	/**
	 * 手机登录状态标识，可用于快速登录,保留
	 */
    @Column(name = "m_session_id")
	private String mSessionId;
	
	/**
	 * 用户的登录类型
	 */
	@Column
	private int type;
	
	/**
	 * 本次登录时间
	 */
	@Column(name = "login_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginDate;
	
	/**
	 * 上次登陆时间
	 */
	@Column(name = "last_login_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;
	
	/**
	 * 本次登录IP
	 */
	@Column(name = "login_ip")
	private String loginIp;
	
	/**
	 * 上次登录IP
	 */
	@Column(name = "last_login_ip")
	private String lastLoginIp;

	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	public String getmSessionId() {
		return mSessionId;
	}

	public void setmSessionId(String mSessionId) {
		this.mSessionId = mSessionId;
	}
}
