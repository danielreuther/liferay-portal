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

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

import java.net.URL;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Joseph Shum
 */
@ProviderType
public interface PortalCacheManager<K extends Serializable, V> {

	public static final String PORTAL_CACHE_MANAGER_NAME =
		"portal.cache.manager.name";

	public void clearAll() throws PortalCacheException;

	public void destroy();

	public PortalCache<K, V> fetchPortalCache(String portalCacheName);

	public PortalCache<K, V> getPortalCache(String portalCacheName)
		throws PortalCacheException;

	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean mvcc)
		throws PortalCacheException;

	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean mvcc, boolean sharded)
		throws PortalCacheException;

	public Set<PortalCacheManagerListener> getPortalCacheManagerListeners();

	public String getPortalCacheManagerName();

	public void reconfigurePortalCaches(
		URL configurationURL, ClassLoader classLoader);

	public boolean registerPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener);

	public void removePortalCache(String portalCacheName);

	public void removePortalCaches(long companyId);

	public boolean unregisterPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener);

	public void unregisterPortalCacheManagerListeners();

}