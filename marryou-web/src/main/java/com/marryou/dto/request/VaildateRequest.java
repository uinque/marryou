package com.marryou.dto.request;

import java.io.Serializable;

/**
 * Created by linhy on 2018/7/7.
 */
public class VaildateRequest implements Serializable{

    private static final long serialVersionUID = 8270609715518937159L;

    private String loginName;

    private String companyName;

    private Long deliveryId;


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


    @Override
    public String toString() {
        return "VaildateRequest{" +
                "loginName='" + loginName + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
