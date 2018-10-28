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

    private String name;

    private String info;

    /**
     * 是否允许修改出厂时间(0=不允许，1=允许)
     */
    private Integer modifyOutTimeFlag;

    public TenantEntity() {
    }

    public TenantEntity(String name, String info, Integer modifyOutTimeFlag) {
        this.name = name;
        this.info = info;
        this.modifyOutTimeFlag = modifyOutTimeFlag;
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

    @Column(name = "time_flag")
    public Integer getModifyOutTimeFlag() {
        return modifyOutTimeFlag;
    }

    public void setModifyOutTimeFlag(Integer modifyOutTimeFlag) {
        this.modifyOutTimeFlag = modifyOutTimeFlag;
    }

    @Override
    public String toString() {
        return "TenantEntity{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", modifyOutTimeFlag=" + modifyOutTimeFlag +
                "} " + super.toString();
    }
}
