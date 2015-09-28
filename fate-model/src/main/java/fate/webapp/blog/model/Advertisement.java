package fate.webapp.blog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 广告位
 * @author Administrator
 *
 */
@Table
@Entity(name = "reinforce_advertisement")
public class Advertisement implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7788154747193070780L;

	/**
	 * 页面底部广告
	 */
	public final static int TYPE_BOTTOM = 1;
	
	/**
	 * 主题页右侧导航广告
	 */
	public final static int TYPE_RIGHT = 2;
	
	/**
	 * 主题文内广告
	 */
	public final static int TYPE_INSIDE = 3;
	
	/**
	 * 页面两侧空隙的悬浮广告
	 */
	public final static int TYPE_TWOSIDE = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * 广告类型，就是位置
	 */
	@Column
	private int type;
	/**
	 * 给广告的命名
	 */
	@Column
	private String name;
	/**
	 * 广告代码
	 */
	@Column(columnDefinition = "longtext")
	private String code;
	
	/**
	 * 广告是否有效
	 */
	@Column
	private boolean active;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
