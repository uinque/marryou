package com.marryou.controller;


import com.google.common.base.Preconditions;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.dto.DeliveryInfoDto;
import com.marryou.metadata.dto.StandardParamsDto;
import com.marryou.metadata.dto.UserSearchDto;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.DeliveryStandardEntity;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.QrCodeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by linhy on 2018/6/3.
 */
@Controller
@RequestMapping("/api/qrcode")
public class QrcodeController {

	private static final Logger logger = LoggerFactory.getLogger(QrcodeController.class);

	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private QrCodeService qrCodeService;

	@ApiOperation(value = "二维码查询", notes = "二维码扫描后获取出库单单据")
	@PostMapping("/delivery/{id}")
	public @ResponseBody BaseResponse getDelivery(@PathVariable("id") Long id) {
		try {
			Preconditions.checkNotNull(id, "id参数异常");
			DeliveryOrderEntity delivery = deliveryService.findOne(id);
			Preconditions.checkNotNull(delivery, "查无对应出库单数据");
			DeliveryInfoDto info = new DeliveryInfoDto();
			BeanUtils.copyProperties(delivery,info,"standards");
			List<DeliveryStandardEntity> list = delivery.getStandards();
			Preconditions.checkState(Collections3.isNotEmpty(list),"查无对应出库单检验结果");
			List<StandardParamsDto> params = list.stream().map(s->{
				StandardParamsDto dto = new StandardParamsDto();
				BeanUtils.copyProperties(s,dto,"deliveryOrder");
				return dto;
			}).collect(Collectors.toList());
			info.setTechno(delivery.getTechno().getValue());
			info.setLevel(delivery.getLevel().getValue());
			info.setStatus(delivery.getStatus().getValue());
			info.setStandards(params);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", info);
		} catch (Exception e) {
			logger.info("获取出库单据失败",e);
			return new BaseResponse<UserSearchDto>(BaseResponse.CODE_FAILED, e.getMessage());
		}

	}

	@ApiOperation(value = "根据出库单批量生成二维码，上传OSS", notes = "用于批量补充二维码数据")
	@GetMapping("/batch")
	public @ResponseBody BaseResponse batch(HttpServletRequest request) {
		try {
			qrCodeService.batchCreateQrCode();
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("获取出库单据失败",e);
			return new BaseResponse<UserSearchDto>(BaseResponse.CODE_FAILED, e.getMessage());
		}

	}
}
