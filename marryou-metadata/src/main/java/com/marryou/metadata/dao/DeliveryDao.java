package com.marryou.metadata.dao;

import com.marryou.metadata.entity.DeliveryOrderEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * @date 2017/4/16
 */
@Repository
public interface DeliveryDao extends BaseDao<DeliveryOrderEntity> {

    int countByProductId(Long productId);
}
