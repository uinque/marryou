package com.marryou.service;

import com.marryou.metadata.dao.OperateLogDao;
import com.marryou.metadata.dto.LogDto;
import com.marryou.metadata.entity.OperateLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface OperateLogService extends BaseService<OperateLogEntity,OperateLogDao> {

    /**
     * 列表查询
     * @param pageRequest
     * @param search
     * @return
     */
    Page<OperateLogEntity> findLogs(PageRequest pageRequest, LogDto search);
}
