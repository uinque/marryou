package com.marryou.controller.warehouse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.marryou.metadata.dto.WarehouseOrderDto;
import com.marryou.metadata.entity.WarehouseOrderEntity;
import com.marryou.metadata.service.WarehouseOrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.marryou.commons.utils.RandomUtils;
import com.marryou.commons.utils.base.BUtils;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.commons.utils.time.DateUtils;
import com.marryou.dto.request.BasePageRequest;
import com.marryou.dto.response.BaseResponse;
import com.marryou.dto.response.PageResponse;
import com.marryou.metadata.dto.DeliveryCountDto;
import com.marryou.metadata.dto.DeliveryDto;
import com.marryou.metadata.dto.DeliveryInfoDto;
import com.marryou.metadata.dto.StandardParamsDto;
import com.marryou.metadata.entity.CompanyEntity;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.DeliveryStandardEntity;
import com.marryou.metadata.entity.EntrepotEntity;
import com.marryou.metadata.entity.ManufacturerEntity;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.entity.StandardEntity;
import com.marryou.metadata.entity.TenantEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.LevelEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.enums.TechnoEnum;
import com.marryou.metadata.service.CompanyService;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.EntrepotService;
import com.marryou.metadata.service.ManufacturerService;
import com.marryou.metadata.service.ProductService;
import com.marryou.metadata.service.StandardService;
import com.marryou.metadata.service.TenantService;
import com.marryou.metadata.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import com.marryou.utils.RoleUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author Administrator
 * @date 2017/4/22
 */
@Controller
@RequestMapping("/api/warehouse")
public class WarehouseController {

	private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private WarehouseOrderService warehouseOrderService;
	@Autowired
	private TenantService tenantService;

