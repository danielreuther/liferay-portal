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

package com.liferay.portal.vulcan.internal.template;

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.internal.template.servlet.HeadlessHttpClientHttpRequestWrapper;
import com.liferay.portal.vulcan.internal.template.servlet.HeadlessHttpClientHttpResponseWrapper;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "type=" + TemplateContextContributor.TYPE_GLOBAL,
	service = TemplateContextContributor.class
)
public class HeadlessHttpClientTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)contextObjects.get(
			"themeDisplay");

		contextObjects.put(
			"headlessHttpClient",
			new HeadlessHttpClient(
				httpServletRequest, themeDisplay.getResponse()));
	}

	public class HeadlessHttpClient {

		public HeadlessHttpClient(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			_httpServletRequest = httpServletRequest;
			_httpServletResponse = httpServletResponse;
		}

		public Object get(String path) throws Exception {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			ServletContext servletContext = ServletContextPool.get(
				StringPool.BLANK);

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(Portal.PATH_MODULE + path);

			requestDispatcher.forward(
				new HeadlessHttpClientHttpRequestWrapper(_httpServletRequest),
				new HeadlessHttpClientHttpResponseWrapper(
					new PipingServletResponse(
						_httpServletResponse, unsyncStringWriter)));

			return JSONFactoryUtil.looseDeserialize(
				unsyncStringWriter.toString());
		}

		private final HttpServletRequest _httpServletRequest;
		private final HttpServletResponse _httpServletResponse;

	}

}