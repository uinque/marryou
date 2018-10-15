package com.marryou.metadata.service.impl;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.time.DateUtils;
import com.marryou.metadata.dao.OperateLogDao;
import com.marryou.metadata.dto.LogDto;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.service.OperateLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
public class OperateLogServiceImpl extends AbsBaseService<OperateLogEntity, OperateLogDao> implements OperateLogService {

    @Override
    public Page<OperateLogEntity> findLogs(PageRequest pageRequest, LogDto search) {
        Preconditions.checkNotNull(pageRequest, "pageRequest为null");
        Page<OperateLogEntity> page = dao.findAll(new Specification<OperateLogEntity>() {
            @Override
            public Predicate toPredicate(Root<OperateLogEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> where = Lists.newArrayList();
                Path<Long> id = root.get("id");
                Path<OperateTypeEnum> operateType = root.get("operateType");
                Path<LogTypeEnum> type = root.get("type");
                Path<Long> relationId = root.get("relationId");
                Path<Date> createTime = root.get("createTime");
                if (null != search) {
                    if (null != search.getId()) {
                        where.add(cb.and(cb.equal(id, search.getId())));
                    }
                    if(null!=search.getOperateType()){
                        where.add(cb.and(cb.equal(operateType, search.getOperateType())));
                    }
                    if(null!=search.getType()){
                        where.add(cb.and(cb.equal(type, search.getType())));
                    }
                    if(null!=search.getRelationId()){
                        where.add(cb.and(cb.equal(relationId, search.getRelationId())));
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
}
