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

package com.liferay.portal.search.internal.permission;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.FacetPostProcessor;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.search.configuration.DefaultSearchResultPermissionFilterConfiguration;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class DefaultSearchResultPermissionFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSearchGroupAdmin() {
		_groupAdmin = true;
		_permissionFilteredSearchResultAccurateCountThreshold = 0;

		DefaultSearchResultPermissionFilter
			defaultSearchResultPermissionFilter =
				_getDefaultSearchResultPermissionFilter();

		SearchContext searchContext = _getSearchContext(4);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 4, 10);

		searchContext = _getSearchContext(8);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 8, 10);

		searchContext = _getSearchContext(10);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 10, 10);
	}

	@Test
	public void testSearchGuestWithoutThreshold() {
		_groupAdmin = false;
		_permissionFilteredSearchResultAccurateCountThreshold = 0;

		DefaultSearchResultPermissionFilter
			defaultSearchResultPermissionFilter =
				_getDefaultSearchResultPermissionFilter();

		SearchContext searchContext = _getSearchContext(4);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 4, 10);

		searchContext = _getSearchContext(8);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 8, 10);

		searchContext = _getSearchContext(10);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 9, 9);
	}

	@Test
	public void testSearchGuestWithThreshold() {
		_groupAdmin = false;
		_permissionFilteredSearchResultAccurateCountThreshold = 20;

		DefaultSearchResultPermissionFilter
			defaultSearchResultPermissionFilter =
				_getDefaultSearchResultPermissionFilter();

		SearchContext searchContext = _getSearchContext(4);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 4, 9);

		searchContext = _getSearchContext(8);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 8, 9);

		searchContext = _getSearchContext(10);

		_assertPagination(
			searchContext, defaultSearchResultPermissionFilter, 9, 9);
	}

	private void _assertPagination(
		SearchContext searchContext,
		DefaultSearchResultPermissionFilter defaultSearchResultPermissionFilter,
		int pageCount, int totalCount) {

		Hits searchHits = defaultSearchResultPermissionFilter.search(
			searchContext);

		Document[] docs = searchHits.getDocs();

		Assert.assertEquals(
			searchHits.toString(), totalCount, searchHits.getLength());

		Assert.assertEquals(Arrays.toString(docs), pageCount, docs.length);
	}

	private DefaultSearchResultPermissionFilter
		_getDefaultSearchResultPermissionFilter() {

		_mockSearchResultPermissionFilterConfiguration();

		SearchRequestBuilderFactory searchRequestBuilderFactory =
			_getSearchRequestBuilderFactory();

		return new DefaultSearchResultPermissionFilter(
			Mockito.mock(FacetPostProcessor.class),
			Mockito.mock(IndexerRegistry.class), _permissionChecker,
			Mockito.mock(Props.class),
			Mockito.mock(RelatedEntryIndexerRegistry.class), _searchFunction,
			searchRequestBuilderFactory,
			_searchResultPermissionFilterConfiguration);
	}

	private Document _getDocument(String companyId, int index) {
		Document document = Mockito.mock(Document.class);

		Mockito.when(
			document.get(Field.COMPANY_ID)
		).thenReturn(
			companyId
		);

		Mockito.when(
			document.get(Field.ENTRY_CLASS_NAME)
		).thenReturn(
			"com.liferay.journal.model.JournalArticle"
		);

		Mockito.when(
			document.get(Field.ENTRY_CLASS_PK)
		).thenReturn(
			String.valueOf(index)
		);

		return document;
	}

	private Hits _getHits(int start, int end) {
		if (_permissionFilteredSearchResultAccurateCountThreshold > 0) {
			return _getHitsWithPermissionFilteredSearchResultAccurateCountThreshold();
		}

		Hits hits = new HitsImpl();

		int size = end - start;

		Document[] docs = new Document[size];

		for (int i = start; i < end; i++) {
			docs[i] = _documents[i];
		}

		float[] scores = new float[size];

		for (int i = 0; i < size; i++) {
			scores[i] = i;
		}

		hits.setScores(scores);

		hits.setDocs(docs);

		hits.setLength(_documents.length);

		return Mockito.spy(hits);
	}

	private Hits
		_getHitsWithPermissionFilteredSearchResultAccurateCountThreshold() {

		Hits hits = new HitsImpl();

		float[] scores = new float[_documents.length];

		for (int i = 0; i < _documents.length; i++) {
			scores[i] = i;
		}

		hits.setScores(scores);

		hits.setDocs(_documents);

		hits.setLength(_documents.length);

		return Mockito.spy(hits);
	}

	private SearchContext _getSearchContext(int end) {
		SearchContext searchContext = new SearchContext();

		_mockPermission(searchContext);

		_setUpDocuments();

		searchContext.setEnd(end);
		searchContext.setStart(0);

		Mockito.when(
			_searchFunction.apply(searchContext)
		).thenReturn(
			_getHits(0, end)
		);

		return searchContext;
	}

	private SearchRequestBuilderFactory _getSearchRequestBuilderFactory() {
		SearchRequestBuilderFactory searchRequestBuilderFactory = Mockito.mock(
			SearchRequestBuilderFactory.class);

		SearchRequestBuilder searchRequestBuilder = Mockito.mock(
			SearchRequestBuilder.class);

		Mockito.when(
			searchRequestBuilderFactory.builder(Mockito.any())
		).thenReturn(
			searchRequestBuilder
		);

		Mockito.when(
			searchRequestBuilder.build()
		).thenReturn(
			_searchRequest
		);

		return searchRequestBuilderFactory;
	}

	private void _mockPermission(SearchContext searchContext) {
		searchContext.setAttribute(Field.STATUS, 1);

		searchContext.setAttribute(Field.GROUP_ID, _USER_GROUP_ID);

		Mockito.when(
			_permissionChecker.getCompanyId()
		).thenReturn(
			_USER_GROUP_ID
		);

		Mockito.when(
			_permissionChecker.isGroupAdmin(_USER_GROUP_ID)
		).thenReturn(
			_groupAdmin
		);
	}

	private void _mockSearchResultPermissionFilterConfiguration() {
		Mockito.when(
			_searchResultPermissionFilterConfiguration.
				permissionFilteredSearchResultAccurateCountThreshold()
		).thenReturn(
			_permissionFilteredSearchResultAccurateCountThreshold
		);

		Mockito.when(
			_searchResultPermissionFilterConfiguration.
				searchQueryResultWindowLimit()
		).thenReturn(
			100
		);
	}

	private void _setUpDocuments() {
		_documents = new Document[1 + 9];

		for (int i = 0; i < 9; i++) {
			Document document = _getDocument("1", i);

			_documents[i] = document;
		}

		for (int i = 9; i < _documents.length; i++) {
			Document document = _getDocument("0", i);

			_documents[i] = document;
		}
	}

	private static final long _USER_GROUP_ID = 1L;

	private static final SearchRequest _searchRequest = Mockito.mock(
		SearchRequest.class);

	private Document[] _documents;
	private boolean _groupAdmin;
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private int _permissionFilteredSearchResultAccurateCountThreshold;
	private final Function<SearchContext, Hits> _searchFunction = Mockito.mock(
		Function.class);
	private final DefaultSearchResultPermissionFilterConfiguration
		_searchResultPermissionFilterConfiguration = Mockito.mock(
			DefaultSearchResultPermissionFilterConfiguration.class);

}