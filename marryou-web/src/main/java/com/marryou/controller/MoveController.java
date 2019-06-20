package com.marryou.controller;


import com.marryou.metadata.dto.UserSearchDto;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.DeliveryStandardService;
import com.marryou.metadata.service.MoveService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.marryou.dto.response.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by linhy on 2018/6/3.
 */
@Controller
@RequestMapping("/api/move")
public class MoveController {

	private static final Logger logger = LoggerFactory.getLogger(MoveController.class);

	@Autowired
	private MoveService moveService;
	@Autowired
	private DeliveryService deliveryService;

	@GetMapping("/s/{code}")
	public @ResponseBody BaseResponse moveStandard(@PathVariable("code") String code) {
		try {
			Preconditions.checkState(StringUtils.equals(code,"qweasdzxc"),"操作码错误");
			StopWatch watch = new StopWatch();
			watch.start();
			logger.info("############【数据迁移】——模板数据迁移开始############");
			moveService.moveStandardData();
			logger.info("############【数据迁移】——模板数据迁移结束，耗时：{}############", watch.getTime());
			watch.stop();
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("【数据迁移】模板数据失败",e);
			return new BaseResponse<UserSearchDto>(BaseResponse.CODE_FAILED, e.getMessage());
		}

	}

	@GetMapping("/d/{code}")
	public @ResponseBody BaseResponse moveDelivery(@PathVariable("code") String code) {
		try {
			Preconditions.checkState(StringUtils.equals(code,"qweasdzxc"),"操作码错误");
			StopWatch watch2 = new StopWatch();
			watch2.start();
			logger.info("############【数据迁移】——出库单数据迁移开始############");
			Map<String, StandardTitleEntity> columnMap = moveService.bulidColumnTitleMap();
			int size = 2500;
			for(int i=0;i<=3;i++){
				Pageable pageable = new PageRequest(i,size);
				Page<DeliveryOrderEntity> page = deliveryService.findAll(pageable);
				logger.info("############【数据迁移】——第：{}页，共：{}条，出库单数据进行迁移############",i,page.getContent().size());
				moveService.moveDeliveryStandardData(columnMap,page.getContent());
			}

			logger.info("############【数据迁移】——出库单数据迁移结束，耗时：{}############", watch2.getTime());
			watch2.stop();
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("【数据迁移】出库单数据失败",e);
			return new BaseResponse<UserSearchDto>(BaseResponse.CODE_FAILED, e.getMessage());
		}

	}

}
