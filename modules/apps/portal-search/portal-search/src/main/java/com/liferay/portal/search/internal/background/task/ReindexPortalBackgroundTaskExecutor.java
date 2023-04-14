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

package com.liferay.portal.search.internal.background.task;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.internal.SearchEngineInitializer;

import org.osgi.framework.BundleContext;

/**
 * @author Andrew Betts
 */
public class ReindexPortalBackgroundTaskExecutor
	extends BaseReindexBackgroundTaskExecutor {

	public ReindexPortalBackgroundTaskExecutor(
		ConcurrentReindexManager concurrentReindexManager,
		BundleContext bundleContext,
		PortalExecutorManager portalExecutorManager) {

		_concurrentReindexManager = concurrentReindexManager;
		_bundleContext = bundleContext;
		_portalExecutorManager = portalExecutorManager;
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return new ReindexPortalBackgroundTaskExecutor(
			_concurrentReindexManager, _bundleContext, _portalExecutorManager);
	}

	@Override
	protected void reindex(
			String className, long[] companyIds, String executionMode)
		throws Exception {

		for (long companyId : companyIds) {
			ReindexStatusMessageSenderUtil.sendStatusMessage(
				ReindexBackgroundTaskConstants.PORTAL_START, companyId,
				companyIds);

			if (_log.isInfoEnabled()) {
				_log.info("Start reindexing company " + companyId);
			}

			try {
				SearchEngineInitializer searchEngineInitializer =
					new SearchEngineInitializer(
						_concurrentReindexManager, _bundleContext, companyId,
						executionMode, _portalExecutorManager);

				searchEngineInitializer.reindex();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
			finally {
				ReindexStatusMessageSenderUtil.sendStatusMessage(
					ReindexBackgroundTaskConstants.PORTAL_END, companyId,
					companyIds);

				if (_log.isInfoEnabled()) {
					_log.info("Finished reindexing company " + companyId);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexPortalBackgroundTaskExecutor.class);

	private final ConcurrentReindexManager _concurrentReindexManager;
	private final BundleContext _bundleContext;
	private final PortalExecutorManager _portalExecutorManager;

}