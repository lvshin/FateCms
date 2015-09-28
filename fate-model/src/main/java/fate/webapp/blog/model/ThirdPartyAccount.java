package fate.webapp.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 第三方登录后，把一些如openId，token的信息记录下来
 * 和本地账户绑定
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_third_party_account")
public class ThirdPartyAccount {

	public final static int ACCOUNT_TYPE_QQ = 1;
	
	public final static int ACCOUNT_TYPE_XINLANG = 2;
	
	@Id
	@GeneratedValue(generator = "thirdPartyGuid")     
	@GenericGenerator(name = "thirdPartyGuid", strategy = "uuid")   
    @Column(name = "guid", length=32)
	private String guid;
	
	@Column(name = "open_id")
	private String openId;
	
	/**
	 * 第三方登录权限
	 */
	@Column(name = "access_token")
	private String accessToken;
	
	@Column(name = "account_type")
	private int accountType;
	
	/**
	 * 和哪个用户绑定
	 */
	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;
	
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
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public String getHeadIconHD() {
		return headIconHD;
	}

	public void setHeadIconHD(String headIconHD) {
		this.headIconHD = headIconHD;
	}
}
