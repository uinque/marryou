package com.marryou.metadata.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.time.DateUtils;
import com.marryou.metadata.dao.UserDao;
import com.marryou.metadata.dto.UserSearchDto;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.persistence.SearchFilters;
import com.marryou.metadata.persistence.Searcher;
import com.marryou.metadata.service.OperateLogService;
import com.marryou.metadata.service.UserService;
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
 * @author Administrator
 * @date 2017/4/16
 */
@Service
public class UserServiceImpl extends AbsBaseService<UserEntity, UserDao> implements UserService {

	@Autowired
	private OperateLogService operateLogService;

	@Override
	public UserEntity getUserByLoginName(String loginName) {
		Preconditions.checkState(StringUtils.isNotBlank(loginName), "loginName为null");
		SearchFilters searchFilters = new SearchFilters();
		searchFilters.add(Searcher.eq("loginName", loginName)).add(Searcher.eq("status", StatusEnum.EFFECTIVE));
		List<UserEntity> list = this.findAll(searchFilters);
		if (Collections3.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page<UserEntity> findUsers(PageRequest pageRequest, UserSearchDto search) {
		Preconditions.checkNotNull(pageRequest, "pageRequest为null");
		Page<UserEntity> page = dao.findAll(new Specification<UserEntity>() {
			@Override
			public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> where = Lists.newArrayList();
				Path<Long> id = root.get("id");
				Path<String> loginName = root.get("loginName");
				Path<String> phone = root.get("phone");
				Path<StatusEnum> status = root.get("status");
				Path<RoleEnum> role = root.get("role");
				Path<Long> companyId = root.get("companyId");
				Path<String> tenantCode = root.get("tenantCode");
				Path<Date> createTime = root.get("createTime");
				if (null != search) {
					if (null != search.getId()) {
						where.add(cb.and(cb.equal(id, search.getId())));
					}
					if (StringUtils.isNotBlank(search.getLoginName())) {
						where.add(cb.and(cb.equal(loginName, search.getLoginName())));
					}
					if (StringUtils.isNotBlank(search.getPhone())) {
						where.add(cb.and(cb.equal(phone, search.getPhone())));
					}
					if (null != search.getStatus()) {
						where.add(cb.and(cb.equal(status, StatusEnum.getEnum(search.getStatus()))));
					}
					if (null != search.getRole()) {
						where.add(cb.and(cb.equal(role, RoleEnum.getEnum(search.getRole()))));
					}
					if (null != search.getCompanyId()) {
						where.add(cb.and(cb.equal(companyId, search.getCompanyId())));
					}
					if(StringUtils.isNotBlank(search.getTenantCode())){
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
	public void saveUser(UserEntity user, String logContent, OperateTypeEnum type, String operate) {
		this.save(user);
		operateLogService
				.save(new OperateLogEntity(logContent, type, user.getId(), LogTypeEnum.USER,
						operate, new Date(), user.getTenantCode()));
	}

}
