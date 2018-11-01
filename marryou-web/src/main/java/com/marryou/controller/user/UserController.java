package com.marryou.controller.user;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.base.BUtils;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.commons.utils.time.DateUtils;
import com.marryou.dto.request.BasePageRequest;
import com.marryou.dto.request.VaildateRequest;
import com.marryou.dto.response.BaseResponse;
import com.marryou.dto.response.PageResponse;
import com.marryou.metadata.dto.UserDto;
import com.marryou.metadata.dto.UserSearchDto;
import com.marryou.metadata.entity.CompanyEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.service.CompanyService;
import com.marryou.metadata.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import com.marryou.utils.RoleUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2017/4/22
 */
@Controller
@RequestMapping("/api/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}

	@ApiOperation(value = "登录名验证", notes = "新增用户名验证")
	@ApiImplicitParam(name = "vaildate", value = "验证参数", required = true, dataType = "Object")
	@PostMapping("/vaildate")
	public @ResponseBody BaseResponse vaild(@RequestBody VaildateRequest vaildate, HttpServletRequest request) {
		try {
			Preconditions.checkNotNull(vaildate, "验证参数异常");
			Preconditions.checkState(StringUtils.isNotBlank(vaildate.getLoginName()), "登录名参数为null");
			UserEntity user = userService.getUserByLoginName(vaildate.getLoginName());
			if (null != user) {
				return new BaseResponse(BaseResponse.CODE_FAILED, "该登录名已被使用");
			} else {
				return new BaseResponse(BaseResponse.CODE_SUCCESS, "该登录名尚未被使用");
			}
		} catch (Exception e) {
			logger.info("登录名验证异常:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "新增用户登录名异常:" + e.getMessage());
		}
	}

	@ApiOperation(value = "注册用户", notes = "提交注册用户数据")
	@ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody UserSearchDto user, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkNotNull(user, "user为null");
			Preconditions.checkNotNull(user.getRole(), "role角色为null");
			if (!operator.getRole().equals(RoleEnum.SUPER_ADMIN)) {
				Preconditions.checkState(operator.getRole().getValue() < user.getRole(), "无权限创建该角色用户");
			}
			Preconditions.checkNotNull(user.getCompanyId(), "companyId为null");
			Preconditions.checkState(StringUtils.isNotBlank(user.getLoginName()), "loginName为null");
			UserEntity u = new UserEntity();
			BUtils.copyPropertiesIgnoreNull(user, u);
			if (null != user.getBirth()) {
				u.setBirth(DateUtils.convertToDate(user.getBirth()));
			}
			u.setStatus(StatusEnum.getEnum(user.getStatus()));
			u.setRole(RoleEnum.getEnum(user.getRole()));
			u.setTenantCode(operator.getTenantCode());
			u.setCreateBy(loginName);
			u.setCreateTime(new Date());
			userService.saveUser(u, GsonUtils.buildGson().toJson(u), OperateTypeEnum.CREATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "注册成功");
		} catch (Exception e) {
			logger.info("创建用户失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "注册用户失败:" + e.getMessage());
		}
	}

	@ApiOperation(value = "禁/启用户", notes = "禁用、启用使用用户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "status", value = "状态0=启用，1=禁用", required = true, dataType = "Integer") })
	@PostMapping("/status/{id}/{status}")
	public @ResponseBody BaseResponse statusChange(@PathVariable("id") Long id, @PathVariable("status") Integer status,
			HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkNotNull(id, "id为null");
			Preconditions.checkNotNull(status, "status为null");
			UserEntity user = userService.findOne(id);
			Preconditions.checkNotNull(user, "查无该用户");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),user.getTenantCode()),"非本租户下的用户无权操作");
			}
			if (!operator.getRole().equals(RoleEnum.SUPER_ADMIN)) {
				Preconditions.checkState(operator.getRole().getValue() < user.getRole().getValue(), "无权限禁用该用户");
			}
			user.setStatus(StatusEnum.getEnum(status));
			user.setModifyBy(loginName);
			user.setModifyTime(new Date());
			userService.saveUser(user, "更新用户，状态:" + user.getStatus().getText(), OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "禁用成功");
		} catch (Exception e) {
			logger.info("禁用用户失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "禁用用户失败");
		}
	}

	@ApiOperation(value = "用户数据", notes = "获取用户数据")
	@PostMapping("/info")
	public @ResponseBody BaseResponse<UserDto> getSelf(HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity user = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(user, "查无对应用户数据");
			UserDto u = new UserDto();
			BUtils.copyPropertiesIgnoreNull(user, u, "password");
			u.setRole(user.getRole().getValue());
			u.setStatus(user.getStatus().getValue());
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", u);
		} catch (Exception e) {
			logger.info("获取当前用户失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取当前用户个人资料失败");
		}
	}

	@ApiOperation(value = "用户数据", notes = "获取用户数据")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
	@PostMapping("/info/{id}")
	public @ResponseBody BaseResponse<UserDto> getUser(@PathVariable("id") Long id, HttpSession session) {
		try {
			Preconditions.checkNotNull(id, "id参数异常");
			UserEntity user = userService.findOne(id);
			Preconditions.checkNotNull(user, "查无对应用户数据");
			UserDto u = new UserDto();
			BUtils.copyPropertiesIgnoreNull(user, u);
			u.setRole(user.getRole().getValue());
			u.setStatus(user.getStatus().getValue());
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", u);
		} catch (Exception e) {
			logger.info("获取用户失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取用户失败");
		}
	}

	@ApiOperation(value = "用户列表", notes = "获取用户列表数据")
	@ApiImplicitParam(name = "search", value = "查询用户信息", required = false, dataType = "Object")
	@PostMapping("/list")
	public @ResponseBody BaseResponse<PageResponse<UserDto>> list(@RequestBody BasePageRequest<UserSearchDto> search,
			HttpServletRequest request) {
		try {
			Preconditions.checkNotNull(search, "查询参数异常");
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				if(null==search.getParams()){
					search.setParams(new UserSearchDto());
				}
				search.getParams().setTenantCode(operator.getTenantCode());
			}
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "无权限查看用户信息");
			Page<UserEntity> page = userService.findUsers(search.toPageRequest(), search.getParams());
			List<UserDto> users = Lists.newArrayList();
			if (Collections3.isNotEmpty(page.getContent())) {
				users = page.getContent().stream().map(user -> {
					UserDto u = new UserDto();
					BUtils.copyPropertiesIgnoreNull(user, u);
					u.setRole(user.getRole().getValue());
					u.setStatus(user.getStatus().getValue());
					CompanyEntity company = companyService.findOne(u.getCompanyId());
					if (null != company) {
						u.setCompanyName(company.getName());
					}
					return u;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", new PageResponse(page.getTotalElements(),
					page.getTotalPages(), page.getNumber(), page.getSize(), users));
		} catch (Exception e) {
			logger.info("获取用户列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取用户列表失败");
		}
	}

	@ApiOperation(value = "更新用户", notes = "用户数据更新")
	@ApiImplicitParam(name = "user", value = "更新信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody UserSearchDto user, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkNotNull(user, "user为null");
			Preconditions.checkNotNull(user.getId(), "userId为null");
			UserEntity u = userService.findOne(user.getId());
			Preconditions.checkNotNull(u, "查无对应user数据");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),u.getTenantCode()),"非本租户下的用户无权操作");
			}
			if (!operator.getRole().equals(RoleEnum.SUPER_ADMIN)) {
				Preconditions.checkState(u.getRole().getValue() > operator.getRole().getValue(), "无权限跟新该角色用户");
			}
			BUtils.copyPropertiesIgnoreNull(user, u);
			if (StringUtils.isNotBlank(user.getBirth())) {
				u.setBirth(DateUtils.convertToDate(user.getBirth()));
			}
			if (null != user.getRole() && u.getRole().getValue() != user.getRole()) {
				logger.warn("###用户:{},更新了角色:{},操作人:{}###", u.getLoginName(), user.getRole(), operator.getLoginName());
				u.setRole(RoleEnum.getEnum(user.getRole()));
			}
			if (null != user.getStatus()) {
				u.setStatus(StatusEnum.getEnum(user.getStatus()));
			}
			String log = GsonUtils.buildGson().toJson(u);
			if (StringUtils.isNotBlank(user.getPassword())) {
				logger.warn("###用户:{},更新了密码,操作人:{}###", u.getLoginName(), operator.getLoginName());
				u.setPassword(user.getPassword());
				log += "【注:更新了密码】";
			}
			u.setModifyBy(loginName);
			u.setModifyTime(new Date());
			userService.saveUser(u,log, OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新用户失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "更新用户失败");
		}
	}

}
