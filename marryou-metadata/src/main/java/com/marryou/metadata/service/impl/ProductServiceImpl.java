package com.marryou.metadata.service.impl;

import com.marryou.commons.utils.collections.Collections3;
import com.marryou.metadata.dao.ProductDao;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.entity.StandardParamsEntity;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.service.OperateLogService;
import com.marryou.metadata.service.ProductService;
import com.marryou.metadata.service.StandardParamsService;
import com.marryou.metadata.service.StandardService;
import com.marryou.metadata.service.StandardTitleService;
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
	private StandardTitleService standardTitleService;
	@Autowired
	private StandardParamsService standardParamsService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateProduct(ProductEntity product, String logContent, OperateTypeEnum type,
			String operate) {
		this.save(product);
		operateLogService.save(new OperateLogEntity(logContent, type, product.getId(), LogTypeEnum.PRODUCT, operate,
				new Date(), product.getTenantCode()));
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
		List<StandardTitleEntity> titles = standardTitleService.findByProductId(product.getId());
		if(Collections3.isNotEmpty(titles)){
			standardTitleService.delete(titles);
		}
		List<StandardParamsEntity> params = standardParamsService.findByProductId(product.getId());
		if(Collections3.isNotEmpty(params)){
			standardParamsService.delete(params);
		}
		operateLogService.save(new OperateLogEntity(logContent, type, product.getId(), LogTypeEnum.PRODUCT, operate,
				new Date(), product.getTenantCode()));
		this.delete(product.getId());
	}
}
