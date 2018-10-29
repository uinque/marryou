package com.marryou.metadata.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/3.
 */
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 1354959855351613003L;

    private Long id;

    private String name;
    /**
     * 类型：0=粉煤灰，1=铁路运输
     */
    private Integer type;
    /**
     * 打印抬头
     */
    private String printName;
    /**
     * 打印备注
     */
    private String remark;
    /**
     * 状态：0=有效，1=失效
     */
    private Integer status;

    private String startTime;

    private String endTime;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String tenantCode;

    private List<StandardDto> standards;

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public List<StandardDto> getStandards() {
        return standards;
    }

    public void setStandards(List<StandardDto> standards) {
        this.standards = standards;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", printName='" + printName + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", modifyBy='" + modifyBy + '\'' +
                ", modifyTime=" + modifyTime +
                ", tenantCode='" + tenantCode + '\'' +
                ", standards=" + standards +
                '}';
    }
}
