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
 * 操作日志
 * @author Fate
 *
 */
@Table
@Entity(name = "reinforce_apiLog")
public class ApiLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "operation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operationDate;// 操作日期
	
	@Column(name = "operation_user")
	private String operationUser;// 操作用户
	
	@Column(name = "operation_user_id")
	private int operationUserId;//操作用户id;
	
	@Column
	private String ip;// 操作IP
	
	@Column
	private String opertionContent;// 操作内容

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	
	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
	
	public int getOperationUserId() {
		return operationUserId;
	}

	public void setOperationUserId(int operationUserId) {
		this.operationUserId = operationUserId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOpertionContent() {
		return opertionContent;
	}

	public void setOpertionContent(String opertionContent) {
		this.opertionContent = opertionContent;
	}
	
	
}
