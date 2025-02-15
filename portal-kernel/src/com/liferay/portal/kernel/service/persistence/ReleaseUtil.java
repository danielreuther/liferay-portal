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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the release service. This utility wraps <code>com.liferay.portal.service.persistence.impl.ReleasePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReleasePersistence
 * @generated
 */
public class ReleaseUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Release release) {
		getPersistence().clearCache(release);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Release> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Release> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Release> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Release> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Release> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Release update(Release release) {
		return getPersistence().update(release);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Release update(
		Release release, ServiceContext serviceContext) {

		return getPersistence().update(release, serviceContext);
	}

	/**
	 * Returns the release where servletContextName = &#63; or throws a <code>NoSuchReleaseException</code> if it could not be found.
	 *
	 * @param servletContextName the servlet context name
	 * @return the matching release
	 * @throws NoSuchReleaseException if a matching release could not be found
	 */
	public static Release findByServletContextName(String servletContextName)
		throws com.liferay.portal.kernel.exception.NoSuchReleaseException {

		return getPersistence().findByServletContextName(servletContextName);
	}

	/**
	 * Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param servletContextName the servlet context name
	 * @return the matching release, or <code>null</code> if a matching release could not be found
	 */
	public static Release fetchByServletContextName(String servletContextName) {
		return getPersistence().fetchByServletContextName(servletContextName);
	}

	/**
	 * Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param servletContextName the servlet context name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching release, or <code>null</code> if a matching release could not be found
	 */
	public static Release fetchByServletContextName(
		String servletContextName, boolean useFinderCache) {

		return getPersistence().fetchByServletContextName(
			servletContextName, useFinderCache);
	}

	/**
	 * Removes the release where servletContextName = &#63; from the database.
	 *
	 * @param servletContextName the servlet context name
	 * @return the release that was removed
	 */
	public static Release removeByServletContextName(String servletContextName)
		throws com.liferay.portal.kernel.exception.NoSuchReleaseException {

		return getPersistence().removeByServletContextName(servletContextName);
	}

	/**
	 * Returns the number of releases where servletContextName = &#63;.
	 *
	 * @param servletContextName the servlet context name
	 * @return the number of matching releases
	 */
	public static int countByServletContextName(String servletContextName) {
		return getPersistence().countByServletContextName(servletContextName);
	}

	/**
	 * Caches the release in the entity cache if it is enabled.
	 *
	 * @param release the release
	 */
	public static void cacheResult(Release release) {
		getPersistence().cacheResult(release);
	}

	/**
	 * Caches the releases in the entity cache if it is enabled.
	 *
	 * @param releases the releases
	 */
	public static void cacheResult(List<Release> releases) {
		getPersistence().cacheResult(releases);
	}

	/**
	 * Creates a new release with the primary key. Does not add the release to the database.
	 *
	 * @param releaseId the primary key for the new release
	 * @return the new release
	 */
	public static Release create(long releaseId) {
		return getPersistence().create(releaseId);
	}

	/**
	 * Removes the release with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release that was removed
	 * @throws NoSuchReleaseException if a release with the primary key could not be found
	 */
	public static Release remove(long releaseId)
		throws com.liferay.portal.kernel.exception.NoSuchReleaseException {

		return getPersistence().remove(releaseId);
	}

	public static Release updateImpl(Release release) {
		return getPersistence().updateImpl(release);
	}

	/**
	 * Returns the release with the primary key or throws a <code>NoSuchReleaseException</code> if it could not be found.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release
	 * @throws NoSuchReleaseException if a release with the primary key could not be found
	 */
	public static Release findByPrimaryKey(long releaseId)
		throws com.liferay.portal.kernel.exception.NoSuchReleaseException {

		return getPersistence().findByPrimaryKey(releaseId);
	}

	/**
	 * Returns the release with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release, or <code>null</code> if a release with the primary key could not be found
	 */
	public static Release fetchByPrimaryKey(long releaseId) {
		return getPersistence().fetchByPrimaryKey(releaseId);
	}

	/**
	 * Returns all the releases.
	 *
	 * @return the releases
	 */
	public static List<Release> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReleaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @return the range of releases
	 */
	public static List<Release> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReleaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of releases
	 */
	public static List<Release> findAll(
		int start, int end, OrderByComparator<Release> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReleaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of releases
	 */
	public static List<Release> findAll(
		int start, int end, OrderByComparator<Release> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the releases from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of releases.
	 *
	 * @return the number of releases
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ReleasePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ReleasePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ReleasePersistence _persistence;

}