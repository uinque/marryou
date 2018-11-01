package com.marryou.service.impl;

import com.marryou.commons.utils.collections.Collections3;
import com.marryou.metadata.dao.TenantDao;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.TenantEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.persistence.SearchFilters;
import com.marryou.metadata.persistence.Searcher;
import com.marryou.service.OperateLogService;
import com.marryou.service.TenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
			SearchFilters searchFilters = new SearchFilters();
			searchFilters.add(Searcher.eq("tenantCode", tenantCode));
			//searchFilters.add(Searcher.eq("status", StatusEnum.EFFECTIVE));
			List<TenantEntity> list = this.findAll(searchFilters);
			if(Collections3.isNotEmpty(list)){
				return list.get(0);
			}
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
