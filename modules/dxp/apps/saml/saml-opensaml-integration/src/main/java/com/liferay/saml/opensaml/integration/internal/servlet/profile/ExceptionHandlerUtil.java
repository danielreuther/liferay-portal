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

package com.liferay.saml.opensaml.integration.internal.servlet.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.saml.runtime.SamlException;

/**
 * @author Michael C. Han
 */
public class ExceptionHandlerUtil {

	public static void handleException(Exception exception)
		throws PortalException {

		if (exception instanceof PortalException) {
			throw (PortalException)exception;
		}
		else if (exception instanceof SystemException) {
			throw (SystemException)exception;
		}

		throw new SamlException(exception);
	}

}