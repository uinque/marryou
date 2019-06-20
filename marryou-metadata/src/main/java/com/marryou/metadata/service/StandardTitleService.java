package com.marryou.metadata.service;

import com.marryou.metadata.dao.StandardTitleDao;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.enums.OperateTypeEnum;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface StandardTitleService extends BaseService<StandardTitleEntity, StandardTitleDao> {


	StandardTitleEntity saveStandardTitle(StandardTitleEntity title, String logContent, OperateTypeEnum type, String operate);


    void deleteStandardTitle(StandardTitleEntity title, String logContent, OperateTypeEnum type, String operate);

    void updateStandardTitleByReplaceOrderSort(StandardTitleEntity source,StandardTitleEntity target, String operate);

    List<StandardTitleEntity> findByProductIdAndTypeAndTenantCode(Long productId, Integer type, String tenantCode);

    List<StandardTitleEntity> findByProductIdAndType(Long productId, Integer type);

    List<StandardTitleEntity> findByProductIdAndTenantCode(Long productId,String tenantCode);

    List<StandardTitleEntity> findByProductId(Long productId);
}
