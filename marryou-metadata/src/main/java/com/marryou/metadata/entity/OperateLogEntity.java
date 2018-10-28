package com.marryou.metadata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.marryou.metadata.entity.base.BaseEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

/**
 * Created by linhy on 2018/6/2.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "operate_log")
public class OperateLogEntity extends BaseEntity {

	private static final long serialVersionUID = -8327105104163431902L;

	private String content;

	private OperateTypeEnum operateType;

	private Long relationId;

	private LogTypeEnum type;

	public OperateLogEntity() {
	}

	public OperateLogEntity(String content, OperateTypeEnum operateType,
							Long relationId, LogTypeEnum type, String createBy,
							Date createTime, String tenantCode) {
		this.content = content;
		this.operateType = operateType;
		this.relationId = relationId;
		this.type = type;
		super.setCreateBy(createBy);
		super.setCreateTime(createTime);
		super.setTenantCode(tenantCode);
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "operate_type")
	public OperateTypeEnum getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateTypeEnum operateType) {
		this.operateType = operateType;
	}

	@Column(name = "relation_id")
	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	@Column(name = "log_type")
	public LogTypeEnum getType() {
		return type;
	}

	public void setType(LogTypeEnum type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "OperateLogEntity{" +
				"content='" + content + '\'' +
				", operateType=" + operateType +
				", relationId=" + relationId +
				", type=" + type +
				"} " + super.toString();
	}
}
