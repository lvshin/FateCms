package fate.webapp.blog.model;

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
 * 各大搜索引擎的蜘蛛访问记录
 * @author 幻幻
 *
 */
@Entity
@Table(name = "reinforce_spider")
public class Spider {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * 蜘蛛名称
	 */
	@Column(name = "spider_name")
	private String spiderName;
	
	/**
	 * 蜘蛛ip
	 */
	@Column(name = "spider_ip")
	private String spiderIp;
	
	/**
	 * 蜘蛛访问的时间
	 */
	@Column(name = "spider_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date spiderTime;
	
	/**
	 * 蜘蛛访问的url
	 */
	@Column(name = "spider_url")
	private String spiderUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpiderName() {
		return spiderName;
	}

	public void setSpiderName(String spiderName) {
		this.spiderName = spiderName;
	}

	public String getSpiderIp() {
		return spiderIp;
	}

	public void setSpiderIp(String spiderIp) {
		this.spiderIp = spiderIp;
	}

	public Date getSpiderTime() {
		return spiderTime;
	}

	public void setSpiderTime(Date spiderTime) {
		this.spiderTime = spiderTime;
	}

	public String getSpiderUrl() {
		return spiderUrl;
	}

	public void setSpiderUrl(String spiderUrl) {
		this.spiderUrl = spiderUrl;
	}
	
	
}
