package com.marryou.utils;

/**
 * Created by linhy on 2018/6/21.
 */

import java.security.SignatureException;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;

import com.marryou.dto.CheckResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 角色判断工具类
 */
public class RoleUtils {

    /**
     * 平台管理员
     */
    public static final String PLATFORM_ADMIN = "platform";

    public static boolean isPlatformAdmin(String tenantCode) {
        if(StringUtils.equals(PLATFORM_ADMIN,tenantCode)){
            return true;
        }
        return false;
    }

}
