package com.marryou.metadata.dao;

import com.marryou.metadata.entity.StandardParamsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Administrator
 * @date 2017/4/16
 */
@Repository
public interface StandardParamsDao extends BaseDao<StandardParamsEntity> {

    List<StandardParamsEntity> findByProductId(Long productId);

    List<StandardParamsEntity> findByProductIdAndColumnId(Long productId,Long columnId);

    List<StandardParamsEntity> findByProductIdAndRowId(Long productId,Long rowId);

    StandardParamsEntity findByProductIdAndRowIdAndColumnId(Long productId,Long rowId,Long columnId);

    int deleteByProductId(Long productId);
}
