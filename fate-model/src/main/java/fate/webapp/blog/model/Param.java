package fate.webapp.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 存放一些零散的系统参数
 * @author Fate
 *
 */
@Entity
@Table(name = "reinforce_param")
public class Param {

	public static final int TYPE_TEXT = 1;
	
	public static final int TYPE_INT = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name")
	private String key;
	
	@Column(name = "text_value",columnDefinition = "longtext")
	private String textValue;
	
	@Column(name = "int_value", nullable = false)
	private int intValue;
	
	@Column(name = "type")
	private int type;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getTextValue() {
		return textValue;
	}
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
