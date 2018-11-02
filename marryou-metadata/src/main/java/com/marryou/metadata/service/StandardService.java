package com.marryou.metadata.service;

import com.marryou.metadata.dao.StandardDao;
import com.marryou.metadata.entity.StandardEntity;
import com.marryou.metadata.enums.OperateTypeEnum;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface StandardService extends BaseService<StandardEntity,StandardDao> {

    void saveStandard(StandardEntity standard, String logContent, OperateTypeEnum type, String operate);

    void deleteStandard(StandardEntity standard, String logContent, OperateTypeEnum type, String operate);

}
