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

package com.liferay.notification.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientModel;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.Validator;

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
 * The base model implementation for the NotificationRecipient service. Represents a row in the &quot;NotificationRecipient&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>NotificationRecipientModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link NotificationRecipientImpl}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientImpl
 * @generated
 */
public class NotificationRecipientModelImpl
	extends BaseModelImpl<NotificationRecipient>
	implements NotificationRecipientModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a notification recipient model instance should use the <code>NotificationRecipient</code> interface instead.
	 */
	public static final String TABLE_NAME = "NotificationRecipient";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"notificationRecipientId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("notificationRecipientId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table NotificationRecipient (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,notificationRecipientId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG)";

	public static final String TABLE_SQL_DROP =
		"drop table NotificationRecipient";

	public static final String ORDER_BY_JPQL =
		" ORDER BY notificationRecipient.notificationRecipientId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY NotificationRecipient.notificationRecipientId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long CLASSPK_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long NOTIFICATIONRECIPIENTID_COLUMN_BITMASK = 8L;

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

	public NotificationRecipientModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _notificationRecipientId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setNotificationRecipientId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _notificationRecipientId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return NotificationRecipient.class;
	}

	@Override
	public String getModelClassName() {
		return NotificationRecipient.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<NotificationRecipient, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<NotificationRecipient, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<NotificationRecipient, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((NotificationRecipient)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<NotificationRecipient, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<NotificationRecipient, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(NotificationRecipient)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<NotificationRecipient, Object>>
		getAttributeGetterFunctions() {

		return AttributeGetterFunctionsHolder._attributeGetterFunctions;
	}

	public Map<String, BiConsumer<NotificationRecipient, Object>>
		getAttributeSetterBiConsumers() {

		return AttributeSetterBiConsumersHolder._attributeSetterBiConsumers;
	}

	private static class AttributeGetterFunctionsHolder {

		private static final Map
			<String, Function<NotificationRecipient, Object>>
				_attributeGetterFunctions;

		static {
			Map<String, Function<NotificationRecipient, Object>>
				attributeGetterFunctions =
					new LinkedHashMap
						<String, Function<NotificationRecipient, Object>>();

			attributeGetterFunctions.put(
				"mvccVersion", NotificationRecipient::getMvccVersion);
			attributeGetterFunctions.put(
				"uuid", NotificationRecipient::getUuid);
			attributeGetterFunctions.put(
				"notificationRecipientId",
				NotificationRecipient::getNotificationRecipientId);
			attributeGetterFunctions.put(
				"companyId", NotificationRecipient::getCompanyId);
			attributeGetterFunctions.put(
				"userId", NotificationRecipient::getUserId);
			attributeGetterFunctions.put(
				"userName", NotificationRecipient::getUserName);
			attributeGetterFunctions.put(
				"createDate", NotificationRecipient::getCreateDate);
			attributeGetterFunctions.put(
				"modifiedDate", NotificationRecipient::getModifiedDate);
			attributeGetterFunctions.put(
				"classNameId", NotificationRecipient::getClassNameId);
			attributeGetterFunctions.put(
				"classPK", NotificationRecipient::getClassPK);

			_attributeGetterFunctions = Collections.unmodifiableMap(
				attributeGetterFunctions);
		}

	}

	private static class AttributeSetterBiConsumersHolder {

		private static final Map
			<String, BiConsumer<NotificationRecipient, Object>>
				_attributeSetterBiConsumers;

		static {
			Map<String, BiConsumer<NotificationRecipient, ?>>
				attributeSetterBiConsumers =
					new LinkedHashMap
						<String, BiConsumer<NotificationRecipient, ?>>();

			attributeSetterBiConsumers.put(
				"mvccVersion",
				(BiConsumer<NotificationRecipient, Long>)
					NotificationRecipient::setMvccVersion);
			attributeSetterBiConsumers.put(
				"uuid",
				(BiConsumer<NotificationRecipient, String>)
					NotificationRecipient::setUuid);
			attributeSetterBiConsumers.put(
				"notificationRecipientId",
				(BiConsumer<NotificationRecipient, Long>)
					NotificationRecipient::setNotificationRecipientId);
			attributeSetterBiConsumers.put(
				"companyId",
				(BiConsumer<NotificationRecipient, Long>)
					NotificationRecipient::setCompanyId);
			attributeSetterBiConsumers.put(
				"userId",
				(BiConsumer<NotificationRecipient, Long>)
					NotificationRecipient::setUserId);
			attributeSetterBiConsumers.put(
				"userName",
				(BiConsumer<NotificationRecipient, String>)
					NotificationRecipient::setUserName);
			attributeSetterBiConsumers.put(
				"createDate",
				(BiConsumer<NotificationRecipient, Date>)
					NotificationRecipient::setCreateDate);
			attributeSetterBiConsumers.put(
				"modifiedDate",
				(BiConsumer<NotificationRecipient, Date>)
					NotificationRecipient::setModifiedDate);
			attributeSetterBiConsumers.put(
				"classNameId",
				(BiConsumer<NotificationRecipient, Long>)
					NotificationRecipient::setClassNameId);
			attributeSetterBiConsumers.put(
				"classPK",
				(BiConsumer<NotificationRecipient, Long>)
					NotificationRecipient::setClassPK);

			_attributeSetterBiConsumers = Collections.unmodifiableMap(
				(Map)attributeSetterBiConsumers);
		}

	}

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

	@Override
	public long getNotificationRecipientId() {
		return _notificationRecipientId;
	}

	@Override
	public void setNotificationRecipientId(long notificationRecipientId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_notificationRecipientId = notificationRecipientId;
	}

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

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_classNameId = classNameId;
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_classPK = classPK;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalClassPK() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("classPK"));
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(NotificationRecipient.class.getName()),
			getClassNameId());
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
			getCompanyId(), NotificationRecipient.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public NotificationRecipient toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, NotificationRecipient>
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
		NotificationRecipientImpl notificationRecipientImpl =
			new NotificationRecipientImpl();

		notificationRecipientImpl.setMvccVersion(getMvccVersion());
		notificationRecipientImpl.setUuid(getUuid());
		notificationRecipientImpl.setNotificationRecipientId(
			getNotificationRecipientId());
		notificationRecipientImpl.setCompanyId(getCompanyId());
		notificationRecipientImpl.setUserId(getUserId());
		notificationRecipientImpl.setUserName(getUserName());
		notificationRecipientImpl.setCreateDate(getCreateDate());
		notificationRecipientImpl.setModifiedDate(getModifiedDate());
		notificationRecipientImpl.setClassNameId(getClassNameId());
		notificationRecipientImpl.setClassPK(getClassPK());

		notificationRecipientImpl.resetOriginalValues();

		return notificationRecipientImpl;
	}

	@Override
	public NotificationRecipient cloneWithOriginalValues() {
		NotificationRecipientImpl notificationRecipientImpl =
			new NotificationRecipientImpl();

		notificationRecipientImpl.setMvccVersion(
			this.<Long>getColumnOriginalValue("mvccVersion"));
		notificationRecipientImpl.setUuid(
			this.<String>getColumnOriginalValue("uuid_"));
		notificationRecipientImpl.setNotificationRecipientId(
			this.<Long>getColumnOriginalValue("notificationRecipientId"));
		notificationRecipientImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		notificationRecipientImpl.setUserId(
			this.<Long>getColumnOriginalValue("userId"));
		notificationRecipientImpl.setUserName(
			this.<String>getColumnOriginalValue("userName"));
		notificationRecipientImpl.setCreateDate(
			this.<Date>getColumnOriginalValue("createDate"));
		notificationRecipientImpl.setModifiedDate(
			this.<Date>getColumnOriginalValue("modifiedDate"));
		notificationRecipientImpl.setClassNameId(
			this.<Long>getColumnOriginalValue("classNameId"));
		notificationRecipientImpl.setClassPK(
			this.<Long>getColumnOriginalValue("classPK"));

		return notificationRecipientImpl;
	}

	@Override
	public int compareTo(NotificationRecipient notificationRecipient) {
		long primaryKey = notificationRecipient.getPrimaryKey();

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

		if (!(object instanceof NotificationRecipient)) {
			return false;
		}

		NotificationRecipient notificationRecipient =
			(NotificationRecipient)object;

		long primaryKey = notificationRecipient.getPrimaryKey();

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
	public CacheModel<NotificationRecipient> toCacheModel() {
		NotificationRecipientCacheModel notificationRecipientCacheModel =
			new NotificationRecipientCacheModel();

		notificationRecipientCacheModel.mvccVersion = getMvccVersion();

		notificationRecipientCacheModel.uuid = getUuid();

		String uuid = notificationRecipientCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			notificationRecipientCacheModel.uuid = null;
		}

		notificationRecipientCacheModel.notificationRecipientId =
			getNotificationRecipientId();

		notificationRecipientCacheModel.companyId = getCompanyId();

		notificationRecipientCacheModel.userId = getUserId();

		notificationRecipientCacheModel.userName = getUserName();

		String userName = notificationRecipientCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			notificationRecipientCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			notificationRecipientCacheModel.createDate = createDate.getTime();
		}
		else {
			notificationRecipientCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			notificationRecipientCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			notificationRecipientCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		notificationRecipientCacheModel.classNameId = getClassNameId();

		notificationRecipientCacheModel.classPK = getClassPK();

		return notificationRecipientCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<NotificationRecipient, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<NotificationRecipient, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<NotificationRecipient, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply(
				(NotificationRecipient)this);

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

		private static final Function<InvocationHandler, NotificationRecipient>
			_escapedModelProxyProviderFunction =
				ProxyUtil.getProxyProviderFunction(
					NotificationRecipient.class, ModelWrapper.class);

	}

	private long _mvccVersion;
	private String _uuid;
	private long _notificationRecipientId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _classPK;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<NotificationRecipient, Object> function =
			AttributeGetterFunctionsHolder._attributeGetterFunctions.get(
				columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((NotificationRecipient)this);
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
		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put(
			"notificationRecipientId", _notificationRecipientId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("classNameId", _classNameId);
		_columnOriginalValues.put("classPK", _classPK);
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

		columnBitmasks.put("uuid_", 2L);

		columnBitmasks.put("notificationRecipientId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("userName", 32L);

		columnBitmasks.put("createDate", 64L);

		columnBitmasks.put("modifiedDate", 128L);

		columnBitmasks.put("classNameId", 256L);

		columnBitmasks.put("classPK", 512L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private NotificationRecipient _escapedModel;

}