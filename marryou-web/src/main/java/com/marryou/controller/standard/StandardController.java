package com.marryou.controller.standard;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.marryou.commons.utils.base.BUtils;
import com.marryou.metadata.dto.StandardDto;
import com.marryou.metadata.entity.CompanyEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.service.StandardService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import com.marryou.utils.RoleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.entity.StandardEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.service.ProductService;
import com.marryou.metadata.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * Created by linhy on 2018/6/6.
 */
@Controller
@RequestMapping("/api/standard")
public class StandardController {

	private static final Logger logger = LoggerFactory.getLogger(StandardController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private StandardService standardService;

	@ApiOperation(value = "创建产品指标", notes = "提交产品指标参数创建信息")
	@ApiImplicitParam(name = "standard", value = "指标参数信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody StandardDto standard, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
            UserEntity operator = userService.getUserByLoginName(loginName);
            Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(standard, "standard为null");
			Preconditions.checkState(StringUtils.isNotBlank(standard.getName()), "standard为null");
			Preconditions.checkNotNull(standard.getProductId(),"productId为null");
			ProductEntity product = productService.findOne(standard.getProductId());
			Preconditions.checkNotNull(product,"查无对应产品");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),product.getTenantCode()),"非本租户下的产品，无权操作");
			}
			StandardEntity s = new StandardEntity();
			BUtils.copyPropertiesIgnoreNull(standard, s);
			s.setTenantCode(operator.getTenantCode());
			s.setProduct(product);
            s.setCreateBy(loginName);
            s.setCreateTime(new Date());
			standardService.saveStandard(s,"新增标准数据", OperateTypeEnum.CREATE,loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建指标参数");
		} catch (Exception e) {
			logger.info("创建指标参数失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "更新指标", notes = "更新指标信息")
	@ApiImplicitParam(name = "standard", value = "指标信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody StandardDto standard, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
            UserEntity operator = userService.getUserByLoginName(loginName);
            Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(standard, "standard为null");
			Preconditions.checkNotNull(standard.getId(), "standardId为null");
			StandardEntity s = standardService.findOne(standard.getId());
			Preconditions.checkNotNull(s,"查无产品指标数据");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),s.getTenantCode()),"非本租户下的产品指标，无权操作");
			}
			BUtils.copyPropertiesIgnoreNull(standard, s);
            s.setModifyBy(loginName);
            s.setModifyTime(new Date());
			standardService.saveStandard(s,"更新标准数据id:"+s.getId(), OperateTypeEnum.UPDATE,loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新指标参数失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}



	@ApiOperation(value = "删除指标参数", notes = "删除指标参数")
	@PostMapping("/delete/{id}")
	public @ResponseBody BaseResponse delete(@PathVariable("id") Long id, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(id, "id参数异常");
			StandardEntity s = standardService.findOne(id);
			Preconditions.checkNotNull(s,"查无对应的标准值数据");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),s.getTenantCode()),"非本租户下的产品指标，无权操作");
			}
			standardService.deleteStandard(s,"删除标准数据id:"+s.getId(), OperateTypeEnum.DELETE,loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("删除指标参数失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

}
