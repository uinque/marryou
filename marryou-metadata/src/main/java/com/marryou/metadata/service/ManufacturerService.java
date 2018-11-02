package com.marryou.metadata.service;

import com.marryou.metadata.dao.ManufacturerDao;
import com.marryou.metadata.entity.ManufacturerEntity;
import com.marryou.metadata.enums.OperateTypeEnum;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface ManufacturerService extends BaseService<ManufacturerEntity,ManufacturerDao> {

    void saveMft(ManufacturerEntity mft, String logContent, OperateTypeEnum type, String operate);

    void deleteMft(ManufacturerEntity mft, String logContent, OperateTypeEnum type, String operate);
}
