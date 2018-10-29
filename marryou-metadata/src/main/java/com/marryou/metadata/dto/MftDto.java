package com.marryou.metadata.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by linhy on 2018/6/3.
 */
public class MftDto implements Serializable {

    private static final long serialVersionUID = -442197203738586978L;

    private Long id;

    private String name;

    private String info;

    /**
     * 状态0=正常，1=失效
     */
    private Integer status;

    private String createBy;

    private Date createTime;

    private Date modifyTime;

    private String modifyBy;

    private String tenantCode;

    public MftDto() {
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

    @Override
    public String toString() {
        return "MftDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", status=" + status +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", modifyBy='" + modifyBy + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}
