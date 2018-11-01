package com.marryou.controller.prduct;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.marryou.commons.utils.base.BUtils;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.dto.ProductDto;
import com.marryou.metadata.dto.StandardDto;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.entity.StandardEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.ProductTypeEnum;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.persistence.SearchFilters;
import com.marryou.metadata.persistence.Searcher;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.ProductService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by linhy on 2018/6/6.
 */
@Controller
@RequestMapping("/api/product")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DeliveryService deliveryService;

	@ApiOperation(value = "创建产品", notes = "提交产品创建信息")
	@ApiImplicitParam(name = "product", value = "产品信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody ProductDto product, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(product, "productDto为null");
			Preconditions.checkState(StringUtils.isNotBlank(product.getName()), "productName为null");
			ProductEntity p = new ProductEntity();
			BUtils.copyPropertiesIgnoreNull(product, p);
			p.setStatus(StatusEnum.getEnum(product.getStatus()));
			p.setType(ProductTypeEnum.getEnum(product.getType()));
			p.setTenantCode(operator.getTenantCode());
			p.setCreateBy(loginName);
			p.setCreateTime(new Date());
			if (Collections3.isNotEmpty(product.getStandards())) {
				List<StandardEntity> standards = product.getStandards().stream().map(s -> {
					StandardEntity standard = new StandardEntity(s.getName(), s.getOneLevel(), s.getTwoLevel(),
							s.getThreeLevel(), s.getPointNum(),s.getType());
					standard.setTenantCode(operator.getTenantCode());
					standard.setCreateBy(p.getCreateBy());
					standard.setCreateTime(p.getCreateTime());
					standard.setProduct(p);
					return standard;
				}).collect(Collectors.toList());
				p.setStandards(standards);
			}
			productService.saveProduct(p, "新增产品:" + p.getName(), OperateTypeEnum.CREATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建成功");
		} catch (Exception e) {
			logger.info("创建产品失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "更新产品", notes = "更新产品信息")
	@ApiImplicitParam(name = "product", value = "产品信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody ProductDto product, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(product, "productDto为null");
			Preconditions.checkNotNull(product.getId(), "productId为null");
			ProductEntity p = productService.findOne(product.getId());
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),p.getTenantCode()),"非本租户下的产品，无权操作");
			}
			BUtils.copyPropertiesIgnoreNull(product, p, "standards");
			if(StringUtils.isBlank(product.getRemark())){
				p.setRemark(null);
			}
			p.setStatus(StatusEnum.getEnum(product.getStatus()));
			p.setType(ProductTypeEnum.getEnum(product.getType()));
			p.setModifyBy(loginName);
			p.setModifyTime(new Date());
			List<StandardEntity> list = Lists.newArrayList();
			List<Long> delList = Lists.newArrayList();
			if (Collections3.isNotEmpty(product.getStandards())) {
				List<StandardEntity> standardList = p.getStandards();
				List<Long> updateList = Lists.newArrayList();
				if (Collections3.isNotEmpty(standardList)) {
					Map<Long, StandardEntity> map = Maps.newHashMap();
					for (StandardEntity st : standardList) {
						map.put(st.getId(), st);
					}
					product.getStandards().forEach(s -> {
						if (null != s.getId()) {
							StandardEntity standard = map.get(s.getId());
							if (null != standard) {
								BUtils.copyPropertiesIgnoreNull(s, standard);
								standard.setProduct(p);
								standard.setModifyBy(loginName);
								standard.setModifyTime(new Date());
								list.add(standard);
								updateList.add(standard.getId());
							}
						} else {
							StandardEntity standardEntity = new StandardEntity();
							BUtils.copyPropertiesIgnoreNull(s, standardEntity, "id");
							standardEntity.setTenantCode(p.getTenantCode());
							standardEntity.setProduct(p);
							standardEntity.setCreateTime(new Date());
							standardEntity.setCreateBy(loginName);
							list.add(standardEntity);
						}
					});
				}
				//判断是否有需删除的标准数据
				for (StandardEntity s : standardList) {
					if (!updateList.contains(s.getId())) {
						delList.add(s.getId());
					}
				}
			}
			p.setStandards(list);
			productService.updateProduct(p, delList, "更新产品:" + p.getName(), OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新产品失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "禁/启产品", notes = "禁用、启用产品")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "产品ID", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "status", value = "状态0=启用，1=禁用", required = true, dataType = "Integer") })
	@PostMapping("/status/{id}/{status}")
	public @ResponseBody BaseResponse statusChange(@PathVariable("id") Long id, @PathVariable("status") Integer status,
			HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(id, "id为null");
			Preconditions.checkNotNull(status, "status为null");
			ProductEntity product = productService.findOne(id);
			Preconditions.checkNotNull(product, "查无该产品");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),product.getTenantCode()),"非本租户下的产品，无权操作");
			}
			product.setStatus(StatusEnum.getEnum(status));
			product.setModifyBy(loginName);
			product.setModifyTime(new Date());
			productService.saveProduct(product, "更新产品:" + product.getName() + ";状态:" + product.getStatus().getText(),
					OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "禁用成功");
		} catch (Exception e) {
			logger.info("禁用产品失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "产品数据", notes = "获取产品数据")
	@ApiImplicitParam(name = "id", value = "产品ID", required = true, dataType = "Long")
	@PostMapping("/info/{id}")
	public @ResponseBody BaseResponse<ProductDto> getProduct(@PathVariable("id") Long id) {
		try {
			Preconditions.checkNotNull(id, "id参数异常");
			ProductEntity p = productService.findOne(id);
			Preconditions.checkNotNull(p, "查无对应产品");
			ProductDto product = new ProductDto();
			BUtils.copyPropertiesIgnoreNull(p, product);
			product.setType(p.getType().getValue());
			product.setStatus(p.getStatus().getValue());
			List<StandardDto> standards = p.getStandards().stream().map(s -> {
				StandardDto standard = new StandardDto();
				BUtils.copyPropertiesIgnoreNull(s, standard);
				standard.setProductId(p.getId());
				return standard;
			}).collect(Collectors.toList());
			product.setStandards(standards);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", product);
		} catch (Exception e) {
			logger.info("获取 产品失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "产品列表", notes = "获取产品列表数据")
	@PostMapping("/list")
	public @ResponseBody BaseResponse<List<ProductDto>> list(HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			List<ProductEntity> products = Lists.newArrayList();
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				SearchFilters searchFilters = new SearchFilters();
				searchFilters.add(Searcher.eq("tenantCode", operator.getTenantCode()));
				products = (List<ProductEntity>) productService.findAll(searchFilters);
			}else{
				products = (List<ProductEntity>) productService.findAll();
			}
			List<ProductDto> dtos = products.stream().map(p -> {
				ProductDto product = new ProductDto();
				BUtils.copyPropertiesIgnoreNull(p, product);
				product.setType(p.getType().getValue());
				product.setStatus(p.getStatus().getValue());
				List<StandardDto> standards = p.getStandards().stream().map(s -> {
					StandardDto standard = new StandardDto();
					BUtils.copyPropertiesIgnoreNull(s, standard);
					standard.setProductId(p.getId());
					return standard;
				}).collect(Collectors.toList());
				product.setStandards(standards);
				return product;
			}).collect(Collectors.toList());
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", dtos);
		} catch (Exception e) {
			logger.info("获取产品列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取产品列表失败");
		}
	}

	@ApiOperation(value = "删除产品", notes = "删除产品数据")
	@PostMapping("/delete/{id}")
	public @ResponseBody BaseResponse delete(@PathVariable("id") Long id, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(id, "id参数异常");
			ProductEntity product = productService.findOne(id);
			Preconditions.checkNotNull(product, "查无对应产品");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),product.getTenantCode()),"非本租户下的产品，无权操作");
			}
			//判断若有该产品出库单数据则无法执行删除
			SearchFilters searchFilters = new SearchFilters();
			searchFilters.add(Searcher.eq("productId", id));
			List<DeliveryOrderEntity> list = deliveryService.findAll(searchFilters);
			Preconditions.checkState(Collections3.isEmpty(list), "该产品已有对应单据，无法执行删除操作");
			if (Collections3.isNotEmpty(product.getStandards())) {
				logger.info("产品ID:{},级联删除相关标准指标", id);
			}
			productService.deleteProduct(product, "删除产品:" + product.getName(), OperateTypeEnum.DELETE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("删除产品失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

}
