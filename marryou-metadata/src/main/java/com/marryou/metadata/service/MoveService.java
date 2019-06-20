package com.marryou.metadata.service;

import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.StandardTitleEntity;

import java.util.List;
import java.util.Map;

public interface MoveService {

	void moveStandardData();

	void moveDeliveryStandardData(Map<String, StandardTitleEntity> columnMap, List<DeliveryOrderEntity> list);

	Map<String, StandardTitleEntity> bulidColumnTitleMap();
}
