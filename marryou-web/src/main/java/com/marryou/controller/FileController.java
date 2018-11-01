package com.marryou.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.marryou.commons.utils.base.FileUtil;
import com.marryou.dto.request.BasePageRequest;
import com.marryou.metadata.dto.DeliveryDto;
import com.marryou.metadata.dto.DeliveryExportDto;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.service.OperateLogService;
import com.marryou.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import com.marryou.utils.RoleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.service.DeliveryService;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by linhy on 2018/6/3.
 */
@Controller
@RequestMapping("/api/file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private OperateLogService operateLogService;

	@ApiOperation(value = "出库单导出", notes = "按时间导出出库单数据")
	@GetMapping("/delivery/export")
	public @ResponseBody BaseResponse exportDelivery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		BasePageRequest<DeliveryDto> search = new BasePageRequest<>();
		DeliveryDto params = new DeliveryDto();
		search.setParams(params);
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			if (!RoleUtils.isPlatformAdmin(operator.getTenantCode())) {
				if (operator.getRole().equals(RoleEnum.MEMBER)) {
					search.getParams().setDistributorId(operator.getCompanyId());
				}
				search.getParams().setTenantCode(operator.getTenantCode());
			}
			Preconditions.checkNotNull(search, "查询参数异常");
			search.getParams().setStatus(StatusEnum.EFFECTIVE.getValue());
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				search.getParams().setStartTime(startTime);
				search.getParams().setEndTime(endTime);
			}
			//TODO 不需要分页查询改造
			search.setPageIndex(0);
			search.setPageSize(100000);
			Page<DeliveryOrderEntity> page = deliveryService.findDeliveryOrders(search.toPageRequest(),
					search.getParams());
			Preconditions.checkState(Collections3.isNotEmpty(page.getContent()), "查无对应数据");
			List<DeliveryExportDto> list = page.getContent().stream().map(d -> {
				DeliveryExportDto dto = new DeliveryExportDto();
				BeanUtils.copyProperties(d, dto);
				return dto;
			}).collect(Collectors.toList());
			operateLogService.save(new OperateLogEntity("导出出库单数据文件", OperateTypeEnum.OTHER, null, LogTypeEnum.DELIVERY,
					"system", new Date(), operator.getTenantCode()));
			//导出操作
			FileUtil.exportExcel(list, null, null, DeliveryExportDto.class, "出库数据.xls", response);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("出库单数据导出失败", e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}

	}

}
