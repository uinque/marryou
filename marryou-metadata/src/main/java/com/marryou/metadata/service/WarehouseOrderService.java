package com.marryou.metadata.service;


import com.marryou.metadata.dao.WarehouseOrderDao;
import com.marryou.metadata.dto.WarehouseOrderDto;
import com.marryou.metadata.entity.WarehouseOrderEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface WarehouseOrderService extends BaseService<WarehouseOrderEntity, WarehouseOrderDao> {

	/**
	 * 入库单列表
	 * @param pageRequest
	 * @param search
	 * @return
	 */
	Page<WarehouseOrderEntity> findWarehouseOrders(PageRequest pageRequest, WarehouseOrderDto search);


	/**
	 * 创建入库单
	 * @param warehouseOrder
	 */
	void createWarehouseOrder(WarehouseOrderEntity warehouseOrder);

	/**
	 * 更新入库单
	 * @param warehouseOrder
	 * @param logContent
	 * @param type
	 * @param operate
	 */
	void saveWarehouseOrder(WarehouseOrderEntity warehouseOrder, String logContent,
							OperateTypeEnum type, String operate);

}
