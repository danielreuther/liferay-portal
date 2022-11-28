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

package com.liferay.portal.settings.authentication.ldap.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseFormMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.constants.LDAPConstants;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.settings.authentication.ldap.web.internal.portlet.constants.LDAPSettingsConstants;

import java.util.Dictionary;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/portal_settings_authentication_ldap/ldap_form"
	},
	service = MVCActionCommand.class
)
public class LDAPFormMVCActionCommand extends BaseFormMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			SessionErrors.add(actionRequest, PrincipalException.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(LDAPSettingsConstants.CMD_UPDATE_AUTH)) {
			_updateLDAPAuthConfigurationProvider(
				actionRequest, _ldapAuthConfigurationProvider,
				themeDisplay.getCompanyId());
		}
		else if (cmd.equals(LDAPSettingsConstants.CMD_UPDATE_EXPORT)) {
			_updateLDAPExportConfigurationProvider(
				actionRequest, _ldapExportConfigurationProvider,
				themeDisplay.getCompanyId());
		}
		else if (cmd.equals(LDAPSettingsConstants.CMD_UPDATE_IMPORT)) {
			_updateLDAPImportConfigurationProvider(
				actionRequest, _ldapImportConfigurationProvider,
				themeDisplay.getCompanyId());
		}
		else if (cmd.equals(LDAPSettingsConstants.CMD_UPDATE_SERVER)) {
			_sortLdapServerConfigurations(
				themeDisplay.getCompanyId(),
				ParamUtil.getString(
					actionRequest,
					"ldap--" + LDAPConstants.AUTH_SERVER_PRIORITY + "--"));
		}
	}

	@Override
	protected void doValidateForm(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(
				themeDisplay.getCompanyId());

		if (!ldapImportConfiguration.importUserPasswordAutogenerated()) {
			return;
		}

		boolean ldapExportEnabled = ParamUtil.getBoolean(
			actionRequest, "ldap--" + LDAPConstants.EXPORT_ENABLED + "--");
		boolean ldapImportEnabled = ParamUtil.getBoolean(
			actionRequest, "ldap--" + LDAPConstants.IMPORT_ENABLED + "--");

		if (ldapExportEnabled && ldapImportEnabled) {
			SessionErrors.add(
				actionRequest, "ldapExportAndImportOnPasswordAutogeneration");
		}
	}

	private void _setBooleanProperties(
		ActionRequest actionRequest, Dictionary<String, Object> properties,
		String... propertyNames) {

		for (String propertyName : propertyNames) {
			boolean value = ParamUtil.getBoolean(
				actionRequest, "ldap--" + propertyName + "--");

			properties.put(propertyName, value);
		}
	}

	private void _setIntegerProperties(
		ActionRequest actionRequest, Dictionary<String, Object> properties,
		String... propertyNames) {

		for (String propertyName : propertyNames) {
			int value = ParamUtil.getInteger(
				actionRequest, "ldap--" + propertyName + "--");

			properties.put(propertyName, value);
		}
	}

	private void _setLongProperties(
		ActionRequest actionRequest, Dictionary<String, Object> properties,
		String... propertyNames) {

		for (String propertyName : propertyNames) {
			long value = ParamUtil.getLong(
				actionRequest, "ldap--" + propertyName + "--");

			properties.put(propertyName, value);
		}
	}

	private void _setStringProperties(
		ActionRequest actionRequest, Dictionary<String, Object> properties,
		String... propertyNames) {

		for (String propertyName : propertyNames) {
			String value = ParamUtil.getString(
				actionRequest, "ldap--" + propertyName + "--");

			properties.put(propertyName, value);
		}
	}

	private void _sortLdapServerConfigurations(
		long companyId, String orderedLdapServerIdsString) {

		if (Validator.isBlank(orderedLdapServerIdsString)) {
			return;
		}

		String[] orderedLdapServerIds = orderedLdapServerIdsString.split(",");

		List<Dictionary<String, Object>> dictionaries =
			_ldapServerConfigurationProvider.getConfigurationsProperties(
				companyId);

		for (int i = 0; i < orderedLdapServerIds.length; i++) {
			int authServerPriority = i;
			long ldapServerId = GetterUtil.getLong(orderedLdapServerIds[i]);

			Stream<Dictionary<String, Object>> stream = dictionaries.stream();

			stream.filter(
				dictionary -> {
					long dictionaryLDAPServerId = GetterUtil.getLong(
						dictionary.get(LDAPConstants.LDAP_SERVER_ID));

					return dictionaryLDAPServerId == ldapServerId;
				}
			).findFirst(
			).ifPresent(
				dictionary -> {
					dictionary.put(
						LDAPConstants.AUTH_SERVER_PRIORITY, authServerPriority);

					_ldapServerConfigurationProvider.updateProperties(
						companyId,
						GetterUtil.getLong(
							dictionary.get(LDAPConstants.LDAP_SERVER_ID)),
						dictionary);
				}
			);
		}
	}

	private void _updateLDAPAuthConfigurationProvider(
		ActionRequest actionRequest,
		ConfigurationProvider<?> configurationProvider, long companyId) {

		Dictionary<String, Object> properties =
			configurationProvider.getConfigurationProperties(companyId);

		_setBooleanProperties(
			actionRequest, properties, LDAPConstants.AUTH_ENABLED,
			LDAPConstants.AUTH_REQUIRED, LDAPConstants.PASSWORD_POLICY_ENABLED);
		_setStringProperties(
			actionRequest, properties, LDAPConstants.AUTH_METHOD,
			LDAPConstants.PASSWORD_ENCRYPTION_ALGORITHM);

		configurationProvider.updateProperties(companyId, properties);
	}

	private void _updateLDAPExportConfigurationProvider(
		ActionRequest actionRequest,
		ConfigurationProvider<?> configurationProvider, long companyId) {

		Dictionary<String, Object> properties =
			configurationProvider.getConfigurationProperties(companyId);

		_setBooleanProperties(
			actionRequest, properties, LDAPConstants.EXPORT_ENABLED,
			LDAPConstants.EXPORT_GROUP_ENABLED);

		configurationProvider.updateProperties(companyId, properties);
	}

	private void _updateLDAPImportConfigurationProvider(
		ActionRequest actionRequest,
		ConfigurationProvider<?> configurationProvider, long companyId) {

		Dictionary<String, Object> properties =
			configurationProvider.getConfigurationProperties(companyId);

		_setBooleanProperties(
			actionRequest, properties,
			LDAPConstants.IMPORT_CREATE_ROLE_PER_GROUP,
			LDAPConstants.IMPORT_ENABLED,
			LDAPConstants.IMPORT_GROUP_CACHE_ENABLED,
			LDAPConstants.IMPORT_ON_STARTUP,
			LDAPConstants.IMPORT_USER_PASSWORD_AUTOGENERATED,
			LDAPConstants.IMPORT_USER_PASSWORD_DEFAULT,
			LDAPConstants.IMPORT_USER_PASSWORD_ENABLED);
		_setIntegerProperties(
			actionRequest, properties, LDAPConstants.IMPORT_INTERVAL);
		_setLongProperties(
			actionRequest, properties,
			LDAPConstants.IMPORT_LOCK_EXPIRATION_TIME);
		_setStringProperties(
			actionRequest, properties, LDAPConstants.IMPORT_METHOD,
			LDAPConstants.IMPORT_USER_PASSWORD_DEFAULT,
			LDAPConstants.IMPORT_USER_SYNC_STRATEGY);

		configurationProvider.updateProperties(companyId, properties);
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration)"
	)
	private ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration)"
	)
	private ConfigurationProvider<LDAPExportConfiguration>
		_ldapExportConfigurationProvider;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration)"
	)
	private ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration)"
	)
	private ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;

}