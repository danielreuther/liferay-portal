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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.dao.sql.transformer.HQLToJPQLTransformerLogic;
import com.liferay.portal.dao.sql.transformer.JPQLToHQLTransformerLogic;
import com.liferay.portal.dao.sql.transformer.SQLTransformerFactory;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import java.util.function.Function;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Manuel de la Peña
 */
public class SQLTransformer {

	public static void reloadSQLTransformer() {
		_instance._reloadSQLTransformer();
	}

	public static String transform(String sql) {
		com.liferay.portal.dao.sql.transformer.SQLTransformer sqlTransformer =
			_instance._getSQLTransformer();

		return sqlTransformer.transform(sql);
	}

	public static String transformFromHQLToJQPL(String sql) {
		return _instance._transformFromHQLToJPQL(sql);
	}

	public static String transformFromJPQLToHQL(String sql) {
		return _instance._transformFromJPQLToHQL(sql);
	}

	private SQLTransformer() {
		_reloadSQLTransformer();
	}

	private com.liferay.portal.dao.sql.transformer.SQLTransformer
		_getSQLTransformer() {

		return _sqlTransformer;
	}

	private void _reloadSQLTransformer() {
		if (_transformedSQLsPortalCache == null) {
			_transformedSQLsPortalCache = PortalCacheHelperUtil.getPortalCache(
				PortalCacheManagerNames.SINGLE_VM,
				SQLTransformer.class.getName());
		}
		else {
			_transformedSQLsPortalCache.removeAll();
		}

		_sqlTransformer = SQLTransformerFactory.getSQLTransformer(
			DBManagerUtil.getDB());
	}

	private String _transformFromHQLToJPQL(String sql) {
		String newSQL = _transformedSQLsPortalCache.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _sqlTransformer.transform(sql);

		Function[] functions = {
			HQLToJPQLTransformerLogic.getPositionalParameterFunction(),
			HQLToJPQLTransformerLogic.getNotEqualsFunction(),
			HQLToJPQLTransformerLogic.getCompositeIdMarkerFunction()
		};

		for (Function<String, String> function : functions) {
			newSQL = function.apply(newSQL);
		}

		_transformedSQLsPortalCache.put(sql, newSQL);

		return newSQL;
	}

	private String _transformFromJPQLToHQL(String sql) {
		String newSQL = _transformedSQLsPortalCache.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _sqlTransformer.transform(sql);

		Function<String, String> countFunction =
			JPQLToHQLTransformerLogic.getCountFunction();

		newSQL = countFunction.apply(newSQL);

		_transformedSQLsPortalCache.put(sql, newSQL);

		return newSQL;
	}

	private static final SQLTransformer _instance = new SQLTransformer();

	private com.liferay.portal.dao.sql.transformer.SQLTransformer
		_sqlTransformer;
	private PortalCache<String, String> _transformedSQLsPortalCache;

}