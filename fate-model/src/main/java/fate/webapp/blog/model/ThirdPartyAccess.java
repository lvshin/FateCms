package fate.webapp.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用于存放第三方登录的accessKey和accessSecret，如QQ，新浪
 * 
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_third_party_access")
public class ThirdPartyAccess {

	public static final int TYPE_QQ = 0;

	public static final int TYPE_XINLANG = 1;

	@Id
	@GeneratedValue(generator = "tpaGuid")
	@GenericGenerator(name = "tpaGuid", strategy = "uuid")
	@Column(name = "guid", length = 32)
	private String guid;

	@Column
	private int type;

	@Column(name = "access_key")
	private String accessKey;

	@Column(name = "access_secret")
	private String accessSecret;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}

}
