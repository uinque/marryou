package com.marryou.metadata.service;

import com.marryou.metadata.dao.StandardParamsDao;
import com.marryou.metadata.dto.StandardParamsDto;
import com.marryou.metadata.dto.StandardTableDto;
import com.marryou.metadata.entity.StandardParamsEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface StandardParamsService extends BaseService<StandardParamsEntity,StandardParamsDao> {

    void saveStandardParamsList(List<StandardParamsEntity> params);

    void updateStandardParamsList(List<StandardParamsEntity> params);

    List<StandardTableDto> buildStandardParams(Long productId);

    List<StandardParamsEntity> findByProductIdAndRowId(Long productId,Long rowId);

    List<StandardParamsEntity> findByProductIdAndColumnId(Long productId,Long columnId);

    List<StandardParamsEntity> findByProductId(Long productId);

    StandardParamsEntity findByProductIdAndRowIdAndColumnId(Long productId,Long rowId,Long columnId);

}
