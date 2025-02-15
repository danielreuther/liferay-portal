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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.version.VersionModel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the LVEntryVersion service. Represents a row in the &quot;LVEntryVersion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.tools.service.builder.test.model.impl.LVEntryVersionModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.tools.service.builder.test.model.impl.LVEntryVersionImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersion
 * @generated
 */
@ProviderType
public interface LVEntryVersionModel
	extends BaseModel<LVEntryVersion>, ShardedModel, VersionModel<LVEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a lv entry version model instance should use the {@link LVEntryVersion} interface instead.
	 */

	/**
	 * Returns the primary key of this lv entry version.
	 *
	 * @return the primary key of this lv entry version
	 */
	@Override
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this lv entry version.
	 *
	 * @param primaryKey the primary key of this lv entry version
	 */
	@Override
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the lv entry version ID of this lv entry version.
	 *
	 * @return the lv entry version ID of this lv entry version
	 */
	public long getLvEntryVersionId();

	/**
	 * Sets the lv entry version ID of this lv entry version.
	 *
	 * @param lvEntryVersionId the lv entry version ID of this lv entry version
	 */
	public void setLvEntryVersionId(long lvEntryVersionId);

	/**
	 * Returns the version of this lv entry version.
	 *
	 * @return the version of this lv entry version
	 */
	@Override
	public int getVersion();

	/**
	 * Sets the version of this lv entry version.
	 *
	 * @param version the version of this lv entry version
	 */
	@Override
	public void setVersion(int version);

	/**
	 * Returns the uuid of this lv entry version.
	 *
	 * @return the uuid of this lv entry version
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this lv entry version.
	 *
	 * @param uuid the uuid of this lv entry version
	 */
	public void setUuid(String uuid);

	/**
	 * Returns the default language ID of this lv entry version.
	 *
	 * @return the default language ID of this lv entry version
	 */
	@AutoEscape
	public String getDefaultLanguageId();

	/**
	 * Sets the default language ID of this lv entry version.
	 *
	 * @param defaultLanguageId the default language ID of this lv entry version
	 */
	public void setDefaultLanguageId(String defaultLanguageId);

	/**
	 * Returns the lv entry ID of this lv entry version.
	 *
	 * @return the lv entry ID of this lv entry version
	 */
	public long getLvEntryId();

	/**
	 * Sets the lv entry ID of this lv entry version.
	 *
	 * @param lvEntryId the lv entry ID of this lv entry version
	 */
	public void setLvEntryId(long lvEntryId);

	/**
	 * Returns the company ID of this lv entry version.
	 *
	 * @return the company ID of this lv entry version
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this lv entry version.
	 *
	 * @param companyId the company ID of this lv entry version
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the group ID of this lv entry version.
	 *
	 * @return the group ID of this lv entry version
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this lv entry version.
	 *
	 * @param groupId the group ID of this lv entry version
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the unique group key of this lv entry version.
	 *
	 * @return the unique group key of this lv entry version
	 */
	@AutoEscape
	public String getUniqueGroupKey();

	/**
	 * Sets the unique group key of this lv entry version.
	 *
	 * @param uniqueGroupKey the unique group key of this lv entry version
	 */
	public void setUniqueGroupKey(String uniqueGroupKey);

	@Override
	public LVEntryVersion cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}