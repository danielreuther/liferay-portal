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

package com.liferay.site.navigation.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.site.navigation.model.SiteNavigationMenuItemTable;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuItemImpl;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuItemModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from SiteNavigationMenuItem.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.site.navigation.model.impl.SiteNavigationMenuItemImpl",
		"table.name=SiteNavigationMenuItem"
	},
	service = ArgumentsResolver.class
)
public class SiteNavigationMenuItemModelArgumentsResolver
	implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		SiteNavigationMenuItemModelImpl siteNavigationMenuItemModelImpl =
			(SiteNavigationMenuItemModelImpl)baseModel;

		long columnBitmask = siteNavigationMenuItemModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				siteNavigationMenuItemModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					siteNavigationMenuItemModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				siteNavigationMenuItemModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return SiteNavigationMenuItemImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return SiteNavigationMenuItemTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		SiteNavigationMenuItemModelImpl siteNavigationMenuItemModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					siteNavigationMenuItemModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = siteNavigationMenuItemModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}