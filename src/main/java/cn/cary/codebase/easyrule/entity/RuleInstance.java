package cn.cary.codebase.easyrule.entity;

import cn.cary.codebase.easyrule.RuleStatus;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
* @author rwe
* @version 创建时间：2021年5月18日 下午5:35:27
* 规则实例实体类
*/

@TableName(value = "rule_instance", autoResultMap = true)
public class RuleInstance  implements Serializable {

	private static final long serialVersionUID = -1670268842529871692L;

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	
	@TableField
	private Long packageInstanceId;
	
	@TableField
	private String name;
	
	@TableField
	private String description;
	
	@TableField
	private Integer priority;
	
	@TableField
	@EnumValue
	private RuleStatus status;
	
	@TableField
	private Date startTime;
	
	@TableField
	private Date endTime;
	
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private Map<String,Object> input;
	
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private Map<String,Object> output;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPackageInstanceId() {
		return packageInstanceId;
	}

	public void setPackageInstanceId(Long packageInstanceId) {
		this.packageInstanceId = packageInstanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public RuleStatus getStatus() {
		return status;
	}

	public void setStatus(RuleStatus status) {
		this.status = status;
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

	public Map<String, Object> getInput() {
		return input;
	}

	public void setInput(Map<String, Object> input) {
		this.input = input;
	}

	public Map<String, Object> getOutput() {
		return output;
	}

	public void setOutput(Map<String, Object> output) {
		this.output = output;
	}
	
	
}
