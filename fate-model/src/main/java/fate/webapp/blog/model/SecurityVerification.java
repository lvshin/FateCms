package fate.webapp.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 记录用户发出的验证申请，在验证成功后，在User中做记录，然后删除本条申请 有一种用户发出申请后一直不验证的情况，要设置定时任务将过期的申请删除
 * 
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_security_verification")
public class SecurityVerification {

	/**
	 * 邮箱验证
	 */
	public final static int VERIFICATION_TYPE_EMAIL = 1;

	/**
	 * 手机验证
	 */
	public final static int VERIFICATION_TYPE_MOBILE = 2;

	/**
	 * 验证状态：未验证
	 */
	public final static int VERIFICATION_STATUS_FAIL = 0;

	/**
	 * 验证状态：已验证
	 */
	public final static int VERIFICATION_STATUS_SUCCESS = 1;

	/**
	 * 验证的guid
	 */
	@Id
	@GeneratedValue(generator = "verificationGuid")
	@GenericGenerator(name = "verificationGuid", strategy = "uuid")
	@Column(name = "guid", length = 32)
	private String guid;

	/**
	 * 验证码
	 */
	@Column
	private String code;

	/**
	 * 验证类型，是邮箱还是手机
	 */
	@Column(name = "verification_type")
	private int verificationType;

	/**
	 * 验证的邮箱或是手机号
	 */
	@Column
	private String value;

	/**
	 * 验证发出的时间，当前时间-发出时间和超时时间对比，判断是否超时
	 */
	@Column(name = "verification_time")
	private Date verificationTime;

	/**
	 * 超时时间
	 */
	@Column(name = "timeout")
	private int timeout;
	/**
	 * 验证状态，即是否验证成功， 旧属性，已弃用，现在记录在User中
	 */
	@Column
	private int status;
	/**
	 * 哪个用户发出的申请
	 */
	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(int verificationType) {
		this.verificationType = verificationType;
	}

	public Date getVerificationTime() {
		return verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
