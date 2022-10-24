/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.avalara.tax.engine.fixed.web.internal.display.context.util;

import com.liferay.portal.kernel.display.context.helper.BaseRequestHelper;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderRequest;

/**
 * @author Marco Leo
 */
public class CommerceAvalaraTaxRateRequestHelper extends BaseRequestHelper {

	public CommerceAvalaraTaxRateRequestHelper(RenderRequest renderRequest) {
		super(PortalUtil.getHttpServletRequest(renderRequest));
	}

}