package com.marryou.dto.request;

import java.io.Serializable;

/**
 * Created by linhy on 2018/7/7.
 */
public class VaildateRequest implements Serializable{

    private static final long serialVersionUID = 8270609715518937159L;

    private String loginName;

    private String companyName;

    private String entrepotName;

    private Long deliveryId;

    private String tenantCode;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
    public String getEntrepotName() {
        return entrepotName;
    }

    public void setEntrepotName(String entrepotName) {
        this.entrepotName = entrepotName;
    }

    @Override
    public String toString() {
        return "VaildateRequest{" +
                "loginName='" + loginName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", deliveryId=" + deliveryId +
                ", tenantCode='" + tenantCode + '\'' +
                ", entrepotName='" + entrepotName + '\'' +
                '}';
    }
}