	@ApiOperation(value = "创建入库单", notes = "提交入库单数据")
	@ApiImplicitParam(name = "warehouse", value = "入库单信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody WarehouseOrderDto warehouse, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().getValue() < RoleEnum.MEMBER.getValue(), "无权限创建出库单据");
			Preconditions.checkState(StringUtils.isNotBlank(warehouse.getCarNo()), "车牌为null");
			Preconditions.checkState(StringUtils.isNotBlank(warehouse.getChecker()), "检验员为null");
			Preconditions.checkState(StringUtils.isNotBlank(warehouse.getAuditor()), "审核员为null");
			Preconditions.checkState(StringUtils.isNotBlank(warehouse.getCompanyName()), "公司名称为null");
			Preconditions.checkState(StringUtils.isNotBlank(warehouse.getProductName()), "产品名称为null");
			WarehouseOrderEntity w = new WarehouseOrderEntity();
			BUtils.copyPropertiesIgnoreNull(warehouse, w, "id", "checkStartTime", "checkOutTime", "status");
			w.setWarehouseNo(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
			w.setCheckStartTime(DateUtils.convertToDateTime(warehouse.getCheckStartTime()));
			w.setCheckOutTime(DateUtils.convertToDateTime(warehouse.getCheckOutTime()));
			w.setStatus(StatusEnum.EFFECTIVE);
			w.setTenantCode(operator.getTenantCode());
			w.setCreateBy(loginName);
			w.setCreateTime(new Date());
			warehouseOrderService.createWarehouseOrder(w);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建成功");
		} catch (Exception e) {
			logger.info("创建入库单失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "创建失败:" + e.getMessage());
		}
	}

	@ApiOperation(value = "失效/恢复", notes = "失效、恢复入库单据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "入库单ID", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "status", value = "状态0=正常，1=失效", required = true, dataType = "Integer") })
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
			WarehouseOrderEntity warehouseOrder = warehouseOrderService.findOne(id);
			Preconditions.checkNotNull(warehouseOrder, "查无该入库单数据");
			Preconditions.checkState(operator.getRole().getValue() < RoleEnum.MEMBER.getValue(), "无权限失效/恢复该单据");
			if (!RoleUtils.isPlatformAdmin(operator.getTenantCode())) {
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(), warehouseOrder.getTenantCode()),
						"非本租户下的出库单，无权操作");
			}
			warehouseOrder.setStatus(StatusEnum.getEnum(status));
			warehouseOrder.setModifyBy(loginName);
			warehouseOrder.setModifyTime(new Date());
			warehouseOrderService.saveWarehouseOrder(warehouseOrder,
					"入库单id:" + warehouseOrder.getId() + ";状态更新:" + warehouseOrder.getStatus(), OperateTypeEnum.UPDATE,
					loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "操作成功");
		} catch (Exception e) {
			logger.info("操作失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "操作失败");
		}
	}

	@ApiOperation(value = "入库单数据", notes = "获取入库单数据")
	@ApiImplicitParam(name = "id", value = "入库单ID", required = true, dataType = "Long")
	@PostMapping("/info/{id}")
	public @ResponseBody BaseResponse<DeliveryInfoDto> getDeliveryOrder(@PathVariable("id") Long id) {
		try {
			Preconditions.checkNotNull(id, "id参数异常");
			WarehouseOrderEntity warehouse = warehouseOrderService.findOne(id);
			Preconditions.checkNotNull(warehouse, "查无对应入库单数据");
			WarehouseOrderDto info = new WarehouseOrderDto();
			BeanUtils.copyProperties(warehouse, info);
			info.setStatus(warehouse.getStatus().getValue());
			info.setCheckStartTime(DateUtils.formatDate(warehouse.getCheckStartTime(), "yyyy-MM-dd HH:mm:ss"));
			info.setCheckOutTime(DateUtils.formatDate(warehouse.getCheckOutTime(), "yyyy-MM-dd HH:mm:ss"));
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", info);
		} catch (Exception e) {
			logger.info("获取入库单失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取入库单失败");
		}
	}

	@ApiOperation(value = "入库单列表", notes = "入库单列表数据")
	@ApiImplicitParam(name = "search", value = "查询出库单信息", required = false, dataType = "Object")
	@PostMapping("/list")
	public @ResponseBody BaseResponse<PageResponse<WarehouseOrderDto>> list(
			@RequestBody BasePageRequest<WarehouseOrderDto> search, HttpServletRequest request) {
		try {
			Preconditions.checkNotNull(search, "查询参数异常");
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			if (null == search.getParams()) {
				search.setParams(new WarehouseOrderDto());
			}
			if (!RoleUtils.isPlatformAdmin(operator.getTenantCode())) {
				search.getParams().setTenantCode(operator.getTenantCode());
			}
			Page<WarehouseOrderEntity> page = warehouseOrderService.findWarehouseOrders(search.toPageRequest(),
					search.getParams());
			List<WarehouseOrderDto> rows = Lists.newArrayList();
			if (Collections3.isNotEmpty(page.getContent())) {
				rows = page.getContent().stream().map(d -> {
					WarehouseOrderDto warehouseOrderDto = new WarehouseOrderDto();
					BUtils.copyPropertiesIgnoreNull(d, warehouseOrderDto);
					warehouseOrderDto.setStatus(d.getStatus().getValue());
					if (null != d.getCheckStartTime()) {
						warehouseOrderDto
								.setCheckStartTime(DateUtils.formatDate(d.getCheckStartTime(), "yyyy-MM-dd HH:mm:ss"));
					}
					if (null != d.getCheckOutTime()) {
						warehouseOrderDto
								.setCheckOutTime(DateUtils.formatDate(d.getCheckOutTime(), "yyyy-MM-dd HH:mm:ss"));
					}
					warehouseOrderDto.setCreateTime(DateUtils.formatDate(d.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					if (null != d.getModifyTime()) {
						warehouseOrderDto.setModifyTime(DateUtils.formatDate(d.getModifyTime(), "yyyy-MM-dd HH:mm:ss"));
					}
					return warehouseOrderDto;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", new PageResponse(page.getTotalElements(),
					page.getTotalPages(), page.getNumber(), page.getSize(), rows));
		} catch (Exception e) {
			logger.info("获取入库单列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取入库单列表失败");
		}
	}

	@ApiOperation(value = "更新入库单数据", notes = "更新入库单数据")
	@ApiImplicitParam(name = "warehouse", value = "更新信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody WarehouseOrderDto warehouseOrderDto,
			HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().getValue() < RoleEnum.MEMBER.getValue(), "无权限更新出库单据");
			logger.info("###更新入库单ID:{},请求参数：{},###", warehouseOrderDto.getId(),
					GsonUtils.buildGson().toJson(warehouseOrderDto));
			Preconditions.checkNotNull(warehouseOrderDto.getId(), "入库单ID为null");
			WarehouseOrderEntity w = warehouseOrderService.findOne(warehouseOrderDto.getId());
			Preconditions.checkNotNull(w, "查无对应入库单单据");
			if (!RoleUtils.isPlatformAdmin(operator.getTenantCode())) {
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(), w.getTenantCode()),
						"非本租户下的出库单，无权操作");
			}
			BUtils.copyPropertiesIgnoreNull(warehouseOrderDto, w, "id", "checkStartTime", "checkOutTime", "status");
			if (StringUtils.isNotBlank(warehouseOrderDto.getCheckStartTime())) {
				w.setCheckStartTime(DateUtils.convertToDateTime(warehouseOrderDto.getCheckStartTime()));
			}
			if (StringUtils.isNotBlank(warehouseOrderDto.getCheckOutTime())) {
				w.setCheckOutTime(DateUtils.convertToDateTime(warehouseOrderDto.getCheckOutTime()));
			}
			if (null != warehouseOrderDto.getStatus()) {
				w.setStatus(StatusEnum.getEnum(warehouseOrderDto.getStatus()));
			}
			w.setModifyBy(loginName);
			w.setModifyTime(new Date());
			warehouseOrderService.saveWarehouseOrder(w, "更新入库单id:" + w.getId(), OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新入库单信息失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "更新入库单信息失败" + e.getMessage());
		}
	}
}
