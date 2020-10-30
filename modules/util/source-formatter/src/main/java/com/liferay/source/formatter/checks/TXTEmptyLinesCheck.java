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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class TXTEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("find-security-bugs-false-positives.txt") &&
			!absolutePath.matches(
				".*\\/modules\\/third-party\\/com-h3xstream-findsecbugs\\/" +
					"src\\/main\\/resources\\/liferay-config\\/\\.txt")) {

			return content;
		}

		return StringUtil.replace(content, "\n\n", "\n");
	}

}