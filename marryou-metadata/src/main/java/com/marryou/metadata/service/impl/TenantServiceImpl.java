package com.marryou.metadata.service.impl;

import java.util.Date;

import com.marryou.metadata.dao.TenantDao;
import com.marryou.metadata.entity.TenantEntity;
import com.marryou.metadata.service.TenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marryou.metadata.dao.ManufacturerDao;
import com.marryou.metadata.entity.ManufacturerEntity;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.service.ManufacturerService;
import com.marryou.metadata.service.OperateLogService;

/**
 * Created by linhy on 2018/6/6.
 */
@Service
public class TenantServiceImpl extends AbsBaseService<TenantEntity, TenantDao>
		implements TenantService {

	@Autowired
	private OperateLogService operateLogService;

	@Override
	public TenantEntity findByTenantCode(String tenantCode) {
		if(StringUtils.isNotBlank(tenantCode)){

		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveTenant(TenantEntity tenant, String logContent, OperateTypeEnum type, String operate) {
		this.save(tenant);
		operateLogService.save(new OperateLogEntity(logContent, type, tenant.getId(), LogTypeEnum.TENANT, operate,
				new Date(), tenant.getTenantCode()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTenant(TenantEntity tenant, String logContent, OperateTypeEnum type, String operate) {
		operateLogService.save(new OperateLogEntity(logContent, type, tenant.getId(), LogTypeEnum.TENANT, operate,
				new Date(), tenant.getTenantCode()));
		this.delete(tenant.getId());
	}
}
