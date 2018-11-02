package com.marryou.metadata.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.marryou.metadata.dao.EntrepotDao;
import com.marryou.metadata.dto.EntrepotDto;
import com.marryou.metadata.entity.EntrepotEntity;
import com.marryou.metadata.enums.OperateTypeEnum;

/**
 * @author xph
 * @date 2018/10/23
 */
public interface EntrepotService extends BaseService<EntrepotEntity, EntrepotDao> {

	Page<EntrepotEntity> findEntrepots(PageRequest pageRequest, EntrepotDto search);

	void saveEntrepot(EntrepotEntity entrepot, String logContent, OperateTypeEnum type, String operate);

	void deleteEntrepot(EntrepotEntity company, String logContent, OperateTypeEnum type, String operate);

	EntrepotEntity findEntrepotByEntrepotName(String entrepotName);
}
