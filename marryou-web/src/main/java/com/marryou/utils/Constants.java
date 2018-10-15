package com.marryou.utils;

import org.hibernate.dialect.Ingres9Dialect;

/**
 * 一些公用属性的定义
 */
public class Constants {
	public final static String PAGE_JSON_TOTAL = "total";
	public final static String PAGE_JSON_DATA = "items";
	public final static String TOTAL_PAGE = "totalPage";
	public final static String CURRENT_PAGE = "currentPage";
	public final static String STATUS_CODE = "statusCode";
	public final static String ERROR_MSG = "errMsg";
	public final static String PAGE_SIZE = "pageSize";

	public final static Integer SUCCESS = 0;

	public final static Integer FAILED = -1;

	public final static Integer ERROR_400 = 400;
	public final static Integer ERROR_401 = 401;
	public final static Integer ERROR_403 = 403;
	public final static Integer ERROR_404 = 404;
	public final static Integer ERROR_500 = 500;

	/**
	 * token
	 */
	public static final String TOKEN_FIELD = "token";
	public static final int RESCODE_REFTOKEN_MSG = 1006;		//刷新TOKEN(有返回数据)
	public static final int RESCODE_REFTOKEN = 1007;			//刷新TOKEN
	public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
	public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
	public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过

	/**
	 * JWT
	 */
	public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			//密匙
	public static final long REDIS_EXPIRE = 60 * 60 * 1000;//1小时									//token有效时间
	public static final long JWT_TTL = 20 * 60 * 60 * 1000;//20小时									//token有效时间


}
