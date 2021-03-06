package com.marryou.metadata.service.impl;

import com.marryou.metadata.dao.StandardDao;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.StandardEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.service.OperateLogService;
import com.marryou.metadata.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by linhy on 2018/6/6.
 */
@Service
public class StandardServiceImpl extends AbsBaseService<StandardEntity, StandardDao> implements StandardService {

    @Autowired
    private OperateLogService operateLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStandard(StandardEntity standard, String logContent, OperateTypeEnum type, String operate) {
        this.save(standard);
        operateLogService.save(new OperateLogEntity(logContent, type, standard.getId(),
                LogTypeEnum.STANDARD,operate, new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStandard(StandardEntity standard, String logContent, OperateTypeEnum type, String operate) {
        operateLogService.save(new OperateLogEntity(logContent, type, standard.getId(),
                LogTypeEnum.STANDARD,operate, new Date()));
        this.delete(standard.getId());
    }
}
