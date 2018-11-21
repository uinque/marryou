package com.marryou.conf;

import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.dto.CheckResult;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.service.RedisService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by linhy on 2018/6/3.
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
				.allowCredentials(false).maxAge(3600);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

		// 排除配置
		addInterceptor.excludePathPatterns("/error");
		//二维码图片请求路径
		addInterceptor.excludePathPatterns("/images/**");
		//二维码扫描访问出库单详情路径
		addInterceptor.excludePathPatterns("/api/qrcode/**");
		//导出出库单路径
		addInterceptor.excludePathPatterns("/api/file/delivery/**");
		addInterceptor.excludePathPatterns("/api/loginSubmit/**");
		//addInterceptor.excludePathPatterns("/api/login/**");
		addInterceptor.excludePathPatterns("/api/swagger**");

		// 拦截配置
		addInterceptor.addPathPatterns("/**");
	}

	/**
	* 发现如果继承了WebMvcConfigurerAdapter，则在yml中配置的相关内容会失效。
	* 需要重新指定静态资源
	* @param registry
	*/
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		//registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		//registry.addResourceHandler("/images/**").addResourceLocations("file:///D:/images/");
		registry.addResourceHandler("/images/**").addResourceLocations("file:/images/");
		super.addResourceHandlers(registry);
	}

	private class SecurityInterceptor extends HandlerInterceptorAdapter {


		@Value("${campany}")
		private String campany;

		@Autowired
		private RedisService redisService;

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {

			/*//设置允许跨域的配置
			// 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
			response.setHeader("Access-Control-Allow-Origin", "*");
			// 允许的访问方法
			response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
			// Access-Control-Max-Age 用于 CORS 相关配置的缓存
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Contrdol-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");*/
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");

			BaseResponse resp = new BaseResponse(Constants.FAILED, "fail");
			boolean isFilter = false;
			String token = request.getHeader(Constants.TOKEN_FIELD);
			if (StringUtils.isNotBlank(token)) {
				CheckResult checkResult = JwtUtils.validateJWT(token);
				if (checkResult.isSuccess()) {
					String cacheKey = campany + "_" + checkResult.getClaims().getSubject();
					//缓存中查询是否有存在token
					String cacheVal = (String) redisService.get(cacheKey);
					if (StringUtils.isNotBlank(cacheVal)) {
						//缓存数据刷新失效时间
						if(redisService.set(cacheKey, token, Constants.REDIS_EXPIRE)){
							//logger.info("###用户token:{},授权认证通过!###", token);
							resp.setCode(Constants.SUCCESS);
							isFilter = true;
						}
					} else {
						logger.info("###用户token:{},但redis中无token数据授权验证不通过!###", token);
						resp.setCode(Constants.JWT_ERRCODE_NULL);
						resp.setMsg("token缓存验证失败");
					}
				} else {
					switch (checkResult.getErrCode()) {
					// 签名验证不通过
					case Constants.JWT_ERRCODE_FAIL:
						logger.info("###用户token:{},签名验证不通过###", token);
						resp.setCode(checkResult.getErrCode());
						resp.setMsg("登录超时【token验证失败】");
						break;
					// 签名过期，返回过期提示码
					case Constants.JWT_ERRCODE_EXPIRE:
						logger.info("###用户token:{},签名验证过期###", token);
						resp.setCode(checkResult.getErrCode());
						resp.setMsg("登录超时【token过期】");
						break;
					default:
						break;
					}
				}
			} else {
				//缓存中查询是否有存在token
				resp.setCode(Constants.JWT_ERRCODE_NULL);
				resp.setMsg("用户授权认证没有通过!客户端请求参数中无token信息");
			}
			if (!resp.isSuccess()) {
				PrintWriter writer = null;
				OutputStreamWriter osw = null;
				try {
					osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
					writer = new PrintWriter(osw, true);
					String jsonStr = GsonUtils.buildGson().toJson(resp);
					writer.write(jsonStr);
					writer.flush();
					writer.close();
					osw.close();
				} catch (UnsupportedEncodingException e) {
					logger.error("过滤器返回信息失败:" + e.getMessage(), e);
				} catch (IOException e) {
					logger.error("过滤器返回信息失败:" + e.getMessage(), e);
				} finally {
					if (null != writer) {
						writer.close();
					}
					if (null != osw) {
						osw.close();
					}
				}
			}
			return isFilter;
		}
	}
}
