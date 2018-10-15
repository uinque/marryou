package com.marryou.metadata.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marryou.commons.utils.json.JsonDateSerializer;
import com.marryou.metadata.enums.LevelEnum;
import com.marryou.metadata.enums.TechnoEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.marryou.metadata.entity.base.BaseEntity;
import com.marryou.metadata.enums.StatusEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/2.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "delivery_order")
public class DeliveryOrderEntity extends BaseEntity {

    private static final long serialVersionUID = -6897154429607587457L;

    /**
     * 出库单号
     */
    private String deliveryNo;
    /**
     * 生产时间
     */
    private Date deliveryTime;
    /**
     * 供应商Id
     */
    private Long supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 分销商Id
     */
    private Long distributorId;
    /**
     * 分销商名称
     */
    private String distributorName;
    /**
     * 客户名称
     */
    private String customer;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 产品名称
     */
    private String productName;
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
     * 级别 0=一级、1=二级、2=三级
     */
    private LevelEnum level;
    /**
     * 工艺 0=分选
     */
    private TechnoEnum techno;
    /**
     * 二维码图片url
     */
    private String qrcodeUrl;
    /**
     * 公司抬头
     */
    private String title;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 标准值
     */
    private List<DeliveryStandardEntity> standards;

    public DeliveryOrderEntity() {
    }

    @Column(name = "delivery_no")
    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    @Column(name = "delivery_time")
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Column(name = "supplier_id")
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Column(name = "distributor_id")
    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    @Column(name = "distributor_name")
    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    @Column(name = "customer")
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    @Column(name = "level")
    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    @Column(name = "techno")
    public TechnoEnum getTechno() {
        return techno;
    }

    public void setTechno(TechnoEnum techno) {
        this.techno = techno;
    }

    @Column(name="qrcode_url")
    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    @Column(name="title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY,mappedBy = "deliveryOrder")
    public List<DeliveryStandardEntity> getStandards() {
        return standards;
    }

    public void setStandards(List<DeliveryStandardEntity> standards) {
        this.standards = standards;
    }

    @Override
    public String toString() {
        return "DeliveryOrderEntity{" +
                "deliveryNo='" + deliveryNo + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", distributorId=" + distributorId +
                ", distributorName='" + distributorName + '\'' +
                ", customer='" + customer + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", tareWeight=" + tareWeight +
                ", grossWeight=" + grossWeight +
                ", netWeight=" + netWeight +
                ", checker='" + checker + '\'' +
                ", auditor='" + auditor + '\'' +
                ", carNo='" + carNo + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", level=" + level +
                ", techno=" + techno +
                ", qrcodeUrl='" + qrcodeUrl + '\'' +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", standards=" + standards +
                "} " + super.toString();
    }
}
