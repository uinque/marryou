package com.marryou.metadata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.marryou.metadata.entity.base.BaseEntity;

import java.math.BigDecimal;

/**
 * Created by linhy on 2018/6/2.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "standard")
public class StandardEntity extends BaseEntity {

    private static final long serialVersionUID = 8241789730653380333L;

    private String name;

    private String oneLevel;

    private String twoLevel;

    private String threeLevel;

    private ProductEntity product;

    private Integer pointNum;

    private Integer type;

    public StandardEntity() {
    }

    public StandardEntity(String name, String oneLevel, String twoLevel,
                          String threeLevel, Integer pointNum,Integer type) {
        this.name = name;
        this.oneLevel = oneLevel;
        this.twoLevel = twoLevel;
        this.threeLevel = threeLevel;
        this.pointNum = pointNum;
        this.type = type;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "one_level")
    public String getOneLevel() {
        return oneLevel;
    }

    public void setOneLevel(String oneLevel) {
        this.oneLevel = oneLevel;
    }

    @Column(name = "two_level")
    public String getTwoLevel() {
        return twoLevel;
    }

    public void setTwoLevel(String twoLevel) {
        this.twoLevel = twoLevel;
    }

    @Column(name = "three_level")
    public String getThreeLevel() {
        return threeLevel;
    }

    public void setThreeLevel(String threeLevel) {
        this.threeLevel = threeLevel;
    }

    @ManyToOne
    @JoinColumn(name="product_id")
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Column(name = "point_num")
    public Integer getPointNum() {
        return pointNum;
    }

    public void setPointNum(Integer pointNum) {
        this.pointNum = pointNum;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StandardEntity{" +
                "name='" + name + '\'' +
                ", oneLevel=" + oneLevel +
                ", twoLevel=" + twoLevel +
                ", threeLevel=" + threeLevel +
                ", product=" + product +
                ", pointNum=" + pointNum +
                ", type=" + type +
                "} " + super.toString();
    }
}
