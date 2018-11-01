package com.marryou.service;

import com.marryou.metadata.dao.DeliveryDao;
import com.marryou.metadata.dto.DeliveryCountDto;
import com.marryou.metadata.dto.DeliveryDto;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.DeliveryStandardEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface DeliveryService extends BaseService<DeliveryOrderEntity, DeliveryDao> {

	/**
	 * 出库单列表
	 * @param pageRequest
	 * @param search
	 * @return
	 */
	Page<DeliveryOrderEntity> findDeliveryOrders(PageRequest pageRequest, DeliveryDto search);

	/**
	 * 出库单统计
	 * @param search
	 * @return
	 */
	DeliveryCountDto statisticsDelivery(DeliveryDto search);

	/**
	 * 创建出库单据并生成二维码
	 * @param deliveryOrder
	 */
	void createDeliverOrder(DeliveryOrderEntity deliveryOrder);

	void saveDelivery(DeliveryOrderEntity delivery, List<DeliveryStandardEntity> needDelete,
					  String logContent, OperateTypeEnum type, String operate);

}
