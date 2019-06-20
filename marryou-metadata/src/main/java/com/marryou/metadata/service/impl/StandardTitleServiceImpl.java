package com.marryou.metadata.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.metadata.dao.StandardTitleDao;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.StandardParamsEntity;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.service.OperateLogService;
import com.marryou.metadata.service.StandardParamsService;
import com.marryou.metadata.service.StandardTitleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by linhy on 2019/2/11.
 */
@Service
public class StandardTitleServiceImpl extends AbsBaseService<StandardTitleEntity,StandardTitleDao> implements StandardTitleService {

    @Autowired
    private OperateLogService operateLogService;
    @Autowired
    private StandardTitleDao standardTitleDao;
    @Autowired
    private StandardParamsService standardParamsService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public StandardTitleEntity saveStandardTitle(StandardTitleEntity title, String logContent, OperateTypeEnum type, String operate) {
        operateLogService.save(new OperateLogEntity(logContent, type, title.getId(), LogTypeEnum.STANDARD, operate,
                new Date(), title.getTenantCode()));
        StandardTitleEntity result = this.save(title);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteStandardTitle(StandardTitleEntity title, String logContent, OperateTypeEnum type, String operate) {
        List<StandardParamsEntity> params = Lists.newArrayList();
        if(title.getType().equals(StandardTitleEntity.ROW)){
            params = standardParamsService.findByProductIdAndRowId(title.getProductId(),title.getId());
        }else if(title.getType().equals(StandardTitleEntity.COLUMN)){
            params = standardParamsService.findByProductIdAndColumnId(title.getProductId(),title.getId());
        }
        if(Collections3.isNotEmpty(params)){
            standardParamsService.delete(params);
        }
        this.delete(title.getId());
        operateLogService.save(new OperateLogEntity(logContent, type, title.getId(), LogTypeEnum.STANDARD, operate,
                new Date(), title.getTenantCode()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStandardTitleByReplaceOrderSort(StandardTitleEntity source, StandardTitleEntity target,  String operate) {
        Date currentTime = new Date();
        Integer sourceOrder = source.getOrderSort();
        Integer targetOrder = target.getOrderSort();
        source.setOrderSort(targetOrder);
        source.setModifyBy(operate);
        source.setModifyTime(currentTime);
        target.setOrderSort(sourceOrder);
        target.setModifyBy(operate);
        target.setModifyTime(currentTime);
        this.save(source);
        this.save(target);
    }

    @Override
    public List<StandardTitleEntity> findByProductIdAndTypeAndTenantCode(Long productId, Integer type,String tenantCode) {
        Preconditions.checkNotNull(productId,"productId为null");
        Preconditions.checkNotNull(type,"type为null");
        Preconditions.checkState(StringUtils.isNotBlank(tenantCode),"tenantCode为null");
        return standardTitleDao.findByProductIdAndTypeAndTenantCode(productId,type,tenantCode);
    }

    @Override
    public List<StandardTitleEntity> findByProductIdAndType(Long productId, Integer type) {
        Preconditions.checkNotNull(productId,"productId为null");
        Preconditions.checkNotNull(type,"type为null");
        return standardTitleDao.findByProductIdAndType(productId,type);
    }

    @Override
    public List<StandardTitleEntity> findByProductIdAndTenantCode(Long productId, String tenantCode) {
        Preconditions.checkNotNull(productId,"productId为null");
        Preconditions.checkState(StringUtils.isNotBlank(tenantCode),"tenantCode为null");
        return standardTitleDao.findByProductIdAndTenantCode(productId,tenantCode);
    }

    @Override
    public List<StandardTitleEntity> findByProductId(Long productId) {
        Preconditions.checkNotNull(productId,"productId为null");
        return standardTitleDao.findByProductId(productId);
    }
}
