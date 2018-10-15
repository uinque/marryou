package com.marryou.metadata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.marryou.metadata.enums.StatusEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.marryou.metadata.entity.base.BaseEntity;

/**
 * Created by linhy on 2018/6/2.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "manufacturer")
public class ManufacturerEntity extends BaseEntity {

    private static final long serialVersionUID = -8534207810080981923L;

    private String name;

    private String info;

    private StatusEnum status;

    public ManufacturerEntity() {
    }

    public ManufacturerEntity(String name, String info, StatusEnum status) {
        this.name = name;
        this.info = info;
        this.status = status;
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

    @Override
    public String toString() {
        return "ManufacturerEntity{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}
