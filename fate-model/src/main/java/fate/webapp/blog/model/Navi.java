package fate.webapp.blog.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 自定义导航
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_navi")
public class Navi {

	/**
	 * 主导航
	 */
	public static final int TYPE_NAVI = 1;
	
	/**
	 * 下拉菜单项
	 */
	public static final int TYPE_SUB = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String name;//导航名称
	
	@Column
	private String url;//对应地址
	
	@Column
	private int type;
	
	@Column(name = "navi_order")
	private int order;//显示顺序
	
	@ManyToOne
	@JoinColumn(name = "parent_navi_id")
	@JsonIgnore
	private Navi parent;
	
	@OneToMany(mappedBy = "parent",fetch = FetchType.EAGER)
	@OrderBy(value = "order asc")
	@JsonIgnore
	private List<Navi> childs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Navi getParent() {
		return parent;
	}

	public void setParent(Navi parent) {
		this.parent = parent;
	}

	public List<Navi> getChilds() {
		return childs;
	}

	public void setChilds(List<Navi> childs) {
		this.childs = childs;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
