package fate.webapp.blog.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 用户类
 * 
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5762385417954163682L;

	/**
	 * 管理员
	 */
	public static final int USER_TYPE_ADMIN = 1;

	/**
	 * 普通用户
	 */
	public static final int USER_TYPE_NORMAL = 0;

	/**
	 * 本地头像
	 */
	public static final int HEADICON_LOCAL = 0;

	/**
	 * QQ头像
	 */
	public static final int HEADICON_QQ = 1;

	/**
	 * 微博头像
	 */
	public static final int HEADICON_WEIBO = 2;

	/**
	 * 状态正常
	 */
	public static final int STATE_NORMAL = 0;

	/**
	 * 被禁封
	 */
	public static final int STATE_FORBIDDEN = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uid;

	/**
	 * 昵称
	 */
	@Column(name = "nickname")
	private String nickName;

	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 电子邮件
	 */
	@Column
	private String email;

	/**
	 * 手机号
	 */
	@Column
	private String mobile;

	/**
	 * 邮箱是否经过验证
	 */
	@Column(name = "email_status")
	private boolean emailStatus;
	/**
	 * 手机号是否经过验证
	 */
	@Column(name = "mobile_status")
	private boolean mobileStatus;
	/**
	 * 性别
	 */
	@Column
	private char sex;
	/**
	 * 生日
	 */
	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	private Date birthday;

	/**
	 * 住址
	 */
	@Column
	private String address;

	/**
	 * QQ号
	 */
	@Column
	private String qq;

	/**
	 * 哪一个头像被使用了
	 */
	@Column(name = "headicon_used", nullable = false)
	private int headIconUsed;

	/**
	 * 本地上传的头像
	 */
	@Column(name = "head_icon_local")
	private String headIconLocal;

	/**
	 * 小头像
	 */
	@Column(name = "head_icon_small")
	private String headIconSmall;
	/**
	 * 中头像
	 */
	@Column(name = "head_icon_mid")
	private String headIconMid;
	/**
	 * 大头像
	 */
	@Column(name = "head_icon_big")
	private String headIconBig;
	/**
	 * 高清头像
	 */
	@Column(name = "head_icon_hd")
	private String headIconHD;

	/**
	 * 用户状态
	 */
	@Column(nullable = false)
	private int state;
	/**
	 * 注册时间
	 */
	@Column(name = "activate_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date activateDate = new Date();
	/**
	 * 用户类型，普通用户还是管理员 管理员根据角色（权限）可以看到不同的内容
	 */
	@Column(name = "user_type")
	private int userType;

	/**
	 * 密码锁，是否允许修改密码
	 */
	@Column(name = "password_lock", nullable = false)
	private boolean passwordLock;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHeadIconSmall() {
		return headIconSmall;
	}

	public void setHeadIconSmall(String headIconSmall) {
		this.headIconSmall = headIconSmall;
	}

	public String getHeadIconMid() {
		return headIconMid;
	}

	public void setHeadIconMid(String headIconMid) {
		this.headIconMid = headIconMid;
	}

	public String getHeadIconBig() {
		return headIconBig;
	}

	public void setHeadIconBig(String headIconBig) {
		this.headIconBig = headIconBig;
	}

	public Date getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public boolean getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(boolean emailStatus) {
		this.emailStatus = emailStatus;
	}

	public boolean getMobileStatus() {
		return mobileStatus;
	}

	public void setMobileStatus(boolean mobileStatus) {
		this.mobileStatus = mobileStatus;
	}

	public String getHeadIconHD() {
		return headIconHD;
	}

	public void setHeadIconHD(String headIconHD) {
		this.headIconHD = headIconHD;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getHeadIconUsed() {
		return headIconUsed;
	}

	public void setHeadIconUsed(int headIconUsed) {
		this.headIconUsed = headIconUsed;
	}

	public String getHeadIconLocal() {
		return headIconLocal;
	}

	public void setHeadIconLocal(String headIconLocal) {
		this.headIconLocal = headIconLocal;
	}

	public boolean getPasswordLock() {
		return passwordLock;
	}

	public void setPasswordLock(boolean passwordLock) {
		this.passwordLock = passwordLock;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
