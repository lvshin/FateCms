package fate.webapp.blog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "reinforce_media")
public class Media implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1628452388705465277L;
	
	public final static int TYPE_MUSIC = 1;
	public final static int TYPE_VIDEO = 2;
	public final static int TYPE_PIC = 3;

	@Id
	@GeneratedValue(generator = "mediaGuid")
	@GenericGenerator(name = "mediaGuid", strategy = "uuid")
	@Column(name = "guid", length = 32)
	private String guid;

	/**
	 * 关联的文章
	 */
	@ManyToOne
	@JoinColumn(name = "tid")
	private Theme theme;

	/**
	 * 媒体的地址
	 */
	@Column(name = "url", columnDefinition = "longtext")
	private String url;

	/**
	 * 媒体名
	 */
	@Column
	private String title;

	/**
	 * 歌手
	 */
	@Column
	private String singer;

	/**
	 * 持续时间
	 */
	@Column
	private String lastTime;

	/**
	 * 媒体类型
	 */
	@Column
	private int type;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

}
