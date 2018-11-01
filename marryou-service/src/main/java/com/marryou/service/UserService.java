package com.marryou.service;

import com.marryou.metadata.dto.UserSearchDto;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.dao.UserDao;
import com.marryou.metadata.enums.OperateTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author Administrator
 * @date 2017/4/16
 */
public interface UserService extends BaseService<UserEntity, UserDao> {

	/**
	 * 根据登录名获取用户信息
	 * @param loginName
	 * @return
	 */
	public UserEntity getUserByLoginName(String loginName);

	/**
	 * 分页查询
	 * @param pageRequest
	 * @param search
	 * @return
	 */
	Page<UserEntity> findUsers(PageRequest pageRequest, UserSearchDto search);

	/**
	 * @param user
	 * @param logContent
	 * @param type
	 */
	public void saveUser(UserEntity user, String logContent, OperateTypeEnum type, String operate);
}
