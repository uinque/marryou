package com.marryou.controller;

import com.google.common.base.Preconditions;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.dto.UserSearchDto;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.service.RedisService;
import com.marryou.metadata.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by linhy on 2018/6/3.
 */
@Controller
@RequestMapping("/api")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Value("${campany}")
	private String campany;

	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;

	@ApiOperation(value = "登录页面", notes = "登录跳转页面")
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@ApiOperation(value = "登录验证", notes = "提交登录信息验证")
	@ApiImplicitParams({ @ApiImplicitParam(name = "loginName", value = "用户账号", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String") })
	@PostMapping("/loginSubmit")
	public @ResponseBody BaseResponse<UserSearchDto> loginPost(@RequestBody UserSearchDto userDto) {
		try {
			Preconditions.checkNotNull(userDto, "参数为null");
			//logger.info("---loginName:{},发起登录验证请求---", userDto.getLoginName());
			Preconditions.checkState(StringUtils.isNotBlank(userDto.getLoginName()), "账号为null");
			Preconditions.checkState(StringUtils.isNotBlank(userDto.getPassword()), "密码为null");
			UserEntity user = userService.getUserByLoginName(userDto.getLoginName());
			Preconditions.checkNotNull(user, "根据账号与密码查无对应用户或已被禁用");
			Preconditions.checkState(StringUtils.equals(userDto.getPassword(), user.getPassword()), "用户名与密码不匹配");
			//把token返回给客户端-->客户端保存至cookie-->客户端每次请求附带cookie参数
			String jwt = JwtUtils.createJWT(user.getId().toString(), user.getLoginName(), Constants.JWT_TTL);
			if(redisService.set(campany + "_" + user.getLoginName(), jwt, Constants.REDIS_EXPIRE)){
				UserSearchDto u = new UserSearchDto();
				BeanUtils.copyProperties(user, u,"password");
				u.setRole(user.getRole().getValue());
				u.setToken(jwt);
				return new BaseResponse<UserSearchDto>(BaseResponse.CODE_SUCCESS, "success", u);
			}
			return new BaseResponse<UserSearchDto>(BaseResponse.CODE_FAILED, "token存入缓存失败");
		} catch (Exception e) {
			return new BaseResponse<UserSearchDto>(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "注销用户", notes = "用户退出系统")
	@GetMapping("/logout")
	public @ResponseBody BaseResponse logout(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(Constants.TOKEN_FIELD);
		try {
			Claims claims = JwtUtils.parseJWT(token);
			//logger.info("---当前用户:{},注销登录---", claims.getSubject());
			//删除缓存中的token
			redisService.remove(campany + "_" + claims.getSubject());
			return new BaseResponse<UserSearchDto>(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("注销失败",e);
			return new BaseResponse<UserSearchDto>(BaseResponse.CODE_FAILED, e.getMessage());
		}

	}
}
