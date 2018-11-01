package com.marryou.service.impl;

import com.marryou.commons.utils.collections.Collections3;
import com.marryou.metadata.dao.ProductDao;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.service.OperateLogService;
import com.marryou.service.ProductService;
import com.marryou.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/6.
 */
@Service
public class ProductServiceImpl extends AbsBaseService<ProductEntity, ProductDao> implements ProductService {

	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private StandardService standardService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateProduct(ProductEntity product, List<Long> standardsIds, String logContent, OperateTypeEnum type,
			String operate) {
		this.save(product);
		operateLogService.save(new OperateLogEntity(logContent, type, product.getId(), LogTypeEnum.PRODUCT, operate,
				new Date(), product.getTenantCode()));
		if (Collections3.isNotEmpty(standardsIds)) {
			standardsIds.forEach(id -> {
				standardService.delete(id);
			});
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveProduct(ProductEntity product, String logContent, OperateTypeEnum type, String operate) {
		this.save(product);
		operateLogService.save(new OperateLogEntity(logContent, type, product.getId(), LogTypeEnum.PRODUCT, operate,
				new Date(), product.getTenantCode()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteProduct(ProductEntity product, String logContent, OperateTypeEnum type, String operate) {
		operateLogService.save(new OperateLogEntity(logContent, type, product.getId(), LogTypeEnum.PRODUCT, operate,
				new Date(), product.getTenantCode()));
		this.delete(product.getId());
	}
}
