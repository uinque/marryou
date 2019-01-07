package com.marryou.metadata.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marryou.commons.utils.json.JsonDateSerializer;
import com.marryou.metadata.entity.base.BaseEntity;
import com.marryou.metadata.enums.LevelEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.enums.TechnoEnum;

/**
 * Created by linhy on 2018/6/2.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "warehouse_order")
public class WarehouseOrderEntity extends BaseEntity {


    private static final long serialVersionUID = 8989200660180545456L;
    /**
     * 入库单号
     */
    private String warehouseNo;
    /**
     * 打灰开始时间
     */
    private Date checkStartTime;
    /**
     * 打灰结束时间
     */
    private Date checkOutTime;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 细度
     */
    private String fineness;
    /**
     * 皮重
     */
    private BigDecimal tareWeight;
    /**
     * 毛重
     */
    private BigDecimal grossWeight;
    /**
     * 净重
     */
    private BigDecimal netWeight;
    /**
     * 检验员
     */
    private String checker;
    /**
     * 审核员
     */
    private String auditor;
    /**
     * 运输车牌
     */
    private String carNo;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private StatusEnum status;

    public WarehouseOrderEntity() {
    }

    @Column(name = "warehouse_no")
    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    @Column(name = "check_start_time")
    public Date getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(Date checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    @Column(name = "check_out_time")
    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "fineness")
    public String getFineness() {
        return fineness;
    }

    public void setFineness(String fineness) {
        this.fineness = fineness;
    }

    @Column(name = "tare_weight")
    public BigDecimal getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(BigDecimal tareWeight) {
        this.tareWeight = tareWeight;
    }

    @Column(name = "gross_weight")
    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    @Column(name = "net_weight")
    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    @Column(name = "checker")
    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    @Column(name = "auditor")
    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    @Column(name = "car_no")
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    @Column(name = "batch_no")
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
        return "WarehouseOrderEntity{" +
                "warehouseNo='" + warehouseNo + '\'' +
                ", checkStartTime=" + checkStartTime +
                ", checkOutTime=" + checkOutTime +
                ", companyName='" + companyName + '\'' +
                ", productName='" + productName + '\'' +
                ", fineness='" + fineness + '\'' +
                ", tareWeight=" + tareWeight +
                ", grossWeight=" + grossWeight +
                ", netWeight=" + netWeight +
                ", checker='" + checker + '\'' +
                ", auditor='" + auditor + '\'' +
                ", carNo='" + carNo + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}
