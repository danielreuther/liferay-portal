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

package com.liferay.asset.model;

import com.liferay.asset.kernel.NoSuchClassTypeFieldException;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Adolfo Pérez
 */
public class DDMStructureClassType implements ClassType {

	public DDMStructureClassType(
		long classTypeId, String classTypeName, String languageId) {

		_classTypeId = classTypeId;
		_classTypeName = classTypeName;
		_languageId = languageId;
	}

	@Override
	public ClassTypeField getClassTypeField(String fieldName)
		throws PortalException {

		for (ClassTypeField classTypeField : getClassTypeFields()) {
			if (fieldName.equals(classTypeField.getName())) {
				return classTypeField;
			}
		}

		throw new NoSuchClassTypeFieldException();
	}

	@Override
	public List<ClassTypeField> getClassTypeFields() throws PortalException {
		return getClassTypeFields(getClassTypeId());
	}

	@Override
	public List<ClassTypeField> getClassTypeFields(int start, int end)
		throws PortalException {

		return ListUtil.subList(getClassTypeFields(), start, end);
	}

	@Override
	public int getClassTypeFieldsCount() throws PortalException {
		return getClassTypeFields().size();
	}

	@Override
	public long getClassTypeId() {
		return _classTypeId;
	}

	@Override
	public String getName() {
		return _classTypeName;
	}

	protected List<ClassTypeField> getClassTypeFields(long ddmStructureId)
		throws PortalException {

		List<ClassTypeField> classTypeFields = new ArrayList<>();

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.getDDMStructure(ddmStructureId);

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			String indexType = ddmFormField.getIndexType();

			String type = ddmFormField.getType();

			if (Validator.isNull(indexType) ||
				Objects.equals(indexType, "none") ||
				!ArrayUtil.contains(_SELECTABLE_DDM_STRUCTURE_FIELDS, type)) {

				continue;
			}

			LocalizedValue label = ddmFormField.getLabel();

			classTypeFields.add(
				new ClassTypeField(
					ddmStructure.getStructureId(),
					ddmFormField.getFieldReference(),
					label.getString(LocaleUtil.fromLanguageId(_languageId)),
					ddmFormField.getName(), type));
		}

		return classTypeFields;
	}

	private static final String[] _SELECTABLE_DDM_STRUCTURE_FIELDS = {
		"checkbox", "checkbox_multiple", "date", "ddm-date", "ddm-decimal",
		"ddm-image", "ddm-integer", "ddm-number", "ddm-text-html", "image",
		"numeric", "radio", "rich_text", "select", "text", "textarea"
	};

	private final long _classTypeId;
	private final String _classTypeName;
	private final String _languageId;

}