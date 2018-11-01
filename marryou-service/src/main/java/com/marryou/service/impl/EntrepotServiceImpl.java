package com.marryou.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.metadata.dao.EntrepotDao;
import com.marryou.metadata.dto.EntrepotDto;
import com.marryou.metadata.entity.EntrepotEntity;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.persistence.SearchFilters;
import com.marryou.metadata.persistence.Searcher;
import com.marryou.service.EntrepotService;
import com.marryou.service.OperateLogService;
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
public class EntrepotServiceImpl extends AbsBaseService<EntrepotEntity, EntrepotDao> implements EntrepotService {

	@Autowired
	private OperateLogService operateLogService;

	@Override
	public Page<EntrepotEntity> findEntrepots(PageRequest pageRequest, EntrepotDto search) {
		Preconditions.checkNotNull(pageRequest, "pageRequest为null");
		Page<EntrepotEntity> page = dao.findAll(new Specification<EntrepotEntity>() {
			@Override
			public Predicate toPredicate(Root<EntrepotEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> where = Lists.newArrayList();
				Path<Long> id = root.get("id");
				Path<String> name = root.get("name");
				Path<StatusEnum> status = root.get("status");
				Path<Date> createTime = root.get("createTime");
				if (null != search) {
					if (null != search.getId()) {
						where.add(cb.and(cb.equal(id, search.getId())));
					}
					if (StringUtils.isNotBlank(search.getName())) {
						where.add(cb.and(cb.like(name, search.getName() + "%")));
					}
					if (null != search.getStatus()) {
						where.add(cb.and(cb.equal(status, StatusEnum.getEnum(search.getStatus()))));
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
	public void saveEntrepot(EntrepotEntity entrepot, String logContent, OperateTypeEnum type, String operate) {
		this.save(entrepot);
		operateLogService.save(
				new OperateLogEntity(logContent, type, entrepot.getId(), LogTypeEnum.COMPANY, operate, new Date(), entrepot.getTenantCode()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteEntrepot(EntrepotEntity entrepot, String logContent, OperateTypeEnum type, String operate) {
		operateLogService.save(
				new OperateLogEntity(logContent, type, entrepot.getId(), LogTypeEnum.COMPANY, operate, new Date(), entrepot.getTenantCode()));
		this.delete(entrepot.getId());
	}

	@Override
	public EntrepotEntity findEntrepotByEntrepotName(String entrepotName) {
		Preconditions.checkState(StringUtils.isNotBlank(entrepotName),"仓库名称为null");
		SearchFilters searchFilters = new SearchFilters();
		searchFilters.add(Searcher.eq("name", entrepotName));
		List<EntrepotEntity> list = this.findAll(searchFilters);
		if(Collections3.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
}
