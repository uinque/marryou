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
@Table(name = "delivery_standard")
public class DeliveryStandardEntity extends BaseEntity {

    private static final long serialVersionUID = 7281395295497659453L;

    private DeliveryOrderEntity deliveryOrder;

    private Long standardId;

    private String standardName;

    private String parameter;

    public DeliveryStandardEntity() {
    }

    public DeliveryStandardEntity(DeliveryOrderEntity deliveryOrder,
                                  Long standardId, String standardName, String parameter) {
        this.deliveryOrder = deliveryOrder;
        this.standardId = standardId;
        this.standardName = standardName;
        this.parameter = parameter;
    }

    @ManyToOne()
    @JoinColumn(name = "delivery_id")
    public DeliveryOrderEntity getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(DeliveryOrderEntity deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    @Column(name = "standard_id")
    public Long getStandardId() {
        return standardId;
    }

    public void setStandardId(Long standardId) {
        this.standardId = standardId;
    }

    @Column(name = "standard_name")
    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    @Column(name = "parameter")
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "DeliveryStandardEntity{" +
                "deliveryOrder=" + deliveryOrder +
                ", standardId=" + standardId +
                ", standardName='" + standardName + '\'' +
                ", parameter=" + parameter +
                "} " + super.toString();
    }
}
