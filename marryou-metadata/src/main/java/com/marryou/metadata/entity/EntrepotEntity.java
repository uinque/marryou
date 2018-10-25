package com.marryou.metadata.entity;

import com.marryou.metadata.entity.base.BaseEntity;
import com.marryou.metadata.enums.StatusEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 仓库实体
 * Created by xph on 2018/10/23.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "entrepot")
public class EntrepotEntity extends BaseEntity {

    private static final long serialVersionUID = 6475950047481509331L;

    private String name;

    private String remark;

    private StatusEnum status;

    public EntrepotEntity() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "EntrepotEntity{" +
                "name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}
