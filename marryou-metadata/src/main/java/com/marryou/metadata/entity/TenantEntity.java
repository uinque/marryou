package com.marryou.metadata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.marryou.metadata.entity.base.BaseEntity;
import com.marryou.metadata.enums.StatusEnum;

/**
 * Created by linhy on 2018/6/2.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tenant")
public class TenantEntity extends BaseEntity {

    private static final long serialVersionUID = 4873052956296068049L;

    public static final Integer FLAG_NO = 0;

    public static final Integer FLAG_ALLOW = 1;

    private String name;

    private String info;

    private StatusEnum status;

    /**
     * 是否允许修改出厂时间(0=不允许，1=允许)
     */
    private Integer modifyOutTimeFlag;
    /**
     * 是否需要审批人(0=不需要，1=需要)
     */
    private Integer approverFlag;
    /**
     * 是否需要展示车牌(0=不需要，1=需要)
     */
    private Integer showCarNoFlag;

    public TenantEntity() {
    }


    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "status")
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Column(name = "time_flag")
    public Integer getModifyOutTimeFlag() {
        return modifyOutTimeFlag;
    }

    public void setModifyOutTimeFlag(Integer modifyOutTimeFlag) {
        this.modifyOutTimeFlag = modifyOutTimeFlag;
    }

    @Column(name = "approver_flag")
    public Integer getApproverFlag() {
        return approverFlag;
    }

    public void setApproverFlag(Integer approverFlag) {
        this.approverFlag = approverFlag;
    }

    @Column(name = "car_flag")
    public Integer getShowCarNoFlag() {
        return showCarNoFlag;
    }

    public void setShowCarNoFlag(Integer showCarNoFlag) {
        this.showCarNoFlag = showCarNoFlag;
    }

    @Override
    public String toString() {
        return "TenantEntity{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", status=" + status +
                ", modifyOutTimeFlag=" + modifyOutTimeFlag +
                ", approverFlag=" + approverFlag +
                ", showCarNoFlag=" + showCarNoFlag +
                "} " + super.toString();
    }
}
