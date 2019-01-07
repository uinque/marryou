package com.marryou.metadata.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.time.DateUtils;
import com.marryou.metadata.dao.WarehouseOrderDao;
import com.marryou.metadata.dto.WarehouseOrderDto;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.WarehouseOrderEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.service.OperateLogService;
import com.marryou.metadata.service.WarehouseOrderService;
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
 * Created by linhy on 2018/12/2.
 */
@Service
public class WarehouseOrderServiceImpl extends AbsBaseService<WarehouseOrderEntity,WarehouseOrderDao> implements WarehouseOrderService{

    @Autowired
    private OperateLogService operateLogService;

    @Override
    public Page<WarehouseOrderEntity> findWarehouseOrders(PageRequest pageRequest, WarehouseOrderDto search) {
        Preconditions.checkNotNull(pageRequest, "pageRequest为null");
        Page<WarehouseOrderEntity> page = dao.findAll(new Specification<WarehouseOrderEntity>() {
            @Override
            public Predicate toPredicate(Root<WarehouseOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> where = Lists.newArrayList();
                Path<Long> id = root.get("id");
                Path<String> warehouseNo = root.get("warehouseNo");
                Path<Date> checkStartTime = root.get("checkStartTime");
                Path<String> companyName = root.get("companyName");
                Path<String> productName = root.get("productName");
                Path<String> checker = root.get("checker");
                Path<String> auditor = root.get("auditor");
                Path<String> carNo = root.get("carNo");
                Path<String> batchNo = root.get("batchNo");
                Path<StatusEnum> status = root.get("status");
                Path<String> tenantCode = root.get("tenantCode");
                if (null != search) {
                    if (StringUtils.isNotBlank(search.getWarehouseNo())) {
                        where.add(cb.and(cb.like(warehouseNo, search.getWarehouseNo() + "%")));
                    }
                    if (StringUtils.isNotBlank(search.getBatchNo())) {
                        where.add(cb.and(cb.like(batchNo, search.getBatchNo() + "%")));
                    }
                    if (StringUtils.isNotBlank(search.getProductName())) {
                        where.add(cb.and(cb.like(productName, search.getProductName() + "%")));
                    }
                    if (StringUtils.isNotBlank(search.getCompanyName())) {
                        where.add(cb.and(cb.like(companyName, search.getCompanyName() + "%")));
                    }
                    if (StringUtils.isNotBlank(search.getChecker())) {
                        where.add(cb.and(cb.like(checker, search.getChecker() + "%")));
                    }
                    if (StringUtils.isNotBlank(search.getAuditor())) {
                        where.add(cb.and(cb.like(auditor, search.getAuditor() + "%")));
                    }
                    if (StringUtils.isNotBlank(search.getCarNo())) {
                        where.add(cb.and(cb.like(carNo, search.getCarNo() + "%")));
                    }
                    if (null != search.getStatus()) {
                        where.add(cb.and(cb.equal(status, StatusEnum.getEnum(search.getStatus()))));
                    }
                    if (StringUtils.isNotBlank(search.getTenantCode())) {
                        where.add(cb.and(cb.equal(tenantCode, search.getTenantCode())));
                    }
                    if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
                        where.add(cb.and(cb.between(checkStartTime, DateUtils.convertToDateTime(search.getStartTime()),
                                DateUtils.convertToDateTime(search.getEndTime()))));
                    }
                }
                query.where(where.toArray(new Predicate[] {}));
                return null;
            }
        }, pageRequest);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createWarehouseOrder(WarehouseOrderEntity warehouseOrderEntity) {
        WarehouseOrderEntity w = this.save(warehouseOrderEntity);
        operateLogService.save(new OperateLogEntity("创建入库单id:" + w.getId(), OperateTypeEnum.CREATE, w.getId(),
                LogTypeEnum.WAREHOUSE, w.getCreateBy(), new Date(), w.getTenantCode()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWarehouseOrder(WarehouseOrderEntity warehouseOrder, String logContent, OperateTypeEnum type, String operate) {
        this.save(warehouseOrder);
        operateLogService.save(new OperateLogEntity(logContent, type, warehouseOrder.getId(), LogTypeEnum.WAREHOUSE, operate,
                new Date(), warehouseOrder.getTenantCode()));
    }
}
