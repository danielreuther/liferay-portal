/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.currency.service.base;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.service.CommerceCurrencyServiceUtil;
import com.liferay.commerce.currency.service.persistence.CommerceCurrencyFinder;
import com.liferay.commerce.currency.service.persistence.CommerceCurrencyPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the commerce currency remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl
 * @generated
 */
public abstract class CommerceCurrencyServiceBaseImpl
	extends BaseServiceImpl
	implements AopService, CommerceCurrencyService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceCurrencyService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceCurrencyServiceUtil</code>.
	 */
	@Deactivate
	protected void deactivate() {
		CommerceCurrencyServiceUtil.setService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CommerceCurrencyService.class, IdentifiableOSGiService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		commerceCurrencyService = (CommerceCurrencyService)aopProxy;

		CommerceCurrencyServiceUtil.setService(commerceCurrencyService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceCurrencyService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceCurrency.class;
	}

	protected String getModelClassName() {
		return CommerceCurrency.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = commerceCurrencyPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Reference
	protected com.liferay.commerce.currency.service.CommerceCurrencyLocalService
		commerceCurrencyLocalService;

	protected CommerceCurrencyService commerceCurrencyService;

	@Reference
	protected CommerceCurrencyPersistence commerceCurrencyPersistence;

	@Reference
	protected CommerceCurrencyFinder commerceCurrencyFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCurrencyServiceBaseImpl.class);

}