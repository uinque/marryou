package com.marryou.dto;

import io.jsonwebtoken.Claims;

import java.io.Serializable;

/**
 * Created by linhy on 2018/6/21.
 */
public class CheckResult implements Serializable{

    private static final long serialVersionUID = 3492200008990523955L;

    private int errCode;

    private boolean success;

    private Claims claims;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "errCode=" + errCode +
                ", success=" + success +
                ", claims=" + claims +
                '}';
    }
}
