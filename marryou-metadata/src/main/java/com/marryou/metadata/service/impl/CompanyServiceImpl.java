package com.marryou.metadata.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.time.DateUtils;
import com.marryou.metadata.dao.CompanyDao;
import com.marryou.metadata.dto.CompanyDto;
import com.marryou.metadata.entity.CompanyEntity;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.persistence.SearchFilters;
import com.marryou.metadata.persistence.Searcher;
import com.marryou.metadata.service.CompanyService;
import com.marryou.metadata.service.OperateLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/6.
 */
@Service
public class CompanyServiceImpl extends AbsBaseService<CompanyEntity, CompanyDao> implements CompanyService {

	@Autowired
	private OperateLogService operateLogService;

	@Override
	public Page<CompanyEntity> findCompanys(PageRequest pageRequest, CompanyDto search) {
		Preconditions.checkNotNull(pageRequest, "pageRequest为null");
		Page<CompanyEntity> page = dao.findAll(new Specification<CompanyEntity>() {
			@Override
			public Predicate toPredicate(Root<CompanyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> where = Lists.newArrayList();
				Path<Long> id = root.get("id");
				Path<String> name = root.get("name");
				Path<String> city = root.get("city");
				Path<String> contacts = root.get("contacts");
				Path<String> phone = root.get("phone");
				Path<StatusEnum> status = root.get("status");
				Path<Date> createTime = root.get("createTime");
				Path<String> tenantCode = root.get("tenantCode");
				if (null != search) {
					if (null != search.getId()) {
						where.add(cb.and(cb.equal(id, search.getId())));
					}
					if (StringUtils.isNotBlank(search.getName())) {
						where.add(cb.and(cb.like(name, search.getName() + "%")));
					}
					if (StringUtils.isNotBlank(search.getContacts())) {
						where.add(cb.and(cb.like(contacts, search.getContacts() + "%")));
					}
					if (StringUtils.isNotBlank(search.getPhone())) {
						where.add(cb.and(cb.equal(phone, search.getPhone())));
					}
					if (StringUtils.isNotBlank(search.getCity())) {
						where.add(cb.and(cb.like(city, search.getCity() + "%")));
					}
					if (null != search.getStatus()) {
						where.add(cb.and(cb.equal(status, StatusEnum.getEnum(search.getStatus()))));
					}
					if (StringUtils.isNotBlank(search.getTenantCode())) {
						where.add(cb.and(cb.equal(tenantCode, search.getTenantCode())));
					}
					if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
						where.add(cb.and(cb.between(createTime, DateUtils.convertToDateTime(search.getStartTime()),
								DateUtils.convertToDateTime(search.getEndTime()))));
					}
				}
				query.where(where.toArray(new Predicate[] {}));
				return null;
			}
		}, pageRequest);
		return page;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveCompany(CompanyEntity company, String logContent, OperateTypeEnum type, String operate) {
		this.save(company);
		operateLogService.save(new OperateLogEntity(logContent, type, company.getId(), LogTypeEnum.COMPANY, operate,
				new Date(), company.getTenantCode()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCompany(CompanyEntity company, String logContent, OperateTypeEnum type, String operate) {
		operateLogService.save(new OperateLogEntity(logContent, type, company.getId(), LogTypeEnum.COMPANY, operate,
				new Date(), company.getTenantCode()));
		this.delete(company.getId());
	}

	@Override
	public CompanyEntity findCompanyByCompanyName(String companyName, String tenantCode) {
		Preconditions.checkState(StringUtils.isNotBlank(companyName), "公司名称为null");
		SearchFilters searchFilters = new SearchFilters();
		searchFilters.add(Searcher.eq("name", companyName));
		if (StringUtils.isNotBlank(tenantCode)) {
			searchFilters.add(Searcher.eq("tenantCode", tenantCode));
		}
		List<CompanyEntity> list = this.findAll(searchFilters);
		if (Collections3.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}
