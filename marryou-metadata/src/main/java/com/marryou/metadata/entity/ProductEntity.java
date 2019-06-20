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

    /**
     * 模板表格表头项目名称
     */
    private String headName;
    /**
     * 模板表格表头标题
     */
    private String headTitle;
    /**
     * 模板表格表头结果
     */
    private String headResult;
    /**
     * 模板表格底部名称
     */
    private String footName;
    /**
     * 模板表格底部内容
     */
    private String footContent;

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

    @Column(name = "head_name")
    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    @Column(name = "head_title")
    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    @Column(name = "head_result")
    public String getHeadResult() {
        return headResult;
    }

    public void setHeadResult(String headResult) {
        this.headResult = headResult;
    }

    @Column(name = "foot_name")
    public String getFootName() {
        return footName;
    }

    public void setFootName(String footName) {
        this.footName = footName;
    }

    @Column(name = "foot_content")
    public String getFootContent() {
        return footContent;
    }

    public void setFootContent(String footContent) {
        this.footContent = footContent;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", printName='" + printName + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", headName='" + headName + '\'' +
                ", headTitle='" + headTitle + '\'' +
                ", headResult='" + headResult + '\'' +
                ", footName='" + footName + '\'' +
                ", footContent='" + footContent + '\'' +
                "} " + super.toString();
    }
}
