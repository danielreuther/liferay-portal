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

package com.liferay.portal.vulcan.extension.validation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.vulcan.extension.PropertyDefinition;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Map;
import java.util.Set;

import javax.validation.ValidationException;

/**
 * @author Carlos Correa
 */
public class DefaultPropertyValidator implements PropertyValidator {

	@Override
	public void validate(
		PropertyDefinition propertyDefinition, Object propertyValue) {

		boolean valid = false;

		Set<Class<?>> classes = propertyDefinition.getPropertyClasses();

		PropertyDefinition.PropertyType propertyType =
			propertyDefinition.getPropertyType();

		if (propertyType == PropertyDefinition.PropertyType.DATE_TIME) {
			if (propertyValue instanceof String) {
				try {
					LocalDateTime.parse(
						(String)propertyValue,
						DateTimeFormatter.ofPattern(
							_getDateTimePattern((String)propertyValue)));

					valid = true;
				}
				catch (DateTimeParseException dateTimeParseException) {
					if (_log.isDebugEnabled()) {
						_log.debug(dateTimeParseException);
					}
				}
			}
		}
		else if (propertyType ==
					PropertyDefinition.PropertyType.MULTIPLE_ELEMENT) {

			Class<?> propertyValueClass = propertyValue.getClass();

			if ((classes != null) && propertyValueClass.isArray()) {
				valid = true;

				for (Object object : (Object[])propertyValue) {
					if (!_isReadable(classes, object)) {
						valid = false;

						break;
					}
				}
			}
		}
		else if (propertyType ==
					PropertyDefinition.PropertyType.SINGLE_ELEMENT) {

			if ((classes != null) && (propertyValue instanceof Map) &&
				_isReadable(classes, propertyValue)) {

				valid = true;
			}
		}
		else if (classes != null) {
			for (Class<?> clazz : classes) {
				if (clazz.isInstance(propertyValue)) {
					valid = true;

					break;
				}
			}
		}

		if (!valid) {
			throw new ValidationException(
				StringBundler.concat(
					"The property name \"",
					propertyDefinition.getPropertyName(),
					"\" is invalid for property type ", propertyType));
		}
	}

	private String _getDateTimePattern(String value) {
		if (value.length() == 16) {
			return "yyyy-MM-dd HH:mm";
		}
		else if (value.length() == 20) {
			return "yyyy-MM-dd'T'HH:mm:ss'Z'";
		}
		else if (value.length() == 21) {
			return "yyyy-MM-dd HH:mm:ss.S";
		}
		else if ((value.length() == 23) && (value.charAt(10) == 'T')) {
			return "yyyy-MM-dd'T'HH:mm:ss.SSS";
		}
		else if ((value.length() == 24) && (value.charAt(10) == 'T')) {
			return "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		}

		return DateUtil.ISO_8601_PATTERN;
	}

	private boolean _isReadable(Set<Class<?>> classes, Object object) {
		for (Class<?> clazz : classes) {
			if (ObjectMapperUtil.readValue(clazz, object) != null) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultPropertyValidator.class);

}