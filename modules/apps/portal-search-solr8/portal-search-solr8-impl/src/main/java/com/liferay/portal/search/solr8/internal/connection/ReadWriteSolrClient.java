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

package com.liferay.portal.search.solr8.internal.connection;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.IsUpdateRequest;
import org.apache.solr.common.util.NamedList;

/**
 * @author Michael C. Han
 */
public class ReadWriteSolrClient extends SolrClient {

	public ReadWriteSolrClient(SolrClient writeSolrClient) {
		_writeSolrClient = writeSolrClient;

		_readSolrClient = null;
	}

	public ReadWriteSolrClient(
		SolrClient readSolrClient, SolrClient writeSolrClient) {

		_readSolrClient = readSolrClient;
		_writeSolrClient = writeSolrClient;
	}

	@Override
	public void close() throws IOException {
		if (_readSolrClient != null) {
			_readSolrClient.close();
		}

		if (_writeSolrClient != null) {
			_writeSolrClient.close();
		}
	}

	@Override
	public NamedList<Object> request(SolrRequest solrRequest, String collection)
		throws IOException, SolrServerException {

		if ((_readSolrClient != null) &&
			!(solrRequest instanceof IsUpdateRequest)) {

			return _readSolrClient.request(solrRequest, collection);
		}

		return _writeSolrClient.request(solrRequest, collection);
	}

	private final SolrClient _readSolrClient;
	private final SolrClient _writeSolrClient;

}