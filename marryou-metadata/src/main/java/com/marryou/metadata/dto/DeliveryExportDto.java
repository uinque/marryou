package com.marryou.metadata.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/3.
 */
public class DeliveryExportDto implements Serializable {

	private static final long serialVersionUID = -3227508582355762660L;
	/**
	 * 出库单号
	 */
	@Excel(name = "出厂编号", orderNum = "0")
	private String deliveryNo;
	/**
	 * 生产日期
	 */
	@Excel(name = "生产日期", exportFormat = "yyyy-MM-dd", orderNum = "1")
	private Date deliveryTime;
	/**
	 * 供应商名称
	 */
	@Excel(name = "生厂商名称", orderNum = "2")
	private String supplierName;
	/**
	 * 公司名称
	 */
	@Excel(name = "公司名称", orderNum = "3")
	private String distributorName;

	@Excel(name = "客户名称", orderNum = "4")
	private String customer;

	/**
	 * 产品名称
	 */
	@Excel(name = "品名", orderNum = "5")
	private String productName;
	/**
	 * 皮重
	 */
	@Excel(name = "皮重(kg)", orderNum = "6")
	private BigDecimal tareWeight;
	/**
	 * 毛重
	 */
	@Excel(name = "毛重(kg)", orderNum = "7")
	private BigDecimal grossWeight;
	/**
	 * 净重
	 */
	@Excel(name = "净重(kg)", orderNum = "8")
	private BigDecimal netWeight;
	/**
	 * 检验员
	 */
	@Excel(name = "检验员", orderNum = "9")
	private String checker;
	/**
	 * 审核员
	 */
	@Excel(name = "审核员", orderNum = "10")
	private String auditor;
	/**
	 * 运输车牌
	 */
	@Excel(name = "运输车牌", orderNum = "11")
	private String carNo;
	/**
	 * 批次号
	 */
	@Excel(name = "批次号", orderNum = "12")
	private String batchNo;

	@Excel(name = "出厂时间", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "13")
	private Date createTime;

	/**
	 * 灰源：0=麦特，1=嵩屿，2=后石
	 */
	@Excel(name = "灰源", orderNum = "14")
	private String flyashSource;

	/**
	 * 外部关联编号
	 */
	@Excel(name = "关联编号", orderNum = "15")
	private String relationCode;

	/**
	 * 磅单号
	 */
	@Excel(name = "磅单号", orderNum = "16")
	private String poundCode;

	public DeliveryExportDto() {
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getFlyashSource() {
		return flyashSource;
	}

	public void setFlyashSource(String flyashSource) {
		this.flyashSource = flyashSource;
	}

	public String getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getPoundCode() {
		return poundCode;
	}

	public void setPoundCode(String poundCode) {
		this.poundCode = poundCode;
	}
}
