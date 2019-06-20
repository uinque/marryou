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
@Table(name = "standard_title")
public class StandardTitleEntity extends BaseEntity {

    private static final long serialVersionUID = -1715721930617326441L;

    public static final Integer ROW = 0;

    public static final Integer COLUMN = 1;

    private String name;

    private Integer type;

    private Long productId;

    private Integer orderSort;


    public StandardTitleEntity() {
    }

    public StandardTitleEntity(String name, Integer type, Long productId,
                               Integer orderSort) {
        this.name = name;
        this.type = type;
        this.productId = productId;
        this.orderSort = orderSort;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Column(name = "order_sort")
    public Integer getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(Integer orderSort) {
        this.orderSort = orderSort;
    }

    @Override
    public String toString() {
        return "StandardTitleEntity{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", productId=" + productId +
                ", orderSort=" + orderSort +
                "} " + super.toString();
    }
}
