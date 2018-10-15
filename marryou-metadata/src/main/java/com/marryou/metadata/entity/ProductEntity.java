package com.marryou.metadata.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marryou.metadata.enums.ProductTypeEnum;
import com.marryou.metadata.enums.StatusEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.marryou.metadata.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by linhy on 2018/6/2.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
public class ProductEntity extends BaseEntity {

    private static final long serialVersionUID = -8815845656600830810L;

    private String name;

    private ProductTypeEnum type;

    /**
     * 打印名称
     */
    private String printName;

    /**
     * 备注（打印出库单时的）
     */
    private String remark;

    private StatusEnum status;

    private List<StandardEntity> standards;

    public ProductEntity() {
    }

    public ProductEntity(String name, ProductTypeEnum type,String printName,
                         StatusEnum status,String remark) {
        this.name = name;
        this.type = type;
        this.printName = printName;
        this.status = status;
        this.remark = remark;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "type")
    public ProductTypeEnum getType() {
        return type;
    }

    public void setType(ProductTypeEnum type) {
        this.type = type;
    }

    @Column(name = "print_name")
    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
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

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "product")
    @JsonIgnore
    public List<StandardEntity> getStandards() {
        return standards;
    }

    public void setStandards(List<StandardEntity> standards) {
        this.standards = standards;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", printName='" + printName + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", standards=" + standards +
                "} " + super.toString();
    }
}
