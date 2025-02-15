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

package com.liferay.data.engine.rest.internal.storage;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistry;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.storage.util.DataStorageUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordExporter {

	public DataRecordExporter(
		DataDefinitionContentTypeRegistry dataDefinitionContentTypeRegistry,
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		SPIDDMFormRuleConverter spiDDMFormRuleConverter) {

		_dataDefinitionContentTypeRegistry = dataDefinitionContentTypeRegistry;
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmFormFieldTypeServicesRegistry = ddmFormFieldTypeServicesRegistry;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_spiDDMFormRuleConverter = spiDDMFormRuleConverter;
	}

	public String export(List<DataRecord> dataRecords) throws Exception {
		if (ListUtil.isEmpty(dataRecords)) {
			return StringPool.BLANK;
		}

		DataRecord dataRecord = dataRecords.get(0);

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecord.getDataRecordCollectionId());

		DataDefinition dataDefinition = DataDefinitionUtil.toDataDefinition(
			_dataDefinitionContentTypeRegistry,
			_ddmFormFieldTypeServicesRegistry, ddlRecordSet.getDDMStructure(),
			_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DataRecord record : dataRecords) {
			jsonArray.put(_toJSON(dataDefinition, record));
		}

		return jsonArray.toString();
	}

	private String _toJSON(
		DataDefinition dataDefinition, DataRecord dataRecord) {

		try {
			return DataStorageUtil.toJSON(
				dataDefinition, dataRecord.getDataRecordValues());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataRecordExporter.class);

	private final DataDefinitionContentTypeRegistry
		_dataDefinitionContentTypeRegistry;
	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDMFormFieldTypeServicesRegistry
		_ddmFormFieldTypeServicesRegistry;
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final SPIDDMFormRuleConverter _spiDDMFormRuleConverter;

}