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

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the kaleo log service. This utility wraps <code>com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoLogPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoLogPersistence
 * @generated
 */
public class KaleoLogUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(KaleoLog kaleoLog) {
		getPersistence().clearCache(kaleoLog);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, KaleoLog> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<KaleoLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoLog update(KaleoLog kaleoLog) {
		return getPersistence().update(kaleoLog);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoLog update(
		KaleoLog kaleoLog, ServiceContext serviceContext) {

		return getPersistence().update(kaleoLog, serviceContext);
	}

	/**
	 * Returns all the kaleo logs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo logs
	 */
	public static List<KaleoLog> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the kaleo logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	public static List<KaleoLog> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByCompanyId_First(
			long companyId, OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByCompanyId_First(
		long companyId, OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByCompanyId_Last(
			long companyId, OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByCompanyId_Last(
		long companyId, OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog[] findByCompanyId_PrevAndNext(
			long kaleoLogId, long companyId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByCompanyId_PrevAndNext(
			kaleoLogId, companyId, orderByComparator);
	}

	/**
	 * Removes all the kaleo logs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of kaleo logs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo logs
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoLogId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoDefinitionVersionId_PrevAndNext(
			kaleoLogId, kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Removes all the kaleo logs where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public static void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		getPersistence().removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns the number of kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo logs
	 */
	public static int countByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return getPersistence().countByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns all the kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoInstanceId(long kaleoInstanceId) {
		return getPersistence().findByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKaleoInstanceId_First(
			long kaleoInstanceId, OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoInstanceId_First(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKaleoInstanceId_First(
		long kaleoInstanceId, OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKaleoInstanceId_First(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKaleoInstanceId_Last(
			long kaleoInstanceId, OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoInstanceId_Last(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKaleoInstanceId_Last(
		long kaleoInstanceId, OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKaleoInstanceId_Last(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog[] findByKaleoInstanceId_PrevAndNext(
			long kaleoLogId, long kaleoInstanceId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoInstanceId_PrevAndNext(
			kaleoLogId, kaleoInstanceId, orderByComparator);
	}

	/**
	 * Removes all the kaleo logs where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	public static void removeByKaleoInstanceId(long kaleoInstanceId) {
		getPersistence().removeByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns the number of kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo logs
	 */
	public static int countByKaleoInstanceId(long kaleoInstanceId) {
		return getPersistence().countByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns all the kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		return getPersistence().findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end) {

		return getPersistence().findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKaleoTaskInstanceTokenId_First(
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoTaskInstanceTokenId_First(
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKaleoTaskInstanceTokenId_First(
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKaleoTaskInstanceTokenId_Last(
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoTaskInstanceTokenId_Last(
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKaleoTaskInstanceTokenId_Last(
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog[] findByKaleoTaskInstanceTokenId_PrevAndNext(
			long kaleoLogId, long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKaleoTaskInstanceTokenId_PrevAndNext(
			kaleoLogId, kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Removes all the kaleo logs where kaleoTaskInstanceTokenId = &#63; from the database.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 */
	public static void removeByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		getPersistence().removeByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns the number of kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the number of matching kaleo logs
	 */
	public static int countByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		return getPersistence().countByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @return the matching kaleo logs
	 */
	public static List<KaleoLog> findByKITI_T(
		long kaleoInstanceTokenId, String type) {

		return getPersistence().findByKITI_T(kaleoInstanceTokenId, type);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKITI_T(
		long kaleoInstanceTokenId, String type, int start, int end) {

		return getPersistence().findByKITI_T(
			kaleoInstanceTokenId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKITI_T(
		long kaleoInstanceTokenId, String type, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().findByKITI_T(
			kaleoInstanceTokenId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKITI_T(
		long kaleoInstanceTokenId, String type, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByKITI_T(
			kaleoInstanceTokenId, type, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKITI_T_First(
			long kaleoInstanceTokenId, String type,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKITI_T_First(
			kaleoInstanceTokenId, type, orderByComparator);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKITI_T_First(
		long kaleoInstanceTokenId, String type,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKITI_T_First(
			kaleoInstanceTokenId, type, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKITI_T_Last(
			long kaleoInstanceTokenId, String type,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKITI_T_Last(
			kaleoInstanceTokenId, type, orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKITI_T_Last(
		long kaleoInstanceTokenId, String type,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKITI_T_Last(
			kaleoInstanceTokenId, type, orderByComparator);
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog[] findByKITI_T_PrevAndNext(
			long kaleoLogId, long kaleoInstanceTokenId, String type,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKITI_T_PrevAndNext(
			kaleoLogId, kaleoInstanceTokenId, type, orderByComparator);
	}

	/**
	 * Removes all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63; from the database.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 */
	public static void removeByKITI_T(long kaleoInstanceTokenId, String type) {
		getPersistence().removeByKITI_T(kaleoInstanceTokenId, type);
	}

	/**
	 * Returns the number of kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @return the number of matching kaleo logs
	 */
	public static int countByKITI_T(long kaleoInstanceTokenId, String type) {
		return getPersistence().countByKITI_T(kaleoInstanceTokenId, type);
	}

	/**
	 * Returns all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @return the matching kaleo logs
	 */
	public static List<KaleoLog> findByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type) {

		return getPersistence().findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, int start, int end) {

		return getPersistence().findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type, start,
			end);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type, start,
			end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	public static List<KaleoLog> findByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKCN_KCPK_KITI_T_First(
			String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
			String type, OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKCN_KCPK_KITI_T_First(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type,
			orderByComparator);
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKCN_KCPK_KITI_T_First(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKCN_KCPK_KITI_T_First(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type,
			orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	public static KaleoLog findByKCN_KCPK_KITI_T_Last(
			String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
			String type, OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKCN_KCPK_KITI_T_Last(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type,
			orderByComparator);
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	public static KaleoLog fetchByKCN_KCPK_KITI_T_Last(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().fetchByKCN_KCPK_KITI_T_Last(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type,
			orderByComparator);
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog[] findByKCN_KCPK_KITI_T_PrevAndNext(
			long kaleoLogId, String kaleoClassName, long kaleoClassPK,
			long kaleoInstanceTokenId, String type,
			OrderByComparator<KaleoLog> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByKCN_KCPK_KITI_T_PrevAndNext(
			kaleoLogId, kaleoClassName, kaleoClassPK, kaleoInstanceTokenId,
			type, orderByComparator);
	}

	/**
	 * Removes all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 */
	public static void removeByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type) {

		getPersistence().removeByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type);
	}

	/**
	 * Returns the number of kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @return the number of matching kaleo logs
	 */
	public static int countByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type) {

		return getPersistence().countByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type);
	}

	/**
	 * Caches the kaleo log in the entity cache if it is enabled.
	 *
	 * @param kaleoLog the kaleo log
	 */
	public static void cacheResult(KaleoLog kaleoLog) {
		getPersistence().cacheResult(kaleoLog);
	}

	/**
	 * Caches the kaleo logs in the entity cache if it is enabled.
	 *
	 * @param kaleoLogs the kaleo logs
	 */
	public static void cacheResult(List<KaleoLog> kaleoLogs) {
		getPersistence().cacheResult(kaleoLogs);
	}

	/**
	 * Creates a new kaleo log with the primary key. Does not add the kaleo log to the database.
	 *
	 * @param kaleoLogId the primary key for the new kaleo log
	 * @return the new kaleo log
	 */
	public static KaleoLog create(long kaleoLogId) {
		return getPersistence().create(kaleoLogId);
	}

	/**
	 * Removes the kaleo log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoLogId the primary key of the kaleo log
	 * @return the kaleo log that was removed
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog remove(long kaleoLogId)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().remove(kaleoLogId);
	}

	public static KaleoLog updateImpl(KaleoLog kaleoLog) {
		return getPersistence().updateImpl(kaleoLog);
	}

	/**
	 * Returns the kaleo log with the primary key or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param kaleoLogId the primary key of the kaleo log
	 * @return the kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog findByPrimaryKey(long kaleoLogId)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchLogException {

		return getPersistence().findByPrimaryKey(kaleoLogId);
	}

	/**
	 * Returns the kaleo log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoLogId the primary key of the kaleo log
	 * @return the kaleo log, or <code>null</code> if a kaleo log with the primary key could not be found
	 */
	public static KaleoLog fetchByPrimaryKey(long kaleoLogId) {
		return getPersistence().fetchByPrimaryKey(kaleoLogId);
	}

	/**
	 * Returns all the kaleo logs.
	 *
	 * @return the kaleo logs
	 */
	public static List<KaleoLog> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the kaleo logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of kaleo logs
	 */
	public static List<KaleoLog> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo logs
	 */
	public static List<KaleoLog> findAll(
		int start, int end, OrderByComparator<KaleoLog> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo logs
	 */
	public static List<KaleoLog> findAll(
		int start, int end, OrderByComparator<KaleoLog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the kaleo logs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of kaleo logs.
	 *
	 * @return the number of kaleo logs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoLogPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(KaleoLogPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile KaleoLogPersistence _persistence;

}