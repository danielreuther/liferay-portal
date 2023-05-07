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

package com.liferay.layout.page.template.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureModel;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Blob;
import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the LayoutPageTemplateStructure service. Represents a row in the &quot;LayoutPageTemplateStructure&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>LayoutPageTemplateStructureModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LayoutPageTemplateStructureImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureImpl
 * @generated
 */
@JSON(strict = true)
public class LayoutPageTemplateStructureModelImpl
	extends BaseModelImpl<LayoutPageTemplateStructure>
	implements LayoutPageTemplateStructureModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout page template structure model instance should use the <code>LayoutPageTemplateStructure</code> interface instead.
	 */
	public static final String TABLE_NAME = "LayoutPageTemplateStructure";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR},
		{"layoutPageTemplateStructureId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"plid", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("layoutPageTemplateStructureId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table LayoutPageTemplateStructure (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,layoutPageTemplateStructureId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,plid LONG,primary key (layoutPageTemplateStructureId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
		"drop table LayoutPageTemplateStructure";

	public static final String ORDER_BY_JPQL =
		" ORDER BY layoutPageTemplateStructure.layoutPageTemplateStructureId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY LayoutPageTemplateStructure.layoutPageTemplateStructureId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long PLID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long LAYOUTPAGETEMPLATESTRUCTUREID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	public LayoutPageTemplateStructureModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _layoutPageTemplateStructureId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLayoutPageTemplateStructureId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutPageTemplateStructureId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPageTemplateStructure.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPageTemplateStructure.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<LayoutPageTemplateStructure, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<LayoutPageTemplateStructure, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LayoutPageTemplateStructure, Object>
				attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply(
					(LayoutPageTemplateStructure)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<LayoutPageTemplateStructure, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<LayoutPageTemplateStructure, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(LayoutPageTemplateStructure)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<LayoutPageTemplateStructure, Object>>
		getAttributeGetterFunctions() {

		return AttributeGetterFunctionsHolder._attributeGetterFunctions;
	}

	public Map<String, BiConsumer<LayoutPageTemplateStructure, Object>>
		getAttributeSetterBiConsumers() {

		return AttributeSetterBiConsumersHolder._attributeSetterBiConsumers;
	}

	private static class AttributeGetterFunctionsHolder {

		private static final Map
			<String, Function<LayoutPageTemplateStructure, Object>>
				_attributeGetterFunctions;

		static {
			Map<String, Function<LayoutPageTemplateStructure, Object>>
				attributeGetterFunctions =
					new LinkedHashMap
						<String,
						 Function<LayoutPageTemplateStructure, Object>>();

			attributeGetterFunctions.put(
				"mvccVersion", LayoutPageTemplateStructure::getMvccVersion);
			attributeGetterFunctions.put(
				"ctCollectionId",
				LayoutPageTemplateStructure::getCtCollectionId);
			attributeGetterFunctions.put(
				"uuid", LayoutPageTemplateStructure::getUuid);
			attributeGetterFunctions.put(
				"layoutPageTemplateStructureId",
				LayoutPageTemplateStructure::getLayoutPageTemplateStructureId);
			attributeGetterFunctions.put(
				"groupId", LayoutPageTemplateStructure::getGroupId);
			attributeGetterFunctions.put(
				"companyId", LayoutPageTemplateStructure::getCompanyId);
			attributeGetterFunctions.put(
				"userId", LayoutPageTemplateStructure::getUserId);
			attributeGetterFunctions.put(
				"userName", LayoutPageTemplateStructure::getUserName);
			attributeGetterFunctions.put(
				"createDate", LayoutPageTemplateStructure::getCreateDate);
			attributeGetterFunctions.put(
				"modifiedDate", LayoutPageTemplateStructure::getModifiedDate);
			attributeGetterFunctions.put(
				"plid", LayoutPageTemplateStructure::getPlid);

			_attributeGetterFunctions = Collections.unmodifiableMap(
				attributeGetterFunctions);
		}

	}

	private static class AttributeSetterBiConsumersHolder {

		private static final Map
			<String, BiConsumer<LayoutPageTemplateStructure, Object>>
				_attributeSetterBiConsumers;

		static {
			Map<String, BiConsumer<LayoutPageTemplateStructure, ?>>
				attributeSetterBiConsumers =
					new LinkedHashMap
						<String, BiConsumer<LayoutPageTemplateStructure, ?>>();

			attributeSetterBiConsumers.put(
				"mvccVersion",
				(BiConsumer<LayoutPageTemplateStructure, Long>)
					LayoutPageTemplateStructure::setMvccVersion);
			attributeSetterBiConsumers.put(
				"ctCollectionId",
				(BiConsumer<LayoutPageTemplateStructure, Long>)
					LayoutPageTemplateStructure::setCtCollectionId);
			attributeSetterBiConsumers.put(
				"uuid",
				(BiConsumer<LayoutPageTemplateStructure, String>)
					LayoutPageTemplateStructure::setUuid);
			attributeSetterBiConsumers.put(
				"layoutPageTemplateStructureId",
				(BiConsumer<LayoutPageTemplateStructure, Long>)
					LayoutPageTemplateStructure::
						setLayoutPageTemplateStructureId);
			attributeSetterBiConsumers.put(
				"groupId",
				(BiConsumer<LayoutPageTemplateStructure, Long>)
					LayoutPageTemplateStructure::setGroupId);
			attributeSetterBiConsumers.put(
				"companyId",
				(BiConsumer<LayoutPageTemplateStructure, Long>)
					LayoutPageTemplateStructure::setCompanyId);
			attributeSetterBiConsumers.put(
				"userId",
				(BiConsumer<LayoutPageTemplateStructure, Long>)
					LayoutPageTemplateStructure::setUserId);
			attributeSetterBiConsumers.put(
				"userName",
				(BiConsumer<LayoutPageTemplateStructure, String>)
					LayoutPageTemplateStructure::setUserName);
			attributeSetterBiConsumers.put(
				"createDate",
				(BiConsumer<LayoutPageTemplateStructure, Date>)
					LayoutPageTemplateStructure::setCreateDate);
			attributeSetterBiConsumers.put(
				"modifiedDate",
				(BiConsumer<LayoutPageTemplateStructure, Date>)
					LayoutPageTemplateStructure::setModifiedDate);
			attributeSetterBiConsumers.put(
				"plid",
				(BiConsumer<LayoutPageTemplateStructure, Long>)
					LayoutPageTemplateStructure::setPlid);

			_attributeSetterBiConsumers = Collections.unmodifiableMap(
				(Map)attributeSetterBiConsumers);
		}

	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_ctCollectionId = ctCollectionId;
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_uuid = uuid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalUuid() {
		return getColumnOriginalValue("uuid_");
	}

	@JSON
	@Override
	public long getLayoutPageTemplateStructureId() {
		return _layoutPageTemplateStructureId;
	}

	@Override
	public void setLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId) {

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_layoutPageTemplateStructureId = layoutPageTemplateStructureId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_groupId = groupId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalGroupId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("groupId"));
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("companyId"));
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getPlid() {
		return _plid;
	}

	@Override
	public void setPlid(long plid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_plid = plid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalPlid() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("plid"));
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(
				LayoutPageTemplateStructure.class.getName()));
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (!Objects.equals(
					entry.getValue(), getColumnValue(entry.getKey()))) {

				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), LayoutPageTemplateStructure.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public LayoutPageTemplateStructure toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, LayoutPageTemplateStructure>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		LayoutPageTemplateStructureImpl layoutPageTemplateStructureImpl =
			new LayoutPageTemplateStructureImpl();

		layoutPageTemplateStructureImpl.setMvccVersion(getMvccVersion());
		layoutPageTemplateStructureImpl.setCtCollectionId(getCtCollectionId());
		layoutPageTemplateStructureImpl.setUuid(getUuid());
		layoutPageTemplateStructureImpl.setLayoutPageTemplateStructureId(
			getLayoutPageTemplateStructureId());
		layoutPageTemplateStructureImpl.setGroupId(getGroupId());
		layoutPageTemplateStructureImpl.setCompanyId(getCompanyId());
		layoutPageTemplateStructureImpl.setUserId(getUserId());
		layoutPageTemplateStructureImpl.setUserName(getUserName());
		layoutPageTemplateStructureImpl.setCreateDate(getCreateDate());
		layoutPageTemplateStructureImpl.setModifiedDate(getModifiedDate());
		layoutPageTemplateStructureImpl.setPlid(getPlid());

		layoutPageTemplateStructureImpl.resetOriginalValues();

		return layoutPageTemplateStructureImpl;
	}

	@Override
	public LayoutPageTemplateStructure cloneWithOriginalValues() {
		LayoutPageTemplateStructureImpl layoutPageTemplateStructureImpl =
			new LayoutPageTemplateStructureImpl();

		layoutPageTemplateStructureImpl.setMvccVersion(
			this.<Long>getColumnOriginalValue("mvccVersion"));
		layoutPageTemplateStructureImpl.setCtCollectionId(
			this.<Long>getColumnOriginalValue("ctCollectionId"));
		layoutPageTemplateStructureImpl.setUuid(
			this.<String>getColumnOriginalValue("uuid_"));
		layoutPageTemplateStructureImpl.setLayoutPageTemplateStructureId(
			this.<Long>getColumnOriginalValue("layoutPageTemplateStructureId"));
		layoutPageTemplateStructureImpl.setGroupId(
			this.<Long>getColumnOriginalValue("groupId"));
		layoutPageTemplateStructureImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		layoutPageTemplateStructureImpl.setUserId(
			this.<Long>getColumnOriginalValue("userId"));
		layoutPageTemplateStructureImpl.setUserName(
			this.<String>getColumnOriginalValue("userName"));
		layoutPageTemplateStructureImpl.setCreateDate(
			this.<Date>getColumnOriginalValue("createDate"));
		layoutPageTemplateStructureImpl.setModifiedDate(
			this.<Date>getColumnOriginalValue("modifiedDate"));
		layoutPageTemplateStructureImpl.setPlid(
			this.<Long>getColumnOriginalValue("plid"));

		return layoutPageTemplateStructureImpl;
	}

	@Override
	public int compareTo(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {

		long primaryKey = layoutPageTemplateStructure.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutPageTemplateStructure)) {
			return false;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			(LayoutPageTemplateStructure)object;

		long primaryKey = layoutPageTemplateStructure.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<LayoutPageTemplateStructure> toCacheModel() {
		LayoutPageTemplateStructureCacheModel
			layoutPageTemplateStructureCacheModel =
				new LayoutPageTemplateStructureCacheModel();

		layoutPageTemplateStructureCacheModel.mvccVersion = getMvccVersion();

		layoutPageTemplateStructureCacheModel.ctCollectionId =
			getCtCollectionId();

		layoutPageTemplateStructureCacheModel.uuid = getUuid();

		String uuid = layoutPageTemplateStructureCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			layoutPageTemplateStructureCacheModel.uuid = null;
		}

		layoutPageTemplateStructureCacheModel.layoutPageTemplateStructureId =
			getLayoutPageTemplateStructureId();

		layoutPageTemplateStructureCacheModel.groupId = getGroupId();

		layoutPageTemplateStructureCacheModel.companyId = getCompanyId();

		layoutPageTemplateStructureCacheModel.userId = getUserId();

		layoutPageTemplateStructureCacheModel.userName = getUserName();

		String userName = layoutPageTemplateStructureCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			layoutPageTemplateStructureCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			layoutPageTemplateStructureCacheModel.createDate =
				createDate.getTime();
		}
		else {
			layoutPageTemplateStructureCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			layoutPageTemplateStructureCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			layoutPageTemplateStructureCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		layoutPageTemplateStructureCacheModel.plid = getPlid();

		return layoutPageTemplateStructureCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<LayoutPageTemplateStructure, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<LayoutPageTemplateStructure, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LayoutPageTemplateStructure, Object>
				attributeGetterFunction = entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply(
				(LayoutPageTemplateStructure)this);

			if (value == null) {
				sb.append("null");
			}
			else if (value instanceof Blob || value instanceof Date ||
					 value instanceof Map || value instanceof String) {

				sb.append(
					"\"" + StringUtil.replace(value.toString(), "\"", "'") +
						"\"");
			}
			else {
				sb.append(value);
			}

			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, LayoutPageTemplateStructure>
				_escapedModelProxyProviderFunction =
					ProxyUtil.getProxyProviderFunction(
						LayoutPageTemplateStructure.class, ModelWrapper.class);

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _layoutPageTemplateStructureId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _plid;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<LayoutPageTemplateStructure, Object> function =
			AttributeGetterFunctionsHolder._attributeGetterFunctions.get(
				columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((LayoutPageTemplateStructure)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("mvccVersion", _mvccVersion);
		_columnOriginalValues.put("ctCollectionId", _ctCollectionId);
		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put(
			"layoutPageTemplateStructureId", _layoutPageTemplateStructureId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("plid", _plid);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("ctCollectionId", 2L);

		columnBitmasks.put("uuid_", 4L);

		columnBitmasks.put("layoutPageTemplateStructureId", 8L);

		columnBitmasks.put("groupId", 16L);

		columnBitmasks.put("companyId", 32L);

		columnBitmasks.put("userId", 64L);

		columnBitmasks.put("userName", 128L);

		columnBitmasks.put("createDate", 256L);

		columnBitmasks.put("modifiedDate", 512L);

		columnBitmasks.put("plid", 1024L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private LayoutPageTemplateStructure _escapedModel;

}