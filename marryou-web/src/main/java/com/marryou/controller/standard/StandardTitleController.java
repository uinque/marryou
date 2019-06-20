package com.marryou.controller.standard;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.marryou.metadata.dto.StandardTitleDto;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.StandardTitleService;
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
import com.marryou.commons.utils.base.BUtils;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.service.ProductService;
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
@RequestMapping("/api/standardTitle")
public class StandardTitleController {

	private static final Logger logger = LoggerFactory.getLogger(StandardTitleController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private StandardTitleService standardTitleService;
	@Autowired
	private DeliveryService deliveryService;

	@ApiOperation(value = "创建产品指标标题", notes = "提交产品指标标题参数创建信息")
	@ApiImplicitParam(name = "standardTitle", value = "指标标题参数信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody StandardTitleDto standardTitle, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(standardTitle, "standardTitle为null");
			Preconditions.checkNotNull(standardTitle.getProductId(), "productId为null");
			ProductEntity product = productService.findOne(standardTitle.getProductId());
			Preconditions.checkNotNull(product, "查无对应产品");
			if (!RoleUtils.isPlatformAdmin(operator.getTenantCode())) {
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(), product.getTenantCode()),
						"非本租户下的产品，无权操作");
			}
			StandardTitleEntity title = new StandardTitleEntity();
			title.setName(standardTitle.getName());
			title.setProductId(standardTitle.getProductId());
			title.setTenantCode(operator.getTenantCode());
			title.setType(standardTitle.getType());
			title.setOrderSort(standardTitle.getOrderSort());
			title.setCreateBy(loginName);
			title.setCreateTime(new Date());
			StandardTitleEntity result = standardTitleService.saveStandardTitle(title, "新增模板标题", OperateTypeEnum.CREATE,
					loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建模板指标标题", result);
		} catch (Exception e) {
			logger.info("创建模板指标标题失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "更新模板指标标题", notes = "更新模板指标标题")
	@ApiImplicitParam(name = "standard", value = "更新模板指标标题", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody StandardTitleDto standardTitleDto,
			HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(standardTitleDto, "standardTitleDto为null");
			Preconditions.checkNotNull(standardTitleDto.getId(), "standardTitleId为null");
			StandardTitleEntity title = standardTitleService.findOne(standardTitleDto.getId());
			Preconditions.checkNotNull(title, "查无产品模板指标标题");
			if (!RoleUtils.isPlatformAdmin(operator.getTenantCode())) {
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(), title.getTenantCode()),
						"非本租户下的产品模板指标标题，无权操作");
			}
			BUtils.copyPropertiesIgnoreNull(standardTitleDto, title);
			title.setModifyBy(loginName);
			title.setModifyTime(new Date());
			standardTitleService.saveStandardTitle(title, "更新模板标准标题数据id:" + title.getId(), OperateTypeEnum.UPDATE,
					loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新模板指标标题失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "删除模板指标标题参数", notes = "删除模板指标标题参数")
	@PostMapping("/delete/{id}")
	public @ResponseBody BaseResponse delete(@PathVariable("id") Long id, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(id, "id参数异常");
			StandardTitleEntity title = standardTitleService.findOne(id);
			Preconditions.checkNotNull(title, "查无对应的标准值数据");
			if (!RoleUtils.isPlatformAdmin(operator.getTenantCode())) {
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(), title.getTenantCode()),
						"非本租户下的产品模板指标，无权操作");
			}
			//判断是否有被使用，若被使用则无法删除
			int count = deliveryService.countByProductId(title.getProductId());
			Preconditions.checkState(count == 0, "该产品模板已被使用，产生历史数据，无法执行删除");

			standardTitleService.deleteStandardTitle(title, "删除模板标准标题数据id:" + title.getId(), OperateTypeEnum.DELETE,
					loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("删除模板指标标题失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "模板指标标题参数排序", notes = "模板指标标题参数排序")
	@PostMapping("/sort/{sourceId}/{targetId}")
	public @ResponseBody BaseResponse replaceOrderSort(@PathVariable("sourceId") Long sourceId,
			@PathVariable("targetId") Long targetId, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(sourceId, "sourceId参数异常");
			Preconditions.checkNotNull(targetId, "targetId参数异常");
			StandardTitleEntity sourceTitle = standardTitleService.findOne(sourceId);
			Preconditions.checkNotNull(sourceTitle, "sourceId查无对应的模板标准标题数据");
			StandardTitleEntity targetTitle = standardTitleService.findOne(targetId);
			Preconditions.checkNotNull(targetTitle, "targetId查无对应的模板标准标题数据");
			Preconditions.checkState(sourceTitle.getProductId().equals(targetTitle.getProductId()), "标题选项所属产品不一致");
			if (!RoleUtils.isPlatformAdmin(operator.getTenantCode())) {
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(), sourceTitle.getTenantCode()),
						"非本租户下的产品模板指标标题，无权操作");
			}
			standardTitleService.updateStandardTitleByReplaceOrderSort(sourceTitle, targetTitle, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("删除模板指标标题失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "模板指标标题列表", notes = "模板指标标题列表")
	@PostMapping("/list/{productId}/{type}")
	public @ResponseBody BaseResponse<List<StandardTitleEntity>> list(@PathVariable("productId") Long productId,
			@PathVariable("type") Integer type, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkNotNull(productId, "productId参数异常");
			Preconditions.checkNotNull(type, "type参数异常");
			List<StandardTitleEntity> titles = standardTitleService.findByProductIdAndType(productId, type);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", titles);
		} catch (Exception e) {
			logger.info("获取模板指标标题列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

}
