package com.marryou.controller.tenant;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.marryou.metadata.dto.TenantDto;
import com.marryou.metadata.entity.TenantEntity;
import com.marryou.metadata.service.TenantService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.base.BUtils;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import com.marryou.utils.RoleUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * Created by linhy on 2018/6/6.
 */
@Controller
@RequestMapping("/api/tenant")
public class TenantController {

	private static final Logger logger = LoggerFactory.getLogger(TenantController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private TenantService tenantService;

	@ApiOperation(value = "创建租户信息", notes = "提交租户基础信息")
	@ApiImplicitParam(name = "tenant", value = "租户基础信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody TenantDto tenantDto, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(RoleUtils.isPlatformAdmin(operator.getTenantCode()), "非平台管理员，无权限操作");
			Preconditions.checkNotNull(tenantDto, "租户数据为null");
			Preconditions.checkState(StringUtils.isNotBlank(tenantDto.getName()), "租户名称为null");
			TenantEntity tenant = new TenantEntity();
			BUtils.copyPropertiesIgnoreNull(tenantDto, tenant);
			tenant.setStatus(StatusEnum.getEnum(tenantDto.getStatus()));
			tenant.setCreateBy(loginName);
			tenant.setCreateTime(new Date());
			tenantService.saveTenant(tenant, GsonUtils.buildGson().toJson(tenant), OperateTypeEnum.CREATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建租户成功");
		} catch (Exception e) {
			logger.info("创建租户失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "租户数据", notes = "获取租户数据")
	@ApiImplicitParam(name = "id", value = "租户ID", required = true, dataType = "Long")
	@PostMapping("/info/{id}")
	public @ResponseBody BaseResponse<TenantDto> getTenant(@PathVariable("id") Long id, HttpServletRequest request) {
		try {
			Preconditions.checkNotNull(id, "id参数异常");
			TenantEntity tenant = tenantService.findOne(id);
			Preconditions.checkNotNull(tenant, "查无对应租户数据");
			TenantDto dto = new TenantDto();
			BUtils.copyPropertiesIgnoreNull(tenant, dto);
			dto.setStatus(tenant.getStatus().getValue());
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", dto);
		} catch (Exception e) {
			logger.info("获取租户数据失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取租户数据失败");
		}
	}

	@ApiOperation(value = "租户列表", notes = "获取租户	列表数据")
	@ApiImplicitParam(name = "search", value = "查询租户信息", required = false, dataType = "Object")
	@PostMapping("/list")
	public @ResponseBody BaseResponse<List<TenantDto>> list(HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(RoleUtils.isPlatformAdmin(operator.getTenantCode()), "非平台管理员，无权限操作");
			List<TenantEntity> tenants = (List<TenantEntity>) tenantService.findAll();
			List<TenantDto> list = Lists.newArrayList();
			if (Collections3.isNotEmpty(tenants)) {
				list = tenants.stream().map(m -> {
					TenantDto tenantDto = new TenantDto();
					BUtils.copyPropertiesIgnoreNull(m, tenantDto);
					tenantDto.setStatus(m.getStatus().getValue());
					return tenantDto;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", list);
		} catch (Exception e) {
			logger.info("获取租户列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取租户列表失败");
		}
	}

	@ApiOperation(value = "更新租户信息", notes = "更新租户基础信息")
	@ApiImplicitParam(name = "tenant", value = "租户信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody TenantDto tenantDto, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(RoleUtils.isPlatformAdmin(operator.getTenantCode()), "非平台管理员，无权限操作");
			Preconditions.checkNotNull(tenantDto, "tenant为null");
			Preconditions.checkNotNull(tenantDto.getId(), "租户ID为null");
			TenantEntity tenant = tenantService.findOne(tenantDto.getId());
			Preconditions.checkNotNull(tenant, "查无租户数据");
			BUtils.copyPropertiesIgnoreNull(tenantDto, tenant);
			tenant.setStatus(StatusEnum.getEnum(tenantDto.getStatus()));
			tenant.setModifyBy(loginName);
			tenant.setModifyTime(new Date());
			tenantService.saveTenant(tenant, GsonUtils.buildGson().toJson(tenant), OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新租户数据失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

}
