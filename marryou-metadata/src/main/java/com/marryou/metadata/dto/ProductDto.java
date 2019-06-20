package com.marryou.metadata.dto;

import com.marryou.metadata.entity.TenantEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/3.
 */
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 1354959855351613003L;
    /**
     * 有数据
     */
    public static final Integer BEEN_DATA = 1;
    /**
     * 无数据
     */
    public static final Integer NONE_DATA = 0;

    private Long id;

    private String name;
    /**
     * 类型：0=粉煤灰，1=铁路运输
     */
    private Integer type;
    /**
     * 打印抬头
     */
    private String printName;
    /**
     * 打印备注
     */
    private String remark;
    /**
     * 状态：0=有效，1=失效
     */
    private Integer status;
    /**
     * 是否有模板标准数据
     */
    private Integer standardDataFlag;

    private String startTime;

    private String endTime;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String tenantCode;

    /**
     * 模板表格表头项目名称
     */
    private String headName;
    /**
     * 模板表格表头标题
     */
    private String headTitle;
    /**
     * 模板表格表头结果
     */
    private String headResult;
    /**
     * 模板表格底部名称
     */
    private String footName;
    /**
     * 模板表格底部内容
     */
    private String footContent;

    /**
     * 0=不允许，1=允许
     */
    private Integer allowModifyOutTime;
    /**
     * 0=不需要，1=需要
     */
    private Integer allowApprover;
    /**
     * 0=不需要，1=需要
     */
    private Integer allowShowCarNo;


    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
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

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Integer getStandardDataFlag() {
        return standardDataFlag;
    }

    public void setStandardDataFlag(Integer standardDataFlag) {
        this.standardDataFlag = standardDataFlag;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public String getHeadResult() {
        return headResult;
    }

    public void setHeadResult(String headResult) {
        this.headResult = headResult;
    }

    public String getFootName() {
        return footName;
    }

    public void setFootName(String footName) {
        this.footName = footName;
    }

    public String getFootContent() {
        return footContent;
    }

    public void setFootContent(String footContent) {
        this.footContent = footContent;
    }

    public Integer getAllowModifyOutTime() {
        return allowModifyOutTime;
    }

    public void setAllowModifyOutTime(Integer allowModifyOutTime) {
        this.allowModifyOutTime = allowModifyOutTime;
    }

    public Integer getAllowApprover() {
        return allowApprover;
    }

    public void setAllowApprover(Integer allowApprover) {
        this.allowApprover = allowApprover;
    }

    public Integer getAllowShowCarNo() {
        return allowShowCarNo;
    }

    public void setAllowShowCarNo(Integer allowShowCarNo) {
        this.allowShowCarNo = allowShowCarNo;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", printName='" + printName + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", standardDataFlag=" + standardDataFlag +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", modifyBy='" + modifyBy + '\'' +
                ", modifyTime=" + modifyTime +
                ", tenantCode='" + tenantCode + '\'' +
                ", headName='" + headName + '\'' +
                ", headTitle='" + headTitle + '\'' +
                ", headResult='" + headResult + '\'' +
                ", footName='" + footName + '\'' +
                ", footContent='" + footContent + '\'' +
                ", allowModifyOutTime=" + allowModifyOutTime +
                ", allowApprover=" + allowApprover +
                ", allowShowCarNo=" + allowShowCarNo +
                '}';
    }
}
