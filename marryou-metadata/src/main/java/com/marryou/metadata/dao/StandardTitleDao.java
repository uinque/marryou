package com.marryou.metadata.dao;

import com.marryou.metadata.entity.StandardTitleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/4/16
 */
@Repository
public interface StandardTitleDao extends BaseDao<StandardTitleEntity> {

	List<StandardTitleEntity> findByProductIdAndTypeAndTenantCode(Long productId, Integer type, String tenantCode);

	@Query(value = "SELECT * from standard_title where product_id=?1 and type=?2 order by order_sort asc",nativeQuery = true)
	List<StandardTitleEntity> findByProductIdAndType(Long productId, Integer type);


	List<StandardTitleEntity> findByProductIdAndTenantCode(Long productId,String tenantCode);

	List<StandardTitleEntity> findByProductId(Long productId);
}
