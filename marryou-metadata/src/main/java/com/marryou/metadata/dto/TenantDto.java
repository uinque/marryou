package com.marryou.metadata.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by linhy on 2018/6/3.
 */
public class TenantDto implements Serializable {

    private static final long serialVersionUID = 6231052828200590279L;

    private Long id;
    //租户名称
    private String name;
    //租户信息
    private String info;
    //状态0=正常，1=失效
    private Integer status;
    //是否可修改出厂时间 0=不可修改，1=可修改
    private Integer modifyOutTimeFlag;
    /**
     * 是否需要审批人(0=不需要，1=需要)
     */
    private Integer approverFlag;
    //是否需要展示车牌(0=不需要，1=需要)
    private Integer showCarNoFlag;
    //租户编码
    private String tenantCode;

    private String createBy;

    private Date createTime;

    private Date modifyTime;

    private String modifyBy;

    public TenantDto() {
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Integer getModifyOutTimeFlag() {
        return modifyOutTimeFlag;
    }

    public void setModifyOutTimeFlag(Integer modifyOutTimeFlag) {
        this.modifyOutTimeFlag = modifyOutTimeFlag;
    }

    public Integer getApproverFlag() {
        return approverFlag;
    }

    public void setApproverFlag(Integer approverFlag) {
        this.approverFlag = approverFlag;
    }

    public Integer getShowCarNoFlag() {
        return showCarNoFlag;
    }

    public void setShowCarNoFlag(Integer showCarNoFlag) {
        this.showCarNoFlag = showCarNoFlag;
    }

    @Override
    public String toString() {
        return "TenantDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", status=" + status +
                ", modifyOutTimeFlag=" + modifyOutTimeFlag +
                ", approverFlag=" + approverFlag +
                ", showCarNoFlag=" + showCarNoFlag +
                ", tenantCode='" + tenantCode + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", modifyBy='" + modifyBy + '\'' +
                '}';
    }
}
