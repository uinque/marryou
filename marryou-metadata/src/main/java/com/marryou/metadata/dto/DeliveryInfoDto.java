package com.marryou.metadata.dto;

import com.marryou.metadata.entity.TenantEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/26.
 */
public class DeliveryInfoDto{

    private Long id;
    /**
     * 出库单号
     */
    private String deliveryNo;
    /**
     * 生产日期
     */
    private Date deliveryTime;
    /**
     * 出厂时间
     */
    private Date outTime;
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
    private Long entrepotName;
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
    private Date loadingTime;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 级别 0=一级、1=二级、2=三级
     */
    private Integer level;
    /**
     * 工艺 0=分选
     */
    private Integer techno;
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
    private Integer status;

    private String createBy;

    private Date createTime;

    private Date modifyTime;

    private String modifyBy;

    private String tenantCode;
    /**
     * 0=不允许，1=允许
     */
    private Integer allowModifyOutTime = TenantEntity.OUTTIME_FLAG_NO;

    /**
     * 标准值
     */
    private List<StandardParamsDto> standards;

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

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Integer getAllowModifyOutTime() {
        return allowModifyOutTime;
    }

    public void setAllowModifyOutTime(Integer allowModifyOutTime) {
        this.allowModifyOutTime = allowModifyOutTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public List<StandardParamsDto> getStandards() {
        return standards;
    }

    public void setStandards(List<StandardParamsDto> standards) {
        this.standards = standards;
    }
}
