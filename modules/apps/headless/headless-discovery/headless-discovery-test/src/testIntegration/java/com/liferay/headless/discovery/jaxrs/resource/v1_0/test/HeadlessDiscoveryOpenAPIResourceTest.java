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

package com.liferay.headless.discovery.jaxrs.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class HeadlessDiscoveryOpenAPIResourceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);
		_objectDefinition2 = _publishObjectDefinition(
			ObjectDefinitionConstants.SCOPE_SITE);
	}

	@Test
	public void testGetGlobalOpenAPI() throws Exception {
		List<String> globalOpenAPIPaths = _getPaths(
			HTTPTestUtil.invoke(null, "openapi/openapi.json", Http.Method.GET));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, "openapi", Http.Method.GET);

		Map<String, Object> map = jsonObject.toMap();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			for (String openAPIPath : (List<String>)entry.getValue()) {
				for (String path :
						_getPaths(
							HTTPTestUtil.invoke(
								null, _getOpenAPISubpath(openAPIPath),
								Http.Method.GET))) {

					if (path.endsWith("/")) {
						path = path.substring(0, path.lastIndexOf("/"));
					}

					Assert.assertTrue(
						globalOpenAPIPaths.remove(entry.getKey() + path));
				}
			}
		}

		Assert.assertTrue(globalOpenAPIPaths.isEmpty());
	}

	private String _getOpenAPISubpath(String openAPIPath) {
		String openAPISubpath = StringUtil.removeFirst(
			openAPIPath, "http://localhost:8080/o/");

		return StringUtil.replaceLast(openAPISubpath, ".yaml", ".json");
	}

	private List<String> _getPaths(JSONObject openAPIJSONObject) {
		JSONObject pathsJSONObject = openAPIJSONObject.getJSONObject("paths");

		Map<String, Object> map = pathsJSONObject.toMap();

		return new ArrayList<>(map.keySet());
	}

	private ObjectDefinition _publishObjectDefinition(String scope)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}