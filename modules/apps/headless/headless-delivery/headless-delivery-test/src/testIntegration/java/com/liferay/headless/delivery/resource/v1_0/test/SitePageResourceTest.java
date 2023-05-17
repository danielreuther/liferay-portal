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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.delivery.client.dto.v1_0.ParentSitePage;
import com.liferay.headless.delivery.client.dto.v1_0.SitePage;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.InputStream;

import java.net.URLEncoder;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class SitePageResourceTest extends BaseSitePageResourceTestCase {

	@Override
	@Test
	public void testGetSiteSitePage() throws Exception {
		Layout layout = _addLayout(testGroup);

		String friendlyURL = layout.getFriendlyURL();

		SitePage sitePage = sitePageResource.getSiteSitePage(
			testGroup.getGroupId(), friendlyURL.substring(1));

		Assert.assertNotNull(sitePage);
		Assert.assertEquals(
			layout.getName(LocaleUtil.getDefault()), sitePage.getTitle());

		try {
			sitePageResource.getSiteSitePage(
				testGroup.getGroupId(), URLEncoder.encode("aaa/bbb"));

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertNotNull(problemException);
		}
	}

	@Override
	@Test
	public void testGetSiteSitePageExperienceExperienceKey() throws Exception {
		Layout layout = _addLayout(
			testGroup, true, RandomTestUtil.randomString());

		String friendlyURL = layout.getFriendlyURL();

		SitePage sitePage =
			sitePageResource.getSiteSitePageExperienceExperienceKey(
				testGroup.getGroupId(), friendlyURL.substring(1),
				SegmentsExperienceConstants.KEY_DEFAULT);

		Assert.assertNotNull(sitePage);
		Assert.assertNotNull(sitePage.getExperience());
	}

	@Override
	@Test
	public void testGetSiteSitePageExperienceExperienceKeyRenderedPage()
		throws Exception {

		Layout layout = _addLayout(
			testGroup, true, RandomTestUtil.randomString());

		String friendlyURL = layout.getFriendlyURL();
		SegmentsExperience segmentsExperience = _addSegmentsExperience(
			layout,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));

		Assert.assertNotNull(
			sitePageResource.getSiteSitePageExperienceExperienceKeyRenderedPage(
				testGroup.getGroupId(), friendlyURL.substring(1),
				segmentsExperience.getSegmentsExperienceKey()));
	}

	@Override
	@Test
	public void testGetSiteSitePageRenderedPage() throws Exception {
		Layout layout = _addLayout(testGroup);

		String friendlyURL = layout.getFriendlyURL();

		Assert.assertNotNull(
			sitePageResource.getSiteSitePageRenderedPage(
				testGroup.getGroupId(), friendlyURL.substring(1)));
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSitePagesExperiencesPage() throws Exception {
		Layout layout = _addLayout(testGroup);

		String friendlyURL = layout.getFriendlyURL();

		Page<SitePage> page = sitePageResource.getSiteSitePagesExperiencesPage(
			testGroup.getGroupId(), friendlyURL.substring(1));

		long originalPageCount = page.getTotalCount();

		_addSegmentsExperience(
			layout,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));

		page = sitePageResource.getSiteSitePagesExperiencesPage(
			testGroup.getGroupId(), friendlyURL.substring(1));

		Assert.assertEquals(originalPageCount + 1, page.getTotalCount());
	}

	@Override
	@Test
	public void testGetSiteSitePagesPage() throws Exception {
		Page<SitePage> sitePagePage = sitePageResource.getSiteSitePagesPage(
			testGroup.getGroupId(), null, null, null, null, null);

		Assert.assertEquals(
			_layoutLocalService.getLayoutsCount(testGroup.getGroupId(), false),
			sitePagePage.getTotalCount());
	}

	@Override
	@Test
	public void testGraphQLGetSiteSitePagesPage() throws Exception {
		Long siteId = testGetSiteSitePagesPage_getSiteId();

		_addLayout(_groupLocalService.fetchGroup(siteId));

		BaseSitePageResourceTestCase.GraphQLField graphQLField =
			new BaseSitePageResourceTestCase.GraphQLField(
				"sitePages",
				HashMapBuilder.<String, Object>put(
					"siteKey", "\"" + siteId + "\""
				).build(),
				new BaseSitePageResourceTestCase.GraphQLField(
					"items", getGraphQLFields()),
				new BaseSitePageResourceTestCase.GraphQLField("page"),
				new BaseSitePageResourceTestCase.GraphQLField("totalCount"));

		JSONObject sitePagesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/sitePages");

		Assert.assertEquals(
			_layoutLocalService.getLayoutsCount(testGroup.getGroupId(), false),
			sitePagesJSONObject.get("totalCount"));
	}

	@Override
	@Test
	public void testPostSiteSitePage() throws Exception {
		super.testPostSiteSitePage();

		_testPostSiteSitePageFailureDuplicateFriendlyURL();
		_testPostSiteSitePageFailureInvalidTitle();
		_testPostSiteSitePageFailureFriendlyURLContainsDoubleSlash();
		_testPostSiteSitePageFailureFriendlyURLContainsInvalidCharacters();
		_testPostSiteSitePageFailureFriendlyURLEndsWithSlash();
		_testPostSiteSitePageFailureFriendlyURLTooLong();
		_testPostSiteSitePageFailureFriendlyURLTooShort();
		_testPostSiteSitePageSuccessInvalidParentSitePage();
		_testPostSiteSitePageSuccessKeywords();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"friendlyUrlPath", "title"};
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return super.getEntityFields();
	}

	@Override
	protected SitePage randomSitePage() {
		return new SitePage() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				friendlyUrlPath =
					StringPool.FORWARD_SLASH +
						StringUtil.toLowerCase(RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				pageType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected String
			testGraphQLGetSiteSitePageExperienceExperienceKey_getExperienceKey()
		throws Exception {

		return SegmentsEntryConstants.KEY_DEFAULT;
	}

	private Layout _addLayout(Group group) throws Exception {
		return _addLayout(group, false, RandomTestUtil.randomString());
	}

	private Layout _addLayout(
			Group group, boolean importPageDefinition, String title)
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentPublishedLayout(
			group, title, WorkflowConstants.STATUS_APPROVED);

		if (importPageDefinition) {
			String name = PrincipalThreadLocal.getName();

			try {
				PrincipalThreadLocal.setName(TestPropsValues.getUserId());

				ServiceContextThreadLocal.pushServiceContext(
					ServiceContextTestUtil.getServiceContext(
						testGroup.getGroupId()));

				LayoutPageTemplateStructure layoutPageTemplateStructure =
					_layoutPageTemplateStructureLocalService.
						fetchLayoutPageTemplateStructure(
							testGroup.getGroupId(), layout.getPlid());

				LayoutStructure layoutStructure = LayoutStructure.of(
					layoutPageTemplateStructure.
						getDefaultSegmentsExperienceData());

				layoutStructure.addRootLayoutStructureItem();

				_layoutsImporter.importPageElement(
					layout, layoutStructure, layoutStructure.getMainItemId(),
					_read("test-page-element.json"), 0);
			}
			finally {
				PrincipalThreadLocal.setName(name);

				ServiceContextThreadLocal.popServiceContext();
			}
		}

		return layout;
	}

	private SegmentsExperience _addSegmentsExperience(
			Layout layout, ServiceContext serviceContext)
		throws Exception {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.addSegmentsEntry(
				null,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				null, true, null, User.class.getName(), serviceContext);

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				TestPropsValues.getUserId(), layout.getGroupId(),
				segmentsEntry.getSegmentsEntryId(), layout.getPlid(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				true, new UnicodeProperties(true), serviceContext);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					testGroup.getGroupId(), layout.getPlid());

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			_layoutPageTemplateStructureRelLocalService.
				fetchLayoutPageTemplateStructureRel(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					_segmentsExperienceLocalService.
						fetchDefaultSegmentsExperienceId(layout.getPlid()));

		layoutPageTemplateStructureRel.setSegmentsExperienceId(
			segmentsExperience.getSegmentsExperienceId());

		_layoutPageTemplateStructureRelLocalService.
			updateLayoutPageTemplateStructureRel(
				layoutPageTemplateStructureRel);

		return segmentsExperience;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _testPostSiteSitePageFailureDuplicateFriendlyURL()
		throws Exception {

		SitePage randomSitePage = randomSitePage();

		testPostSiteSitePage_addSitePage(randomSitePage);

		try {
			testPostSiteSitePage_addSitePage(randomSitePage);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"LayoutFriendlyURLsException", problem.getType());
		}
	}

	private void _testPostSiteSitePageFailureFriendlyURLContainsDoubleSlash()
		throws Exception {

		SitePage randomSitePage = randomSitePage();

		randomSitePage.setFriendlyUrlPath(
			RandomTestUtil.randomString() + StringPool.DOUBLE_SLASH +
				RandomTestUtil.randomString());

		try {
			testPostSiteSitePage_addSitePage(randomSitePage);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"LayoutFriendlyURLsException", problem.getType());
		}
	}

	private void _testPostSiteSitePageFailureFriendlyURLContainsInvalidCharacters()
		throws Exception {

		SitePage randomSitePage = randomSitePage();

		randomSitePage.setFriendlyUrlPath("-%.+\\*_");

		try {
			testPostSiteSitePage_addSitePage(randomSitePage);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"LayoutFriendlyURLsException", problem.getType());
		}
	}

	private void _testPostSiteSitePageFailureFriendlyURLEndsWithSlash()
		throws Exception {

		SitePage randomSitePage = randomSitePage();

		randomSitePage.setFriendlyUrlPath(
			RandomTestUtil.randomString() + StringPool.FORWARD_SLASH);

		try {
			testPostSiteSitePage_addSitePage(randomSitePage);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"LayoutFriendlyURLsException", problem.getType());
		}
	}

	private void _testPostSiteSitePageFailureFriendlyURLTooLong()
		throws Exception {

		SitePage randomSitePage = randomSitePage();

		randomSitePage.setFriendlyUrlPath(
			RandomTestUtil.randomString(
				LayoutConstants.FRIENDLY_URL_MAX_LENGTH + 1));

		try {
			testPostSiteSitePage_addSitePage(randomSitePage);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"LayoutFriendlyURLsException", problem.getType());
		}
	}

	private void _testPostSiteSitePageFailureFriendlyURLTooShort()
		throws Exception {

		SitePage randomSitePage = randomSitePage();

		randomSitePage.setFriendlyUrlPath("a");

		try {
			testPostSiteSitePage_addSitePage(randomSitePage);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"LayoutFriendlyURLsException", problem.getType());
		}
	}

	private void _testPostSiteSitePageFailureInvalidTitle() throws Exception {
		SitePage randomSitePage = randomSitePage();

		int maxLength = ModelHintsUtil.getMaxLength(
			Layout.class.getName(), "friendlyURL");

		randomSitePage.setTitle(RandomTestUtil.randomString(maxLength + 1));

		try {
			testPostSiteSitePage_addSitePage(randomSitePage);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals("LayoutNameException", problem.getType());
		}
	}

	private void _testPostSiteSitePageSuccessInvalidParentSitePage()
		throws Exception {

		SitePage randomSitePage = randomSitePage();

		randomSitePage.setParentSitePage(
			new ParentSitePage() {
				{
					friendlyUrlPath = RandomTestUtil.randomString();
				}
			});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_EXCEPTION_MAPPER, LoggerTestUtil.WARN)) {

			SitePage postSitePage = testPostSiteSitePage_addSitePage(
				randomSitePage);

			Layout layout = _layoutLocalService.fetchLayout(
				postSitePage.getId());

			Assert.assertNotNull(layout);

			Assert.assertEquals(0, layout.getParentPlid());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Could not find parent site page", logEntry.getMessage());
		}
	}

	private void _testPostSiteSitePageSuccessKeywords() throws Exception {
		SitePage randomSitePage = randomSitePage();

		String[] keywords = {
			RandomTestUtil.randomString(), RandomTestUtil.randomString()
		};

		randomSitePage.setKeywords(keywords);

		SitePage postSitePage = testPostSiteSitePage_addSitePage(
			randomSitePage);

		Layout layout = _layoutLocalService.fetchLayout(postSitePage.getId());

		Assert.assertNotNull(layout);

		String[] tags = ListUtil.toArray(
			_assetTagLocalService.getTags(
				Layout.class.getName(), layout.getPlid()),
			AssetTag.NAME_ACCESSOR);

		Assert.assertEquals(tags.toString(), 2, tags.length);

		for (String keyword : keywords) {
			Assert.assertTrue(
				ArrayUtil.contains(tags, StringUtil.toLowerCase(keyword)));
		}
	}

	private static final String _CLASS_NAME_EXCEPTION_MAPPER =
		"com.liferay.headless.delivery.internal.resource.v1_0." +
			"SitePageResourceImpl";

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private LayoutsImporter _layoutsImporter;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}