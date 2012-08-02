/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class AxisServlet extends com.liferay.util.axis.AxisServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext servletContext = servletConfig.getServletContext();

		_pluginClassLoader = (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		if (_pluginClassLoader == null) {
			super.init(servletConfig);
		}
		else {
			ClassLoader contextClassLoader =
				PACLClassLoaderUtil.getContextClassLoader();

			try {
				PACLClassLoaderUtil.setContextClassLoader(_pluginClassLoader);

				super.init(servletConfig);
			}
			finally {
				PACLClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			if (_pluginClassLoader == null) {
				super.service(request, response);
			}
			else {
				ClassLoader contextClassLoader =
					PACLClassLoaderUtil.getContextClassLoader();

				try {
					PACLClassLoaderUtil.setContextClassLoader(
						_pluginClassLoader);

					super.service(request, response);
				}
				finally {
					PACLClassLoaderUtil.setContextClassLoader(
						contextClassLoader);
				}
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (ServletException se) {
			throw se;
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private ClassLoader _pluginClassLoader;

}