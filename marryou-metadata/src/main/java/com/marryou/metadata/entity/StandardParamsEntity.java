package com.marryou.metadata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.marryou.metadata.entity.base.BaseEntity;

/**
 * Created by linhy on 2018/6/2.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "standard_params")
public class StandardParamsEntity extends BaseEntity {

    private static final long serialVersionUID = -3632476123166112935L;

    public static final Integer LT = 0;

    public static final Integer GT = 1;

    private Long rowId;

    private Long columnId;

    private Integer type;

    private Long productId;

    private String val;

    private Integer pointNum;


    public StandardParamsEntity() {
    }

    public StandardParamsEntity(Long rowId, Long columnId, Integer type,
                                Long productId, String val, Integer pointNum) {
        this.rowId = rowId;
        this.columnId = columnId;
        this.type = type;
        this.productId = productId;
        this.val = val;
        this.pointNum = pointNum;
    }

    @Column(name = "row_id")
    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    @Column(name = "column_id")
    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Column(name = "val")
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Column(name = "point_num")
    public Integer getPointNum() {
        return pointNum;
    }

    public void setPointNum(Integer pointNum) {
        this.pointNum = pointNum;
    }

    @Override
    public String toString() {
        return "StandardParamsEntity{" +
                "rowId=" + rowId +
                ", columnId=" + columnId +
                ", type=" + type +
                ", productId=" + productId +
                ", val='" + val + '\'' +
                ", pointNum=" + pointNum +
                "} " + super.toString();
    }
}
