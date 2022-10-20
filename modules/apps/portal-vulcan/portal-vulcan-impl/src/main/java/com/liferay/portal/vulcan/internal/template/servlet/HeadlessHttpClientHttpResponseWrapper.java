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

package com.liferay.portal.vulcan.internal.template.servlet;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Alejandro Tardín
 */
public class HeadlessHttpClientHttpResponseWrapper
	extends HttpServletResponseWrapper {

	public HeadlessHttpClientHttpResponseWrapper(
		HttpServletResponse httpServletResponse) {

		super(httpServletResponse);
	}

	@Override
	public void addCookie(Cookie cookie) {
	}

	@Override
	public void addDateHeader(String name, long date) {
	}

	@Override
	public void addHeader(String name, String value) {
	}

	@Override
	public void addIntHeader(String name, int value) {
	}

	@Override
	public void flushBuffer() {
	}

	@Override
	public void reset() {
	}

	@Override
	public void resetBuffer() {
	}

	@Override
	public void sendError(int sc) {
	}

	@Override
	public void sendError(int sc, String msg) {
	}

	@Override
	public void sendRedirect(String location) {
	}

	@Override
	public void setBufferSize(int size) {
	}

	@Override
	public void setCharacterEncoding(String charset) {
	}

	@Override
	public void setContentLength(int len) {
	}

	@Override
	public void setContentLengthLong(long length) {
	}

	@Override
	public void setContentType(String type) {
	}

	@Override
	public void setDateHeader(String name, long date) {
	}

	@Override
	public void setHeader(String name, String value) {
	}

	@Override
	public void setIntHeader(String name, int value) {
	}

	@Override
	public void setLocale(Locale loc) {
	}

	@Override
	public void setStatus(int sc) {
	}

	@Override
	public void setStatus(int sc, String sm) {
	}

}