package com.marryou.metadata.service;

import java.util.List;

import com.marryou.metadata.dao.ProductDao;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.enums.OperateTypeEnum;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface ProductService extends BaseService<ProductEntity, ProductDao> {

	void updateProduct(ProductEntity product, List<Long> standardsIds, String logContent, OperateTypeEnum type, String operate);

	void saveProduct(ProductEntity product, String logContent, OperateTypeEnum type, String operate);

	void deleteProduct(ProductEntity product, String logContent, OperateTypeEnum type, String operate);
}
