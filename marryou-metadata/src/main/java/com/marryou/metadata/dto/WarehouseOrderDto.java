package com.marryou.metadata.dto;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by linhy on 2018/12/2.
 */
public class WarehouseOrderDto implements Serializable{

    private static final long serialVersionUID = 4276894114796780332L;


    private Long id;
    /**
     * 入库单号
     */
    private String warehouseNo;
    /**
     * 打灰开始时间
     */
    private String checkStartTime;
    /**
     * 打灰结束时间
     */
    private String checkOutTime;
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
    private Integer status;

    private String tenantCode;

    private String startTime;

    private String endTime;

    private String createTime;

    private String createBy;

    private String modifyTime;

    private String modifyBy;

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public String getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(String checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFineness() {
        return fineness;
    }

    public void setFineness(String fineness) {
        this.fineness = fineness;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    @Override
    public String toString() {
        return "WarehouseOrderDto{" +
                "id=" + id +
                ", warehouseNo='" + warehouseNo + '\'' +
                ", checkStartTime='" + checkStartTime + '\'' +
                ", checkOutTime='" + checkOutTime + '\'' +
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
                ", tenantCode='" + tenantCode + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", modifyBy='" + modifyBy + '\'' +
                '}';
    }
}
