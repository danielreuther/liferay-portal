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

package com.liferay.commerce.order.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class CommerceOrderItemDetailsCustomFieldsScreenNavigationEntry
	extends CommerceOrderItemDetailsCustomFieldsScreenNavigationCategory
	implements ScreenNavigationEntry<CommerceOrderItem> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public boolean isVisible(User user, CommerceOrderItem commerceOrderItem) {
		boolean hasCustomAttributesAvailable = false;

		try {
			hasCustomAttributesAvailable =
				CustomAttributesUtil.hasCustomAttributes(
					user.getCompanyId(), CommerceOrderItem.class.getName(),
					commerceOrderItem.getCommerceOrderItemId(), null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return hasCustomAttributesAvailable;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/commerce_order_item/custom_fields.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemDetailsCustomFieldsScreenNavigationEntry.class);

	@Reference
	private JSPRenderer _jspRenderer;

}