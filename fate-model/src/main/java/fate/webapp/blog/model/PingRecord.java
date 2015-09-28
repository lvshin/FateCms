package fate.webapp.blog.model;

import java.io.Serializable;
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
 * ping搜索引擎的记录
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_ping_record")
public class PingRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2562828447241183397L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "theme_guid")
	private Theme theme;
	
	/**
	 * ping百度是否成功
	 */
	@Column(name = "baidu")
	private boolean baidu;
	
	/**
	 * ping google是否成功
	 */
	@Column(name = "google")
	private boolean google;
	
	/**
	 * ping的时间
	 */
	@Column(name = "ping_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pingDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public boolean getBaidu() {
		return baidu;
	}

	public void setBaidu(boolean baidu) {
		this.baidu = baidu;
	}

	public boolean getGoogle() {
		return google;
	}

	public void setGoogle(boolean google) {
		this.google = google;
	}

	public Date getPingDate() {
		return pingDate;
	}

	public void setPingDate(Date pingDate) {
		this.pingDate = pingDate;
	}
	
}
