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

package com.liferay.segments.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.exception.LockedSegmentsExperimentException;
import com.liferay.segments.exception.RequiredSegmentsExperienceException;
import com.liferay.segments.exception.SegmentsExperienceNameException;
import com.liferay.segments.exception.SegmentsExperiencePriorityException;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.base.SegmentsExperienceLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsExperience",
	service = AopService.class
)
public class SegmentsExperienceLocalServiceImpl
	extends SegmentsExperienceLocalServiceBaseImpl {

	@Override
	public SegmentsExperience addDefaultSegmentsExperience(
			long userId, long plid, ServiceContext serviceContext)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(plid);

		return addSegmentsExperience(
			userId, layout.getGroupId(), SegmentsEntryConstants.ID_DEFAULT,
			SegmentsExperienceConstants.KEY_DEFAULT,
			classNameLocalService.getClassNameId(Layout.class),
			layout.getPlid(),
			Collections.singletonMap(
				LocaleUtil.getSiteDefault(),
				LanguageUtil.get(
					LocaleUtil.getSiteDefault(), "default-experience-name")),
			0, true, new UnicodeProperties(true), serviceContext);
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long userId, long groupId, long segmentsEntryId, long classNameId,
			long classPK, Map<Locale, String> nameMap, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		int lowestPriority = _getLowestPriority(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));

		return addSegmentsExperience(
			userId, groupId, segmentsEntryId, classNameId, classPK, nameMap,
			lowestPriority - 1, active, typeSettingsUnicodeProperties,
			serviceContext);
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long userId, long groupId, long segmentsEntryId, long classNameId,
			long classPK, Map<Locale, String> nameMap, int priority,
			boolean active, UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		return addSegmentsExperience(
			userId, groupId, segmentsEntryId,
			String.valueOf(counterLocalService.increment()), classNameId,
			classPK, nameMap, priority, active, typeSettingsUnicodeProperties,
			serviceContext);
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long userId, long groupId, long segmentsEntryId,
			String segmentsExperienceKey, long classNameId, long classPK,
			Map<Locale, String> nameMap, int priority, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		// Segments experience

		User user = userLocalService.getUser(userId);

		long publishedClassPK = _getPublishedLayoutClassPK(classPK);

		_validateName(nameMap);
		_validatePriority(groupId, classNameId, publishedClassPK, priority);

		long segmentsExperienceId = counterLocalService.increment();

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.create(segmentsExperienceId);

		segmentsExperience.setUuid(serviceContext.getUuid());
		segmentsExperience.setGroupId(groupId);
		segmentsExperience.setCompanyId(user.getCompanyId());
		segmentsExperience.setUserId(user.getUserId());
		segmentsExperience.setUserName(user.getFullName());
		segmentsExperience.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsExperience.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsExperience.setSegmentsEntryId(segmentsEntryId);
		segmentsExperience.setSegmentsExperienceKey(segmentsExperienceKey);
		segmentsExperience.setClassNameId(classNameId);
		segmentsExperience.setClassPK(publishedClassPK);
		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setPriority(priority);
		segmentsExperience.setActive(active);
		segmentsExperience.setTypeSettingsUnicodeProperties(
			typeSettingsUnicodeProperties);

		segmentsExperience = segmentsExperiencePersistence.update(
			segmentsExperience);

		// Resources

		resourceLocalService.addModelResources(
			segmentsExperience, serviceContext);

		return segmentsExperience;
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long userId, long groupId, long segmentsEntryId, long classNameId,
			long classPK, Map<Locale, String> nameMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return appendSegmentsExperience(
			userId, groupId, segmentsEntryId, classNameId, classPK, nameMap,
			active, new UnicodeProperties(true), serviceContext);
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long userId, long groupId, long segmentsEntryId, long classNameId,
			long classPK, Map<Locale, String> nameMap, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		int highestPriority = _getHighestPriority(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));

		return addSegmentsExperience(
			userId, groupId, segmentsEntryId, classNameId, classPK, nameMap,
			highestPriority + 1, active, typeSettingsUnicodeProperties,
			serviceContext);
	}

	@Override
	public void deleteSegmentsEntrySegmentsExperiences(long segmentsEntryId)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findBySegmentsEntryId(
				segmentsEntryId);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience);
		}
	}

	@Override
	public SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		return segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsExperience deleteSegmentsExperience(
			SegmentsExperience segmentsExperience)
		throws PortalException {

		// Segments experience

		if (!GroupThreadLocal.isDeleteInProcess() &&
			segmentsExperience.hasSegmentsExperiment()) {

			throw new RequiredSegmentsExperienceException.
				MustNotDeleteSegmentsExperienceReferencedBySegmentsExperiments(
					segmentsExperience.getSegmentsExperienceId());
		}

		segmentsExperiencePersistence.remove(segmentsExperience);

		segmentsExperiencePersistence.flush();

		// Segments experiences priorities

		if (!GroupThreadLocal.isDeleteInProcess()) {
			_updateSegmentExperiencesPriority(segmentsExperience);
		}

		// Segments experiments

		for (SegmentsExperiment segmentsExperiment :
				segmentsExperimentPersistence.findByS_C_C(
					segmentsExperience.getSegmentsExperienceId(),
					segmentsExperience.getClassNameId(),
					_getPublishedLayoutClassPK(
						segmentsExperience.getClassPK()))) {

			_deleteSegmentsExperiment(segmentsExperiment);
		}

		// Resources

		resourceLocalService.deleteResource(
			segmentsExperience, ResourceConstants.SCOPE_INDIVIDUAL);

		return segmentsExperience;
	}

	@Override
	public void deleteSegmentsExperiences(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		// Segments experiments

		SegmentsExperience defaultSegmentsExperience = fetchSegmentsExperience(
			groupId, SegmentsExperienceConstants.KEY_DEFAULT,
			classNameLocalService.getClassNameId(Layout.class), classPK);

		if (defaultSegmentsExperience != null) {
			for (SegmentsExperiment segmentsExperiment :
					segmentsExperimentPersistence.findByS_C_C(
						defaultSegmentsExperience.getSegmentsExperienceId(),
						classNameId, _getPublishedLayoutClassPK(classPK))) {

				_deleteSegmentsExperiment(segmentsExperiment);
			}
		}

		// Segments experiences

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findByG_C_C(
				groupId, classNameId, _getPublishedLayoutClassPK(classPK));

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience);
		}
	}

	@Override
	public long fetchDefaultSegmentsExperienceId(long plid) {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return SegmentsExperienceConstants.ID_DEFAULT;
		}

		SegmentsExperience segmentsExperience = fetchSegmentsExperience(
			layout.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
			classNameLocalService.getClassNameId(Layout.class), plid);

		if (segmentsExperience == null) {
			return SegmentsExperienceConstants.ID_DEFAULT;
		}

		return segmentsExperience.getSegmentsExperienceId();
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long segmentsExperienceId) {

		return segmentsExperiencePersistence.fetchByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, long classNameId, long classPK, int priority) {

		return segmentsExperiencePersistence.fetchByG_C_C_P(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK),
			priority);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, String segmentsExperienceKey, long classNameId,
		long classPK) {

		return segmentsExperiencePersistence.fetchByG_SEK_C_C(
			groupId, segmentsExperienceKey, classNameId,
			_getPublishedLayoutClassPK(classPK));
	}

	@Override
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws PortalException {

		return segmentsExperiencePersistence.findByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience getSegmentsExperience(
			long groupId, String segmentsExperienceKey, long classNameId,
			long classPK)
		throws PortalException {

		return segmentsExperiencePersistence.findByG_SEK_C_C(
			groupId, segmentsExperienceKey, classNameId,
			_getPublishedLayoutClassPK(classPK));
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long classNameId, long classPK) {

		return segmentsExperiencePersistence.findByG_C_C(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active)
		throws PortalException {

		return segmentsExperiencePersistence.findByG_C_C_A(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK), active);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator) {

		return segmentsExperiencePersistence.findByG_C_C_A(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK), active,
			start, end, orderByComparator);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active) {

		return segmentsExperiencePersistence.findByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId,
			_getPublishedLayoutClassPK(classPK), active);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return segmentsExperiencePersistence.findByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId,
			_getPublishedLayoutClassPK(classPK), active, start, end,
			orderByComparator);
	}

	@Override
	public int getSegmentsExperiencesCount(
		long groupId, long classNameId, long classPK) {

		return segmentsExperiencePersistence.countByG_C_C(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));
	}

	@Override
	public int getSegmentsExperiencesCount(
		long groupId, long classNameId, long classPK, boolean active) {

		return segmentsExperiencePersistence.countByG_C_C_A(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK), active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<Locale, String> nameMap, boolean active)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		return updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active,
			segmentsExperience.getTypeSettingsUnicodeProperties());
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<Locale, String> nameMap, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws PortalException {

		_validateName(nameMap);

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		if (segmentsExperience.hasSegmentsExperiment()) {
			throw new LockedSegmentsExperimentException(
				"Segments experience " + segmentsExperienceId +
					" has a locked segments experiment");
		}

		segmentsExperience.setSegmentsEntryId(segmentsEntryId);
		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setActive(active);
		segmentsExperience.setTypeSettingsUnicodeProperties(
			typeSettingsUnicodeProperties);

		return segmentsExperiencePersistence.update(segmentsExperience);
	}

	@Override
	public SegmentsExperience updateSegmentsExperienceActive(
			long segmentsExperienceId, boolean active)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		segmentsExperience.setActive(active);

		return segmentsExperiencePersistence.update(segmentsExperience);
	}

	@Override
	public SegmentsExperience updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		if (segmentsExperience.hasSegmentsExperiment()) {
			throw new LockedSegmentsExperimentException(
				"Segments experience " + segmentsExperienceId +
					" has a locked segments experiment");
		}

		int count = segmentsExperiencePersistence.countByG_C_C(
			segmentsExperience.getGroupId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK());

		if (newPriority >= count) {
			return segmentsExperience;
		}

		int lowestPriority = _getLowestPriority(
			segmentsExperience.getGroupId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK());

		return _swapSegmentsExperience(
			lowestPriority, newPriority, segmentsExperience);
	}

	private void _deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		segmentsExperimentPersistence.remove(segmentsExperiment);

		resourceLocalService.deleteResource(
			segmentsExperiment, ResourceConstants.SCOPE_INDIVIDUAL);

		segmentsExperimentRelPersistence.removeBySegmentsExperimentId(
			segmentsExperiment.getSegmentsExperimentId());
	}

	private int _getHighestPriority(
		long groupId, long classNameId, long classPK) {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_C_C_First(
				groupId, classNameId, classPK, null);

		return segmentsExperience.getPriority();
	}

	private int _getLowestPriority(
		long groupId, long classNameId, long classPK) {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_C_C_Last(
				groupId, classNameId, classPK, null);

		return segmentsExperience.getPriority();
	}

	private long _getPublishedLayoutClassPK(long classPK) {
		Layout layout = _layoutLocalService.fetchLayout(classPK);

		if ((layout != null) && layout.isDraftLayout()) {
			return layout.getClassPK();
		}

		return classPK;
	}

	private SegmentsExperience _swapSegmentsExperience(
		int lowestPriority, int priority,
		SegmentsExperience segmentsExperience) {

		SegmentsExperience swapSegmentsExperience =
			segmentsExperiencePersistence.fetchByG_C_C_P(
				segmentsExperience.getGroupId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), priority);

		if (swapSegmentsExperience == null) {
			segmentsExperience.setPriority(priority);

			return segmentsExperiencePersistence.update(segmentsExperience);
		}

		int originalPriority = segmentsExperience.getPriority();

		segmentsExperience.setPriority(lowestPriority - 1);

		segmentsExperience = segmentsExperiencePersistence.update(
			segmentsExperience);

		swapSegmentsExperience.setPriority(lowestPriority - 2);

		swapSegmentsExperience = segmentsExperiencePersistence.update(
			swapSegmentsExperience);

		segmentsExperiencePersistence.flush();

		segmentsExperience.setPriority(priority);

		segmentsExperience =
			segmentsExperienceLocalService.updateSegmentsExperience(
				segmentsExperience);

		swapSegmentsExperience.setPriority(originalPriority);

		segmentsExperienceLocalService.updateSegmentsExperience(
			swapSegmentsExperience);

		return segmentsExperience;
	}

	private List<SegmentsExperience> _updateSegmentExperiencesPriority(
		List<SegmentsExperience> segmentsExperiences, int initialPriority,
		int offset) {

		int currentPriority = initialPriority;

		List<SegmentsExperience> updatedSegmentsExperiences = new ArrayList<>();

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperience.setPriority(currentPriority);

			updatedSegmentsExperiences.add(
				segmentsExperiencePersistence.update(segmentsExperience));

			currentPriority = currentPriority + offset;
		}

		return updatedSegmentsExperiences;
	}

	private void _updateSegmentExperiencesPriority(
		SegmentsExperience segmentsExperience) {

		if (segmentsExperience.getPriority() >= 0) {
			List<SegmentsExperience> segmentsExperiences = new ArrayList<>(
				segmentsExperiencePersistence.findByG_C_C_GtP(
					segmentsExperience.getGroupId(),
					segmentsExperience.getClassNameId(),
					segmentsExperience.getClassPK(),
					segmentsExperience.getPriority()));

			if (ListUtil.isEmpty(segmentsExperiences)) {
				return;
			}

			int highestPriority = _getHighestPriority(
				segmentsExperience.getGroupId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK());

			_updateSegmentExperiencesPriority(
				segmentsExperiences, highestPriority - 1, -1);

			segmentsExperiencePersistence.flush();
		}
		else {
			List<SegmentsExperience> segmentsExperiences = new ArrayList<>(
				segmentsExperiencePersistence.findByG_C_C_LtP(
					segmentsExperience.getGroupId(),
					segmentsExperience.getClassNameId(),
					segmentsExperience.getClassPK(),
					segmentsExperience.getPriority()));

			if (ListUtil.isEmpty(segmentsExperiences)) {
				return;
			}

			_updateSegmentExperiencesPriority(
				segmentsExperiences, segmentsExperience.getPriority(), +1);
		}
	}

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if (nameMap.isEmpty() || Validator.isNull(nameMap.get(locale))) {
			throw new SegmentsExperienceNameException();
		}
	}

	private void _validatePriority(
			long groupId, long classNameId, long classPK, int priority)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_C_C_P(
				groupId, classNameId, classPK, priority);

		if (segmentsExperience != null) {
			throw new SegmentsExperiencePriorityException(
				"A segments experience with the priority " + priority +
					" already exists");
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}