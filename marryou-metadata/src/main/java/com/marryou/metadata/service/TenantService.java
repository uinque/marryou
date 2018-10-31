package com.marryou.metadata.service;

import com.marryou.metadata.dao.TenantDao;
import com.marryou.metadata.entity.TenantEntity;
import com.marryou.metadata.enums.OperateTypeEnum;


public interface TenantService extends BaseService<TenantEntity,TenantDao> {

    TenantEntity findByTenantCode(String tenantCode);

    void saveTenant(TenantEntity tenant, String logContent, OperateTypeEnum type, String operate);

    void deleteTenant(TenantEntity tenant, String logContent, OperateTypeEnum type, String operate);
}
