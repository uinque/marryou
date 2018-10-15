package com.marryou.metadata.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by linhy on 2018/6/10.
 */
public class StandardParamsDto implements Serializable {

    private static final long serialVersionUID = -1588496937385223404L;

    private Long id;

    private String oneLevel;

    private String twoLevel;

    private String threeLevel;

    private Long standardId;

    private String standardName;

    private String parameter;

    private Integer pointNum;

    private Integer type;

    private String createBy;

    private Date createTime;

    private Date modifyTime;

    private String modifyBy;

    public StandardParamsDto() {
    }

    public StandardParamsDto(Long id, Long standardId, String standardName, String parameter,
                             Integer pointNum,Integer type) {
        this.id = id;
        this.standardId = standardId;
        this.standardName = standardName;
        this.parameter = parameter;
        this.pointNum = pointNum;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStandardId() {
        return standardId;
    }

    public void setStandardId(Long standardId) {
        this.standardId = standardId;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
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

    public String getOneLevel() {
        return oneLevel;
    }

    public void setOneLevel(String oneLevel) {
        this.oneLevel = oneLevel;
    }

    public String getTwoLevel() {
        return twoLevel;
    }

    public void setTwoLevel(String twoLevel) {
        this.twoLevel = twoLevel;
    }

    public String getThreeLevel() {
        return threeLevel;
    }

    public void setThreeLevel(String threeLevel) {
        this.threeLevel = threeLevel;
    }

    public Integer getPointNum() {
        return pointNum;
    }

    public void setPointNum(Integer pointNum) {
        this.pointNum = pointNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StandardParamsDto{" +
                "id=" + id +
                ", oneLevel='" + oneLevel + '\'' +
                ", twoLevel='" + twoLevel + '\'' +
                ", threeLevel='" + threeLevel + '\'' +
                ", standardId=" + standardId +
                ", standardName='" + standardName + '\'' +
                ", parameter='" + parameter + '\'' +
                ", pointNum=" + pointNum +
                ", type=" + type +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", modifyBy='" + modifyBy + '\'' +
                '}';
    }
}
