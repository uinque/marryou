package com.marryou.metadata.dto;



import java.io.Serializable;
import java.util.Date;

/**
 * Created by linhy on 2018/6/3.
 */
public class LogDto implements Serializable {

    private static final long serialVersionUID = 7103870425642661060L;

    private Long id;

    private String content;
    /**
     * 0="新增",1="删除",2="更新",3= "其它"
     */
    private Integer operateType;

    private Long relationId;
    /**
     * 0="用户",1="公司",2="产品",3="出库单",4="生厂商",5="标准"
     */
    private Integer type;

    private String createBy;

    private Date createTime;

    private String startTime;

    private String endTime;

    private String tenantCode;

    public LogDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
