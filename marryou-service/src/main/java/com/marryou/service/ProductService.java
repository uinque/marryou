package com.marryou.service;

import com.marryou.metadata.dao.ProductDao;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.enums.OperateTypeEnum;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface ProductService extends BaseService<ProductEntity, ProductDao> {

	void updateProduct(ProductEntity product, List<Long> standardsIds, String logContent, OperateTypeEnum type, String operate);

	void saveProduct(ProductEntity product, String logContent, OperateTypeEnum type, String operate);

	void deleteProduct(ProductEntity product, String logContent, OperateTypeEnum type, String operate);
}
