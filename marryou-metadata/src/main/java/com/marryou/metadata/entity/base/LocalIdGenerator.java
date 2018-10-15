package com.marryou.metadata.entity.base;

import com.marryou.metadata.entity.base.BaseEntity;
import com.marryou.metadata.utils.IdUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author Administrator
 * @date 2017/4/15
 */
public class LocalIdGenerator implements IdentifierGenerator, Configurable {

	private String entityName;

	@Override
	public void configure(org.hibernate.type.Type type, Properties params, ServiceRegistry serviceRegistry)
			throws MappingException {
		//donothing
	}

	@Override
	public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {
		try {
			//如果对象已经有ID设置了，则不需要再产生
			if (o instanceof BaseEntity) {
				BaseEntity base = (BaseEntity) o;
				if (base.getId() != null && 0L != base.getId()) {
					return base.getId();
				}
			}
		} catch (Exception e) {

		}
		return IdUtils.id();
	}
}
