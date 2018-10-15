package com.marryou.metadata.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by linhy on 2018/7/15.
 */
public class DeliveryCountDto implements Serializable{

    private static final long serialVersionUID = 4739269455352907568L;

    /**
     * 皮重之和
     */
    private BigDecimal sumTareWeight;
    /**
     * 毛重之和
     */
    private BigDecimal sumGrossWeight;
    /**
     * 净重之和
     */
    private BigDecimal sumNetweight;
    /**
     * 发车数（出库单数量）
     */
    private Long totalRecords;

    public DeliveryCountDto() {
    }

    public DeliveryCountDto(BigDecimal sumTareWeight, BigDecimal sumGrossWeight, BigDecimal sumNetweight, Long totalRecords) {
        this.sumTareWeight = sumTareWeight;
        this.sumGrossWeight = sumGrossWeight;
        this.sumNetweight = sumNetweight;
        this.totalRecords = totalRecords;
    }

    public BigDecimal getSumNetweight() {
        return sumNetweight;
    }

    public void setSumNetweight(BigDecimal sumNetweight) {
        this.sumNetweight = sumNetweight;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public BigDecimal getSumTareWeight() {
        return sumTareWeight;
    }

    public void setSumTareWeight(BigDecimal sumTareWeight) {
        this.sumTareWeight = sumTareWeight;
    }

    public BigDecimal getSumGrossWeight() {
        return sumGrossWeight;
    }

    public void setSumGrossWeight(BigDecimal sumGrossWeight) {
        this.sumGrossWeight = sumGrossWeight;
    }

    @Override
    public String toString() {
        return "DeliveryCountDto{" +
                "sumTareWeight=" + sumTareWeight +
                ", sumGrossWeight=" + sumGrossWeight +
                ", sumNetweight=" + sumNetweight +
                ", totalRecords=" + totalRecords +
                '}';
    }
}
