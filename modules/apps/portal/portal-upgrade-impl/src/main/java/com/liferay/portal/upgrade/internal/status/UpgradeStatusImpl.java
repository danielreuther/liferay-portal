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

package com.liferay.portal.upgrade.internal.status;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.ReleaseManager;
import com.liferay.portal.kernel.upgrade.UpgradeStatus;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.logging.log4j.ThreadContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Luis Ortiz
 */
@Component(service = {UpgradeStatus.class, UpgradeStatusImpl.class})
public class UpgradeStatusImpl implements UpgradeStatus {

	public void addErrorMessage(String loggerName, String message) {
		Map<String, Integer> messages = _errorMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = messages.computeIfAbsent(message, key -> 0);

		occurrences++;

		messages.put(message, occurrences);
	}

	public void addUpgradeProcessMessage(String loggerName, String message) {
		List<String> messages = _upgradeProcessMessages.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		messages.add(message);
	}

	public void addWarningMessage(String loggerName, String message) {
		Map<String, Integer> messages = _warningMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = messages.computeIfAbsent(message, key -> 0);

		occurrences++;

		messages.put(message, occurrences);
	}

	public void finish() {
		_processRelease(
			(moduleSchemaVersions, schemaVersion) ->
				moduleSchemaVersions.setFinal(schemaVersion));

		_setFinalState();
		_setType();

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			ThreadContext.put("upgrade.type", _type);
			ThreadContext.put("upgrade.result", _state);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Upgrade of type ", _type, " finished with state ",
					_state));
		}

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			ThreadContext.clearMap();
		}
	}

	public Map<String, Map<String, Integer>> getErrorMessages() {
		_filterMessages();

		return _errorMessages;
	}

	public String getFinalSchemaVersion(String servletContextName) {
		SchemaVersions schemaVersions = _schemaVersionsMap.get(
			servletContextName);

		if (schemaVersions == null) {
			return null;
		}

		return schemaVersions.getFinal();
	}

	public String getInitialSchemaVersion(String servletContextName) {
		SchemaVersions schemaVersions = _schemaVersionsMap.get(
			servletContextName);

		if (schemaVersions == null) {
			return null;
		}

		return schemaVersions.getInitial();
	}

	@Override
	public String getState() {
		return _state;
	}

	@Override
	public String getType() {
		return _type;
	}

	public Map<String, ArrayList<String>> getUpgradeProcessMessages() {
		return _upgradeProcessMessages;
	}

	public Map<String, Map<String, Integer>> getWarningMessages() {
		_filterMessages();

		return _warningMessages;
	}

	public void start() {
		_state = "Running";

		_processRelease(
			(moduleSchemaVersions, schemaVersion) ->
				moduleSchemaVersions.setInitial(schemaVersion));
	}

	private void _filterMessages() {
		if (!_filtered) {
			for (String filteredClassName : _FILTERED_CLASS_NAMES) {
				_errorMessages.remove(filteredClassName);
				_warningMessages.remove(filteredClassName);
			}

			_filtered = true;
		}
	}

	private void _processRelease(
		UnsafeBiConsumer<SchemaVersions, String, Exception> unsafeBiConsumer) {

		DataSource dataSource = InfrastructureUtil.getDataSource();

		if (dataSource == null) {
			return;
		}

		try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName, schemaVersion from Release_")) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String servletContextName = resultSet.getString(
					"servletContextName");
				String schemaVersion = resultSet.getString("schemaVersion");

				SchemaVersions moduleSchemaVersions = _schemaVersionsMap.get(
					servletContextName);

				if (moduleSchemaVersions == null) {
					moduleSchemaVersions = new SchemaVersions(null);

					_schemaVersionsMap.put(
						servletContextName, moduleSchemaVersions);
				}

				unsafeBiConsumer.accept(moduleSchemaVersions, schemaVersion);
			}
		}
		catch (SQLException sqlException) {
			_log.error("Unable to get schema version", sqlException);
		}
		catch (Exception exception) {
			_log.error("Unable to process Release_ table", exception);
		}
	}

	private void _setFinalState() {
		boolean check;

		try {
			check = _releaseManager.check();
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to check the upgrade state due to ",
					exception.getMessage(), ". Please check manually."));

			_state = "Failure";

			return;
		}

		if (!_errorMessages.isEmpty() || !check) {
			_state = "Failure";

			return;
		}

		if (!_warningMessages.isEmpty()) {
			_state = "Warning";

			return;
		}

		_state = "Success";
	}

	private void _setType() {
		String upgradeType = "No upgrade";

		for (Map.Entry<String, SchemaVersions> schemaVersionsEntry :
				_schemaVersionsMap.entrySet()) {

			SchemaVersions schemaVersions = schemaVersionsEntry.getValue();

			if (schemaVersions.getInitial() == null) {
				continue;
			}

			Version initialVersion = Version.parseVersion(
				schemaVersions.getInitial());
			Version finalVersion = Version.parseVersion(
				schemaVersions.getFinal());

			if (initialVersion.getMajor() < finalVersion.getMajor()) {
				upgradeType = "Major";

				break;
			}

			if (initialVersion.getMinor() < finalVersion.getMinor()) {
				upgradeType = "Minor";

				continue;
			}

			if (initialVersion.getMicro() < finalVersion.getMicro()) {
				if (upgradeType.compareTo("Minor") != 0) {
					upgradeType = "Micro";
				}

				continue;
			}

			String initialQualifier = initialVersion.getQualifier();
			String finalQualifier = finalVersion.getQualifier();

			if (!initialQualifier.isEmpty() && finalQualifier.isEmpty() &&
				(upgradeType.compareTo("Minor") != 0)) {

				upgradeType = "Micro";
			}
		}

		_type = upgradeType;
	}

	private static final String[] _FILTERED_CLASS_NAMES = {
		"com.liferay.portal.search.elasticsearch7.internal.sidecar." +
			"SidecarManager"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeStatusImpl.class);

	private final Map<String, Map<String, Integer>> _errorMessages =
		new ConcurrentHashMap<>();
	private boolean _filtered;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private ReleaseManager _releaseManager;

	private final Map<String, SchemaVersions> _schemaVersionsMap =
		new ConcurrentHashMap<>();
	private String _state =
		PropsValues.UPGRADE_DATABASE_AUTO_RUN || DBUpgrader.isUpgradeClient() ?
			"Pending" : "Not enabled";
	private String _type =
		PropsValues.UPGRADE_DATABASE_AUTO_RUN || DBUpgrader.isUpgradeClient() ?
			"Not calculated" : "Not enabled";
	private final Map<String, ArrayList<String>> _upgradeProcessMessages =
		new ConcurrentHashMap<>();
	private final Map<String, Map<String, Integer>> _warningMessages =
		new ConcurrentHashMap<>();

	private class SchemaVersions {

		public SchemaVersions(String initialSchemaVersion) {
			_initial = initialSchemaVersion;
		}

		public String getFinal() {
			return _final;
		}

		public String getInitial() {
			return _initial;
		}

		public void setFinal(String aFinal) {
			_final = aFinal;
		}

		public void setInitial(String initial) {
			if (initial == null) {
				_initial = "0.0.0";

				return;
			}

			_initial = initial;
		}

		private String _final;
		private String _initial;

	}

}