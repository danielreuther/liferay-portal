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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.info.display.url.provider.InfoEditURLProviderRegistry;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.layout.list.permission.provider.LayoutListPermissionProvider;
import com.liferay.layout.list.permission.provider.LayoutListPermissionProviderRegistry;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverRegistry;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryRegistry;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = {})
public class ContentUtil {

	public static Set<LayoutDisplayPageObjectProvider<?>>
		getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
			FragmentEntryLink fragmentEntryLink) {

		return _getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
			fragmentEntryLink, new HashSet<>());
	}

	public static Set<LayoutDisplayPageObjectProvider<?>>
		getLayoutMappedLayoutDisplayPageObjectProviders(String layoutData) {

		return _getLayoutMappedLayoutDisplayPageObjectProviders(
			LayoutStructure.of(layoutData), new HashSet<>());
	}

	public static Set<LayoutDisplayPageObjectProvider<?>>
			getMappedLayoutDisplayPageObjectProviders(long groupId, long plid)
		throws PortalException {

		Set<Long> mappedClassPKs = new HashSet<>();

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders =
				_getFragmentEntryLinksMappedLayoutDisplayPageObjectProviders(
					groupId, plid, mappedClassPKs);

		layoutDisplayPageObjectProviders.addAll(
			_getLayoutMappedLayoutDisplayPageObjectProviders(
				groupId, plid, mappedClassPKs));

		return layoutDisplayPageObjectProviders;
	}

	public static JSONArray getPageContentsJSONArray(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long plid,
			long segmentsExperienceId)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), plid, segmentsExperienceId);

		List<String> hiddenItemIds = _getHiddenItemIds(
			layoutStructure, themeDisplay);

		return JSONUtil.concat(
			_getLayoutClassedModelPageContentsJSONArray(
				httpServletRequest, layoutStructure, plid, hiddenItemIds,
				segmentsExperienceId),
			AssetListEntryUsagesUtil.getPageContentsJSONArray(
				httpServletRequest, httpServletResponse, layoutStructure, plid,
				hiddenItemIds));
	}

	@Reference(unbind = "-")
	protected void setDLURLHelper(DLURLHelper dlURLHelper) {
		_dlURLHelper = dlURLHelper;
	}

	@Reference(unbind = "-")
	protected void setFragmentEntryLinkLocalService(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setInfoEditURLProviderRegistry(
		InfoEditURLProviderRegistry infoEditURLProviderRegistry) {

		_infoEditURLProviderRegistry = infoEditURLProviderRegistry;
	}

	@Reference(unbind = "-")
	protected void setInfoItemServiceRegistry(
		InfoItemServiceRegistry infoItemServiceRegistry) {

		_infoItemServiceRegistry = infoItemServiceRegistry;
	}

	@Reference(unbind = "-")
	protected void setInfoSearchClassMapperRegistry(
		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry) {

		_infoSearchClassMapperRegistry = infoSearchClassMapperRegistry;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setLanguage(Language language) {
		_language = language;
	}

	@Reference(unbind = "-")
	protected void setLayoutClassedModelUsageLocalService(
		LayoutClassedModelUsageLocalService
			layoutClassedModelUsageLocalService) {

		_layoutClassedModelUsageLocalService =
			layoutClassedModelUsageLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutContentModelResourcePermission(
		LayoutContentModelResourcePermission
			layoutContentModelResourcePermission) {

		_layoutContentModelResourcePermission =
			layoutContentModelResourcePermission;
	}

	@Reference(unbind = "-")
	protected void setLayoutDisplayPageProviderRegistry(
		LayoutDisplayPageProviderRegistry layoutDisplayPageProviderRegistry) {

		_layoutDisplayPageProviderRegistry = layoutDisplayPageProviderRegistry;
	}

	@Reference(unbind = "-")
	protected void setLayoutListPermissionProviderRegistry(
		LayoutListPermissionProviderRegistry
			layoutListPermissionProviderRegistry) {

		_layoutListPermissionProviderRegistry =
			layoutListPermissionProviderRegistry;
	}

	@Reference(unbind = "-")
	protected void setLayoutListRetrieverRegistry(
		LayoutListRetrieverRegistry layoutListRetrieverRegistry) {

		_layoutListRetrieverRegistry = layoutListRetrieverRegistry;
	}

	@Reference(unbind = "-")
	protected void setListObjectReferenceFactoryRegistry(
		ListObjectReferenceFactoryRegistry listObjectReferenceFactoryRegistry) {

		_listObjectReferenceFactoryRegistry =
			listObjectReferenceFactoryRegistry;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(unbind = "-")
	protected void setPortletRegistry(PortletRegistry portletRegistry) {
		_portletRegistry = portletRegistry;
	}

	@Reference(unbind = "-")
	protected void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	private static String _generateUniqueLayoutClassedModelUsageKey(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		return layoutClassedModelUsage.getClassNameId() + StringPool.DASH +
			layoutClassedModelUsage.getClassPK();
	}

	private static JSONObject _getActionsJSONObject(
			LayoutClassedModelUsage layoutClassedModelUsage,
			ThemeDisplay themeDisplay, HttpServletRequest httpServletRequest)
		throws Exception {

		String className = layoutClassedModelUsage.getClassName();

		boolean hasUpdatePermission =
			_layoutContentModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), className,
				layoutClassedModelUsage.getClassPK(), ActionKeys.UPDATE);

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderRegistry.
				getLayoutDisplayPageProviderByClassName(className);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					className, layoutClassedModelUsage.getClassPK()));

		return JSONUtil.put(
			"editImage",
			() -> {
				if (!hasUpdatePermission ||
					!Objects.equals(className, FileEntry.class.getName())) {

					return null;
				}

				FileEntry fileEntry =
					(FileEntry)
						layoutDisplayPageObjectProvider.getDisplayObject();

				PortletResponse portletResponse =
					(PortletResponse)httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE);

				LiferayPortletResponse liferayPortletResponse =
					_portal.getLiferayPortletResponse(portletResponse);

				LiferayPortletURL portletURL =
					liferayPortletResponse.createActionURL(
						DLPortletKeys.DOCUMENT_LIBRARY_ADMIN);

				portletURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/document_library/edit_file_entry_image_editor");

				return JSONUtil.put(
					"editImageURL", portletURL.toString()
				).put(
					"fileEntryId", fileEntry.getFileEntryId()
				).put(
					"previewURL",
					_dlURLHelper.getPreviewURL(
						fileEntry, fileEntry.getFileVersion(), themeDisplay,
						StringPool.BLANK)
				);
			}
		).put(
			"editURL",
			() -> {
				if (!hasUpdatePermission) {
					return null;
				}

				InfoEditURLProvider<Object> infoEditURLProvider =
					_infoEditURLProviderRegistry.getInfoEditURLProvider(
						className);

				if (infoEditURLProvider == null) {
					return null;
				}

				return infoEditURLProvider.getURL(
					layoutDisplayPageObjectProvider.getDisplayObject(),
					httpServletRequest);
			}
		).put(
			"permissionsURL",
			() -> {
				if (!_layoutContentModelResourcePermission.contains(
						themeDisplay.getPermissionChecker(), className,
						layoutClassedModelUsage.getClassPK(),
						ActionKeys.PERMISSIONS)) {

					return null;
				}

				return PermissionsURLTag.doTag(
					StringPool.BLANK, className,
					HtmlUtil.escape(
						layoutDisplayPageObjectProvider.getTitle(
							themeDisplay.getLocale())),
					null, String.valueOf(layoutClassedModelUsage.getClassPK()),
					LiferayWindowState.POP_UP.toString(), null,
					httpServletRequest);
			}
		).put(
			"viewUsagesURL",
			() -> {
				if (!_layoutContentModelResourcePermission.contains(
						themeDisplay.getPermissionChecker(), className,
						layoutClassedModelUsage.getClassPK(),
						ActionKeys.VIEW)) {

					return null;
				}

				return PortletURLBuilder.create(
					PortletURLFactoryUtil.create(
						httpServletRequest,
						ContentPageEditorPortletKeys.
							CONTENT_PAGE_EDITOR_PORTLET,
						PortletRequest.RENDER_PHASE)
				).setMVCPath(
					"/view_layout_classed_model_usages.jsp"
				).setParameter(
					"className", className
				).setParameter(
					"classPK", layoutClassedModelUsage.getClassPK()
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString();
			}
		);
	}

	private static AssetRendererFactory<?> _getAssetRendererFactory(
		String className) {

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(
				_infoSearchClassMapperRegistry.getSearchClassName(className));
	}

	private static List<String> _getChildrenItemIds(
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem) {

		List<String> childrenItemIds = new ArrayList<>();

		for (String childItemId : layoutStructureItem.getChildrenItemIds()) {
			childrenItemIds.add(childItemId);

			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			childrenItemIds.addAll(
				_getChildrenItemIds(layoutStructure, childLayoutStructureItem));
		}

		return childrenItemIds;
	}

	private static long _getFragmentEntryLinkClassNameId() {
		if (_fragmentEntryLinkClassNameId != null) {
			return _fragmentEntryLinkClassNameId;
		}

		_fragmentEntryLinkClassNameId = _portal.getClassNameId(
			FragmentEntryLink.class.getName());

		return _fragmentEntryLinkClassNameId;
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
		_getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
			FragmentEntryLink fragmentEntryLink, Set<Long> mappedClassPKs) {

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to create JSON object from " +
						fragmentEntryLink.getEditableValues(),
					jsonException);
			}

			return Collections.emptySet();
		}

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders = new HashSet<>();

		Iterator<String> keysIterator = editableValuesJSONObject.keys();

		while (keysIterator.hasNext()) {
			String key = keysIterator.next();

			JSONObject editableProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(key);

			if (editableProcessorJSONObject == null) {
				continue;
			}

			Iterator<String> editableKeysIterator =
				editableProcessorJSONObject.keys();

			while (editableKeysIterator.hasNext()) {
				String editableKey = editableKeysIterator.next();

				JSONObject editableJSONObject =
					editableProcessorJSONObject.getJSONObject(editableKey);

				if (editableJSONObject == null) {
					continue;
				}

				layoutDisplayPageObjectProviders.addAll(
					_getLocalizedLayoutDisplayPageObjectProviders(
						editableJSONObject, mappedClassPKs));

				JSONObject configJSONObject = editableJSONObject.getJSONObject(
					"config");

				if ((configJSONObject != null) &&
					(configJSONObject.length() > 0)) {

					LayoutDisplayPageObjectProvider<?>
						layoutDisplayPageObjectProvider =
							_getLayoutDisplayPageObjectProvider(
								configJSONObject, mappedClassPKs);

					if (layoutDisplayPageObjectProvider != null) {
						layoutDisplayPageObjectProviders.add(
							layoutDisplayPageObjectProvider);
					}

					layoutDisplayPageObjectProviders.addAll(
						_getLocalizedLayoutDisplayPageObjectProviders(
							configJSONObject, mappedClassPKs));
				}

				JSONObject itemSelectorJSONObject =
					editableJSONObject.getJSONObject("itemSelector");

				if ((itemSelectorJSONObject != null) &&
					(itemSelectorJSONObject.length() > 0)) {

					LayoutDisplayPageObjectProvider<?>
						layoutDisplayPageObjectProvider =
							_getLayoutDisplayPageObjectProvider(
								itemSelectorJSONObject, mappedClassPKs);

					if (layoutDisplayPageObjectProvider != null) {
						layoutDisplayPageObjectProviders.add(
							layoutDisplayPageObjectProvider);
					}
				}

				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						_getLayoutDisplayPageObjectProvider(
							editableJSONObject, mappedClassPKs);

				if (layoutDisplayPageObjectProvider == null) {
					continue;
				}

				layoutDisplayPageObjectProviders.add(
					layoutDisplayPageObjectProvider);
			}
		}

		return layoutDisplayPageObjectProviders;
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
		_getFragmentEntryLinksMappedLayoutDisplayPageObjectProviders(
			long groupId, long plid, Set<Long> mappedClassPKs) {

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders = new HashSet<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				groupId, plid);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			layoutDisplayPageObjectProviders.addAll(
				_getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
					fragmentEntryLink, mappedClassPKs));
		}

		return layoutDisplayPageObjectProviders;
	}

	private static List<String> _getHiddenItemIds(
		LayoutStructure layoutStructure, ThemeDisplay themeDisplay) {

		List<String> hiddenItemIds = new ArrayList<>();

		if (!FeatureFlagManagerUtil.isEnabled("LPS-169923")) {
			return hiddenItemIds;
		}

		for (FormStyledLayoutStructureItem formStyledLayoutStructureItem :
				layoutStructure.getFormStyledLayoutStructureItems()) {

			if (layoutStructure.isItemMarkedForDeletion(
					formStyledLayoutStructureItem.getItemId()) ||
				(formStyledLayoutStructureItem.getClassNameId() <= 0)) {

				continue;
			}

			InfoPermissionProvider<?> infoPermissionProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoPermissionProvider.class,
					_portal.getClassName(
						formStyledLayoutStructureItem.getClassNameId()));

			if ((infoPermissionProvider == null) ||
				infoPermissionProvider.hasViewPermission(
					themeDisplay.getPermissionChecker())) {

				continue;
			}

			hiddenItemIds.addAll(
				_getChildrenItemIds(
					layoutStructure, formStyledLayoutStructureItem));
		}

		for (CollectionStyledLayoutStructureItem
				collectionStyledLayoutStructureItem :
					layoutStructure.getCollectionStyledLayoutStructureItems()) {

			JSONObject collectionJSONObject =
				collectionStyledLayoutStructureItem.getCollectionJSONObject();

			if ((collectionJSONObject == null) ||
				(collectionJSONObject.length() <= 0)) {

				continue;
			}

			String type = collectionJSONObject.getString("type");

			LayoutListRetriever<?, ?> layoutListRetriever =
				_layoutListRetrieverRegistry.getLayoutListRetriever(type);

			if (layoutListRetriever == null) {
				continue;
			}

			ListObjectReferenceFactory<?> listObjectReferenceFactory =
				_listObjectReferenceFactoryRegistry.getListObjectReference(
					type);

			if (listObjectReferenceFactory == null) {
				continue;
			}

			ListObjectReference listObjectReference =
				listObjectReferenceFactory.getListObjectReference(
					collectionJSONObject);

			Class<? extends ListObjectReference> listObjectReferenceClass =
				listObjectReference.getClass();

			LayoutListPermissionProvider<ListObjectReference>
				layoutListPermissionProvider =
					(LayoutListPermissionProvider<ListObjectReference>)
						_layoutListPermissionProviderRegistry.
							getLayoutListPermissionProvider(
								listObjectReferenceClass.getName());

			if ((layoutListPermissionProvider == null) ||
				layoutListPermissionProvider.hasPermission(
					themeDisplay.getPermissionChecker(), listObjectReference,
					ActionKeys.VIEW)) {

				continue;
			}

			hiddenItemIds.addAll(
				_getChildrenItemIds(
					layoutStructure, collectionStyledLayoutStructureItem));
		}

		return hiddenItemIds;
	}

	private static String _getIcon(String className, long classPK)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory = _getAssetRendererFactory(
			className);

		if (assetRendererFactory == null) {
			return "web-content";
		}

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			classPK);

		if (assetRenderer == null) {
			return "web-content";
		}

		return assetRenderer.getIconCssClass();
	}

	private static JSONArray _getLayoutClassedModelPageContentsJSONArray(
			HttpServletRequest httpServletRequest,
			LayoutStructure layoutStructure, long plid,
			List<String> hiddenItemIds, long segmentsExperienceId)
		throws PortalException {

		JSONArray mappedContentsJSONArray = _jsonFactory.createJSONArray();

		Set<String> uniqueLayoutClassedModelUsageKeys = new HashSet<>();

		List<String> restrictedPortletIds = _getRestrictedPortletIds(
			layoutStructure, hiddenItemIds);

		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(plid);

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			if (uniqueLayoutClassedModelUsageKeys.contains(
					_generateUniqueLayoutClassedModelUsageKey(
						layoutClassedModelUsage))) {

				continue;
			}

			if (layoutClassedModelUsage.getContainerType() ==
					_getFragmentEntryLinkClassNameId()) {

				FragmentEntryLink fragmentEntryLink =
					_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
						GetterUtil.getLong(
							layoutClassedModelUsage.getContainerKey()));

				if (fragmentEntryLink == null) {
					_layoutClassedModelUsageLocalService.
						deleteLayoutClassedModelUsage(layoutClassedModelUsage);

					continue;
				}

				if (!Objects.equals(
						fragmentEntryLink.getSegmentsExperienceId(),
						segmentsExperienceId)) {

					continue;
				}

				LayoutStructureItem layoutStructureItem =
					layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
						fragmentEntryLink.getFragmentEntryLinkId());

				if ((layoutStructureItem == null) ||
					fragmentEntryLink.isDeleted() ||
					hiddenItemIds.contains(layoutStructureItem.getItemId())) {

					continue;
				}
			}

			if ((layoutClassedModelUsage.getContainerType() ==
					_getPortletClassNameId()) &&
				(layoutStructure.isPortletMarkedForDeletion(
					layoutClassedModelUsage.getContainerKey()) ||
				 restrictedPortletIds.contains(
					 layoutClassedModelUsage.getContainerKey()))) {

				continue;
			}

			try {
				LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
					_layoutDisplayPageProviderRegistry.
						getLayoutDisplayPageProviderByClassName(
							layoutClassedModelUsage.getClassName());

				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						layoutDisplayPageProvider.
							getLayoutDisplayPageObjectProvider(
								new InfoItemReference(
									layoutClassedModelUsage.getClassName(),
									layoutClassedModelUsage.getClassPK()));

				if (layoutDisplayPageObjectProvider == null) {
					_layoutClassedModelUsageLocalService.
						deleteLayoutClassedModelUsage(layoutClassedModelUsage);

					continue;
				}

				mappedContentsJSONArray.put(
					_getPageContentJSONObject(
						layoutClassedModelUsage,
						layoutDisplayPageObjectProvider, httpServletRequest));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"An error occurred while getting mapped content ",
							"with class PK ",
							layoutClassedModelUsage.getClassPK(),
							" and class name ID ",
							layoutClassedModelUsage.getClassNameId()),
						exception);
				}
			}

			uniqueLayoutClassedModelUsageKeys.add(
				_generateUniqueLayoutClassedModelUsageKey(
					layoutClassedModelUsage));
		}

		return mappedContentsJSONArray;
	}

	private static LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(
			JSONObject jsonObject, Set<Long> mappedClassPKs) {

		if (!jsonObject.has("classNameId") || !jsonObject.has("classPK")) {
			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		if ((classPK <= 0) || mappedClassPKs.contains(classPK)) {
			return null;
		}

		long classNameId = jsonObject.getLong("classNameId");

		if (classNameId <= 0) {
			return null;
		}

		String className = _portal.getClassName(classNameId);

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderRegistry.
				getLayoutDisplayPageProviderByClassName(className);

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		mappedClassPKs.add(classPK);

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			new InfoItemReference(className, classPK));
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
		_getLayoutMappedLayoutDisplayPageObjectProviders(
			LayoutStructure layoutStructure, Set<Long> mappedClassPKs) {

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders = new HashSet<>();

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof
					ContainerStyledLayoutStructureItem) ||
				layoutStructure.isItemMarkedForDeletion(
					layoutStructureItem.getItemId())) {

				continue;
			}

			ContainerStyledLayoutStructureItem
				containerStyledLayoutStructureItem =
					(ContainerStyledLayoutStructureItem)layoutStructureItem;

			JSONObject backgroundImageJSONObject =
				containerStyledLayoutStructureItem.
					getBackgroundImageJSONObject();

			if (backgroundImageJSONObject != null) {
				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						_getLayoutDisplayPageObjectProvider(
							backgroundImageJSONObject, mappedClassPKs);

				if (layoutDisplayPageObjectProvider != null) {
					layoutDisplayPageObjectProviders.add(
						layoutDisplayPageObjectProvider);
				}
			}

			JSONObject linkJSONObject =
				containerStyledLayoutStructureItem.getLinkJSONObject();

			if (linkJSONObject != null) {
				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						_getLayoutDisplayPageObjectProvider(
							linkJSONObject, mappedClassPKs);

				if (layoutDisplayPageObjectProvider != null) {
					layoutDisplayPageObjectProviders.add(
						layoutDisplayPageObjectProvider);
				}

				layoutDisplayPageObjectProviders.addAll(
					_getLocalizedLayoutDisplayPageObjectProviders(
						linkJSONObject, mappedClassPKs));
			}
		}

		return layoutDisplayPageObjectProviders;
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
			_getLayoutMappedLayoutDisplayPageObjectProviders(
				long groupId, long plid, Set<Long> mappedClassPKs)
		throws PortalException {

		return _getLayoutMappedLayoutDisplayPageObjectProviders(
			LayoutStructureUtil.getLayoutStructure(
				groupId, plid, SegmentsExperienceConstants.KEY_DEFAULT),
			mappedClassPKs);
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
		_getLocalizedLayoutDisplayPageObjectProviders(
			JSONObject jsonObject, Set<Long> mappedClassPKs) {

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders = new HashSet<>();

		Set<Locale> locales = _language.getAvailableLocales();

		for (Locale locale : locales) {
			JSONObject localizableJSONObject = jsonObject.getJSONObject(
				LocaleUtil.toLanguageId(locale));

			if ((localizableJSONObject == null) ||
				(localizableJSONObject.length() == 0)) {

				continue;
			}

			LayoutDisplayPageObjectProvider<?>
				localizedLayoutDisplayPageObjectProvider =
					_getLayoutDisplayPageObjectProvider(
						localizableJSONObject, mappedClassPKs);

			if (localizedLayoutDisplayPageObjectProvider != null) {
				layoutDisplayPageObjectProviders.add(
					localizedLayoutDisplayPageObjectProvider);
			}
		}

		return layoutDisplayPageObjectProviders;
	}

	private static JSONObject _getPageContentJSONObject(
			LayoutClassedModelUsage layoutClassedModelUsage,
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"actions",
			_getActionsJSONObject(
				layoutClassedModelUsage, themeDisplay, httpServletRequest)
		).put(
			"className", layoutClassedModelUsage.getClassName()
		).put(
			"classNameId", layoutClassedModelUsage.getClassNameId()
		).put(
			"classPK", layoutClassedModelUsage.getClassPK()
		).put(
			"classTypeId", layoutDisplayPageObjectProvider.getClassTypeId()
		).put(
			"icon",
			_getIcon(
				layoutClassedModelUsage.getClassName(),
				layoutClassedModelUsage.getClassPK())
		).put(
			"status", _getStatusJSONObject(layoutClassedModelUsage)
		).put(
			"subtype",
			_getSubtype(
				layoutClassedModelUsage.getClassName(),
				layoutDisplayPageObjectProvider.getClassTypeId(),
				themeDisplay.getLocale())
		).put(
			"title",
			layoutDisplayPageObjectProvider.getTitle(themeDisplay.getLocale())
		).put(
			"type",
			_resourceActions.getModelResource(
				themeDisplay.getLocale(),
				layoutClassedModelUsage.getClassName())
		).put(
			"usagesCount",
			_layoutClassedModelUsageLocalService.
				getUniqueLayoutClassedModelUsagesCount(
					layoutClassedModelUsage.getClassNameId(),
					layoutClassedModelUsage.getClassPK())
		);
	}

	private static long _getPortletClassNameId() {
		if (_portletClassNameId != null) {
			return _portletClassNameId;
		}

		_portletClassNameId = _portal.getClassNameId(Portlet.class.getName());

		return _portletClassNameId;
	}

	private static List<String> _getRestrictedPortletIds(
		LayoutStructure layoutStructure, List<String> hiddenItemIds) {

		if (hiddenItemIds.isEmpty()) {
			return Collections.emptyList();
		}

		Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
			layoutStructure.getFragmentLayoutStructureItems();

		Map<String, List<String>> portletIds = new HashMap<>();

		for (Map.Entry<Long, LayoutStructureItem> entry :
				fragmentLayoutStructureItems.entrySet()) {

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)entry.getValue();

			if (layoutStructure.isItemMarkedForDeletion(
					fragmentStyledLayoutStructureItem.getItemId())) {

				continue;
			}

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					GetterUtil.getLong(entry.getKey()));

			if ((fragmentEntryLink == null) || fragmentEntryLink.isDeleted()) {
				continue;
			}

			for (String portletId :
					_portletRegistry.getFragmentEntryLinkPortletIds(
						fragmentEntryLink)) {

				List<String> itemIds = portletIds.computeIfAbsent(
					portletId, key -> new ArrayList<>());

				itemIds.add(fragmentStyledLayoutStructureItem.getItemId());
			}
		}

		List<String> restrictedPortletIds = new ArrayList<>();

		for (Map.Entry<String, List<String>> entry : portletIds.entrySet()) {
			boolean restrictedPortletId = true;

			for (String itemId : entry.getValue()) {
				if (!hiddenItemIds.contains(itemId)) {
					restrictedPortletId = false;

					break;
				}
			}

			if (restrictedPortletId) {
				restrictedPortletIds.add(entry.getKey());
			}
		}

		return restrictedPortletIds;
	}

	private static JSONObject _getStatusJSONObject(
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				layoutClassedModelUsage.getClassName());

		if (assetRendererFactory == null) {
			return JSONUtil.put(
				"hasApprovedVersion", false
			).put(
				"label",
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_APPROVED)
			).put(
				"style",
				WorkflowConstants.getStatusStyle(
					WorkflowConstants.STATUS_APPROVED)
			);
		}

		AssetRenderer<?> latestAssetRenderer =
			assetRendererFactory.getAssetRenderer(
				layoutClassedModelUsage.getClassPK(),
				AssetRendererFactory.TYPE_LATEST);

		boolean hasApprovedVersion = false;

		if (latestAssetRenderer.getStatus() !=
				WorkflowConstants.STATUS_APPROVED) {

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					layoutClassedModelUsage.getClassPK(),
					AssetRendererFactory.TYPE_LATEST_APPROVED);

			if (assetRenderer.getStatus() ==
					WorkflowConstants.STATUS_APPROVED) {

				hasApprovedVersion = true;
			}
		}

		return JSONUtil.put(
			"hasApprovedVersion", hasApprovedVersion
		).put(
			"label",
			WorkflowConstants.getStatusLabel(latestAssetRenderer.getStatus())
		).put(
			"style",
			WorkflowConstants.getStatusStyle(latestAssetRenderer.getStatus())
		);
	}

	private static String _getSubtype(
		String className, long classTypeId, Locale locale) {

		AssetRendererFactory<?> assetRendererFactory = _getAssetRendererFactory(
			className);

		if (assetRendererFactory == null) {
			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		try {
			ClassType classType = classTypeReader.getClassType(
				classTypeId, locale);

			return classType.getName();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ContentUtil.class);

	private static DLURLHelper _dlURLHelper;
	private static Long _fragmentEntryLinkClassNameId;
	private static FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private static InfoEditURLProviderRegistry _infoEditURLProviderRegistry;
	private static InfoItemServiceRegistry _infoItemServiceRegistry;
	private static InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;
	private static JSONFactory _jsonFactory;
	private static Language _language;
	private static LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;
	private static LayoutContentModelResourcePermission
		_layoutContentModelResourcePermission;
	private static LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;
	private static LayoutListPermissionProviderRegistry
		_layoutListPermissionProviderRegistry;
	private static LayoutListRetrieverRegistry _layoutListRetrieverRegistry;
	private static ListObjectReferenceFactoryRegistry
		_listObjectReferenceFactoryRegistry;
	private static Portal _portal;
	private static Long _portletClassNameId;
	private static PortletRegistry _portletRegistry;
	private static ResourceActions _resourceActions;

}