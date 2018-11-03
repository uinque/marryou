package com.marryou.metadata.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/3.
 */
public class DeliveryDto implements Serializable {

    private static final long serialVersionUID = 6137234841737436193L;

    /**
     * id
     */
    private Long id;
    /**
     * 出库单号
     */
    private String deliveryNo;
    /**
     * 生产日期 yyyy-MM-dd HH:mm:ss
     */
    private String deliveryTime;
    /**
     * 出厂日期 yyyy-MM-dd HH:mm:ss
     */
    private String outTime;
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
     * 仓库ID
     */
    private Long entrepotId;
    /**
     * 仓库名称
     */
    private String entrepotName;
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
     * 装车时间
     */
    private String loadingTime;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 级别 0=一级，1=二级，2=三级
     */
    private Integer level;
    /**
     * 工艺 0=分选
     */
    private Integer techno;
    /**
     * 备注
     */
    private String remark;
    /**
     * 二维码路径
     */
    private String qrcodeUrl;
    /**
     * 公司抬头
     */
    private String title;
    /**
     * 状态 0=有效，1=无效
     */
    private Integer status;

    private List<StandardParamsDto> standards;

    /**
     * 出库单出厂日期查询起始时间
     */
    private String startTime;
    /**
     * 出库单出厂日期查询结束时间
     */
    private String endTime;

    private String tenantCode;

    public DeliveryDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public BigDecimal getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(BigDecimal tareWeight) {
        this.tareWeight = tareWeight;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StandardParamsDto> getStandards() {
        return standards;
    }

    public void setStandards(List<StandardParamsDto> standards) {
        this.standards = standards;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getTechno() {
        return techno;
    }

    public void setTechno(Integer techno) {
        this.techno = techno;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Long getEntrepotId() {
        return entrepotId;
    }

    public void setEntrepotId(Long entrepotId) {
        this.entrepotId = entrepotId;
    }

    public String getEntrepotName() {
        return entrepotName;
    }

    public void setEntrepotName(String entrepotName) {
        this.entrepotName = entrepotName;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    @Override
    public String toString() {
        return "DeliveryDto{" +
                "id=" + id +
                ", deliveryNo='" + deliveryNo + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", distributorId=" + distributorId +
                ", distributorName='" + distributorName + '\'' +
                ", customer='" + customer + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", entrepotId=" + entrepotId +
                ", entrepotName=" + entrepotName +
                ", tareWeight=" + tareWeight +
                ", grossWeight=" + grossWeight +
                ", netWeight=" + netWeight +
                ", checker='" + checker + '\'' +
                ", auditor='" + auditor + '\'' +
                ", carNo='" + carNo + '\'' +
                ", loadingTime=" + loadingTime +
                ", batchNo='" + batchNo + '\'' +
                ", level=" + level +
                ", techno=" + techno +
                ", remark='" + remark + '\'' +
                ", qrcodeUrl='" + qrcodeUrl + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", standards=" + standards +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}
