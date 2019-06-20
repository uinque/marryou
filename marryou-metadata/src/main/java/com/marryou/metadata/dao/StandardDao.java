package com.marryou.metadata.dao;

import com.marryou.metadata.entity.StandardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marryou.metadata.entity.ProductEntity;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/4/16
 */
@Repository
public interface StandardDao extends BaseDao<StandardEntity> {

    @Query(value = "select * from standard where product_id = ?1",nativeQuery = true)
    List<StandardEntity> findByProductId(Long productId);
}
