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

package com.liferay.object.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the ObjectStateTransition service. Represents a row in the &quot;ObjectStateTransition&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.object.model.impl.ObjectStateTransitionModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.object.model.impl.ObjectStateTransitionImpl</code>.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStateTransition
 * @generated
 */
@ProviderType
public interface ObjectStateTransitionModel
	extends BaseModel<ObjectStateTransition>, MVCCModel, ShardedModel,
			StagedAuditedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a object state transition model instance should use the {@link ObjectStateTransition} interface instead.
	 */

	/**
	 * Returns the primary key of this object state transition.
	 *
	 * @return the primary key of this object state transition
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this object state transition.
	 *
	 * @param primaryKey the primary key of this object state transition
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this object state transition.
	 *
	 * @return the mvcc version of this object state transition
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this object state transition.
	 *
	 * @param mvccVersion the mvcc version of this object state transition
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this object state transition.
	 *
	 * @return the uuid of this object state transition
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this object state transition.
	 *
	 * @param uuid the uuid of this object state transition
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the object state transition ID of this object state transition.
	 *
	 * @return the object state transition ID of this object state transition
	 */
	public long getObjectStateTransitionId();

	/**
	 * Sets the object state transition ID of this object state transition.
	 *
	 * @param objectStateTransitionId the object state transition ID of this object state transition
	 */
	public void setObjectStateTransitionId(long objectStateTransitionId);

	/**
	 * Returns the company ID of this object state transition.
	 *
	 * @return the company ID of this object state transition
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this object state transition.
	 *
	 * @param companyId the company ID of this object state transition
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this object state transition.
	 *
	 * @return the user ID of this object state transition
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this object state transition.
	 *
	 * @param userId the user ID of this object state transition
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this object state transition.
	 *
	 * @return the user uuid of this object state transition
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this object state transition.
	 *
	 * @param userUuid the user uuid of this object state transition
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this object state transition.
	 *
	 * @return the user name of this object state transition
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this object state transition.
	 *
	 * @param userName the user name of this object state transition
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this object state transition.
	 *
	 * @return the create date of this object state transition
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this object state transition.
	 *
	 * @param createDate the create date of this object state transition
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this object state transition.
	 *
	 * @return the modified date of this object state transition
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this object state transition.
	 *
	 * @param modifiedDate the modified date of this object state transition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the object state flow ID of this object state transition.
	 *
	 * @return the object state flow ID of this object state transition
	 */
	public long getObjectStateFlowId();

	/**
	 * Sets the object state flow ID of this object state transition.
	 *
	 * @param objectStateFlowId the object state flow ID of this object state transition
	 */
	public void setObjectStateFlowId(long objectStateFlowId);

	/**
	 * Returns the source object state ID of this object state transition.
	 *
	 * @return the source object state ID of this object state transition
	 */
	public long getSourceObjectStateId();

	/**
	 * Sets the source object state ID of this object state transition.
	 *
	 * @param sourceObjectStateId the source object state ID of this object state transition
	 */
	public void setSourceObjectStateId(long sourceObjectStateId);

	/**
	 * Returns the target object state ID of this object state transition.
	 *
	 * @return the target object state ID of this object state transition
	 */
	public long getTargetObjectStateId();

	/**
	 * Sets the target object state ID of this object state transition.
	 *
	 * @param targetObjectStateId the target object state ID of this object state transition
	 */
	public void setTargetObjectStateId(long targetObjectStateId);

	@Override
	public ObjectStateTransition cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}