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

package com.liferay.dynamic.data.mapping.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceRecordVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
	service = {
		DDMFormInstanceRecordStagedModelRepository.class,
		StagedModelRepository.class
	}
)
public class DDMFormInstanceRecordStagedModelRepository
	implements StagedModelRepository<DDMFormInstanceRecord> {

	@Override
	public DDMFormInstanceRecord addStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public DDMFormInstanceRecord addStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstanceRecord ddmFormInstanceRecord,
			DDMFormValues ddmFormValues)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			ddmFormInstanceRecord.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddmFormInstanceRecord);

		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(ddmFormInstanceRecord.getUuid());
		}

		DDMFormInstanceRecord importedDDMFormInstanceRecord =
			_ddmFormInstanceRecordLocalService.addFormInstanceRecord(
				userId, ddmFormInstanceRecord.getGroupId(),
				ddmFormInstanceRecord.getFormInstanceId(), ddmFormValues,
				serviceContext);

		_updateVersions(
			importedDDMFormInstanceRecord, ddmFormInstanceRecord.getVersion());

		return importedDDMFormInstanceRecord;
	}

	@Override
	public void deleteStagedModel(DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		_ddmFormInstanceRecordLocalService.deleteFormInstanceRecord(
			ddmFormInstanceRecord);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDMFormInstanceRecord ddlRrecord = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ddlRrecord != null) {
			deleteStagedModel(ddlRrecord);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public DDMFormInstanceRecord fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddmFormInstanceRecordLocalService.
			fetchDDMFormInstanceRecordByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DDMFormInstanceRecord> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _ddmFormInstanceRecordLocalService.
			getDDMFormInstanceRecordsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DDMFormInstanceRecord>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmFormInstanceRecordLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property formInstanceRecordIdProperty =
					PropertyFactoryUtil.forName("formInstanceRecordId");

				DynamicQuery formInstanceRecordVersionDynamicQuery =
					_getRecordVersionDynamicQuery();

				dynamicQuery.add(
					formInstanceRecordIdProperty.in(
						formInstanceRecordVersionDynamicQuery));

				Property formInstanceIdProperty = PropertyFactoryUtil.forName(
					"formInstanceId");

				dynamicQuery.add(
					formInstanceIdProperty.in(_getFormInstanceDynamicQuery()));
			});

		return exportActionableDynamicQuery;
	}

	@Override
	public DDMFormInstanceRecord getStagedModel(long formInstanceRecordId)
		throws PortalException {

		return _ddmFormInstanceRecordLocalService.getDDMFormInstanceRecord(
			formInstanceRecordId);
	}

	@Override
	public DDMFormInstanceRecord saveStagedModel(
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		return _ddmFormInstanceRecordLocalService.updateDDMFormInstanceRecord(
			ddmFormInstanceRecord);
	}

	@Override
	public DDMFormInstanceRecord updateStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public DDMFormInstanceRecord updateStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstanceRecord ddmFormInstanceRecord,
			DDMFormValues ddmFormValues)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			ddmFormInstanceRecord.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddmFormInstanceRecord);

		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);

		DDMFormInstanceRecord importedDDMFormInstanceRecord =
			_ddmFormInstanceRecordLocalService.updateFormInstanceRecord(
				userId, ddmFormInstanceRecord.getFormInstanceRecordId(), false,
				ddmFormValues, serviceContext);

		_updateVersions(
			importedDDMFormInstanceRecord, ddmFormInstanceRecord.getVersion());

		return importedDDMFormInstanceRecord;
	}

	private DynamicQuery _getFormInstanceDynamicQuery() {
		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				DDMFormInstanceRecord.class.getName());

		Class<?> clazz = stagedModelDataHandler.getClass();

		DynamicQuery formInstanceDynamicQuery =
			DynamicQueryFactoryUtil.forClass(
				DDMFormInstance.class, "formInstance", clazz.getClassLoader());

		formInstanceDynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formInstanceId"));

		formInstanceDynamicQuery.add(
			RestrictionsFactoryUtil.eqProperty(
				"formInstance.formInstanceId", "formInstanceId"));

		return formInstanceDynamicQuery;
	}

	private DynamicQuery _getRecordVersionDynamicQuery() {
		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				DDMFormInstanceRecord.class.getName());

		Class<?> clazz = stagedModelDataHandler.getClass();

		DynamicQuery formInstanceRecordVersionDynamicQuery =
			DynamicQueryFactoryUtil.forClass(
				DDMFormInstanceRecordVersion.class, "formInstanceRecordVersion",
				clazz.getClassLoader());

		formInstanceRecordVersionDynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formInstanceRecordId"));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		formInstanceRecordVersionDynamicQuery.add(
			statusProperty.in(stagedModelDataHandler.getExportableStatuses()));

		formInstanceRecordVersionDynamicQuery.add(
			RestrictionsFactoryUtil.eqProperty(
				"formInstanceRecordVersion.version", "version"));
		formInstanceRecordVersionDynamicQuery.add(
			RestrictionsFactoryUtil.eqProperty(
				"formInstanceRecordVersion.formInstanceRecordId",
				"formInstanceRecordId"));

		return formInstanceRecordVersionDynamicQuery;
	}

	private void _updateVersions(
			DDMFormInstanceRecord importedDDMFormInstanceRecord, String version)
		throws PortalException {

		if (Objects.equals(
				importedDDMFormInstanceRecord.getVersion(), version)) {

			return;
		}

		try {
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				_ddmFormInstanceRecordVersionLocalService.
					getFormInstanceRecordVersion(
						importedDDMFormInstanceRecord.getFormInstanceRecordId(),
						version);

			_ddmFormInstanceRecordVersionLocalService.
				deleteDDMFormInstanceRecordVersion(
					ddmFormInstanceRecordVersion);
		}
		catch (NoSuchFormInstanceRecordVersionException
					noSuchFormInstanceRecordVersionException) {

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFormInstanceRecordVersionException);
			}
		}

		DDMFormInstanceRecordVersion importedDDMFormInstanceRecordVersion =
			importedDDMFormInstanceRecord.getFormInstanceRecordVersion();

		importedDDMFormInstanceRecordVersion.setVersion(version);

		_ddmFormInstanceRecordVersionLocalService.
			updateDDMFormInstanceRecordVersion(
				importedDDMFormInstanceRecordVersion);

		importedDDMFormInstanceRecord.setVersion(version);

		_ddmFormInstanceRecordLocalService.updateDDMFormInstanceRecord(
			importedDDMFormInstanceRecord);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordStagedModelRepository.class);

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

}