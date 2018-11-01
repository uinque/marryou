package com.marryou.service;

import com.marryou.metadata.dao.CompanyDao;
import com.marryou.metadata.dto.CompanyDto;
import com.marryou.metadata.entity.CompanyEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface CompanyService extends BaseService<CompanyEntity, CompanyDao> {

	Page<CompanyEntity> findCompanys(PageRequest pageRequest, CompanyDto search);

	void saveCompany(CompanyEntity company, String logContent, OperateTypeEnum type, String operate);

	void deleteCompany(CompanyEntity company, String logContent, OperateTypeEnum type, String operate);

	CompanyEntity findCompanyByCompanyName(String companyName,String tenantCode);
}
