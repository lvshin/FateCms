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
 * 公告实体类
 * 
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_announcement")
public class Announcement implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -3045523791919027862L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/**
	 * 发布人
	 */
	@OneToOne
	@JoinColumn(name = "anthor")
	private User anthor;

	/**
	 * 标题
	 */
	@Column
	private String title;

	/**
	 * 显示顺序
	 */
	@Column(name = "display_order")
	private int displayOrder;

	/**
     * 建立公告的时间
     */
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
	
	/**
	 * 开始时间
	 */
	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	/**
	 * 结束时间，和开始时间组成定时显示的功能
	 */
	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@Column(columnDefinition = "longtext")
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getAnthor() {
		return anthor;
	}

	public void setAnthor(User anthor) {
		this.anthor = anthor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
