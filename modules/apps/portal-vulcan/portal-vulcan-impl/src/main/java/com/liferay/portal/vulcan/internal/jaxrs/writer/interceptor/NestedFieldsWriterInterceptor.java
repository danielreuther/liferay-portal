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

package com.liferay.portal.vulcan.internal.jaxrs.writer.interceptor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.internal.fields.servlet.NestedFieldsHttpServletRequestWrapper;
import com.liferay.portal.vulcan.internal.param.converter.DateParamConverter;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Ivica Cardic
 */
@Provider
public class NestedFieldsWriterInterceptor implements WriterInterceptor {

	public NestedFieldsWriterInterceptor(BundleContext bundleContext) {
		this(bundleContext, null);
	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
		throws IOException, WebApplicationException {

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		if (nestedFieldsContext == null) {
			writerInterceptorContext.proceed();

			return;
		}

		try {
			_setFieldValues(
				writerInterceptorContext.getEntity(),
				nestedFieldsContext.getFieldNames(), nestedFieldsContext);
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);

			throw new WebApplicationException(e);
		}

		writerInterceptorContext.proceed();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	protected NestedFieldsWriterInterceptor(
		BundleContext bundleContext,
		NestedFieldServiceTrackerCustomizer
			nestedFieldServiceTrackerCustomizer) {

		if (nestedFieldServiceTrackerCustomizer == null) {
			nestedFieldServiceTrackerCustomizer =
				new NestedFieldServiceTrackerCustomizer(bundleContext);
		}

		_nestedFieldServiceTrackerCustomizer =
			nestedFieldServiceTrackerCustomizer;

		try {
			_serviceTracker = new ServiceTracker<>(
				bundleContext,
				bundleContext.createFilter(
					"(&(api.version=*)(osgi.jaxrs.resource=true))"),
				_nestedFieldServiceTrackerCustomizer);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}

		_serviceTracker.open();
	}

	protected static class NestedFieldServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, List<String>> {

		@Override
		public List<String> addingService(
			ServiceReference<Object> serviceReference) {

			Object resource = _bundleContext.getService(serviceReference);

			Class<?> resourceClass = resource.getClass();

			List<String> keys = null;

			for (Method resourceMethod : resourceClass.getDeclaredMethods()) {
				NestedField nestedField = resourceMethod.getAnnotation(
					NestedField.class);

				if (nestedField == null) {
					continue;
				}

				Class<?> parentClass = nestedField.parentClass();

				String key = StringBundler.concat(
					nestedField.value(), StringPool.POUND,
					parentClass.getName(), StringPool.POUND,
					_getResourceVersion(resourceClass.getSuperclass()));

				ServiceObjects<Object> serviceObjects =
					_bundleContext.getServiceObjects(serviceReference);

				_unsafeTriFunctions.put(
					key,
					(fieldName, item, nestedFieldsContext) ->
						_getNestedFieldValue(
							fieldName, item, resourceMethod,
							nestedFieldsContext, serviceObjects));

				if (keys == null) {
					keys = new ArrayList<>();
				}

				keys.add(key);
			}

			return keys;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, List<String> keys) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, List<String> keys) {

			keys.forEach(_unsafeTriFunctions::remove);

			_bundleContext.ungetService(serviceReference);
		}

		protected NestedFieldServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		protected HttpServletRequest getHttpServletRequest(Message message) {
			return (HttpServletRequest)message.getContextualProperty(
				"HTTP.REQUEST");
		}

		protected ProviderFactory getProviderFactory(Message message) {
			return ProviderFactory.getInstance(message);
		}

		private Object _convert(String value, Class<?> type) {
			if (value == null) {
				return null;
			}

			if (type == BigDecimal.class) {
				return new BigDecimal(value);
			}
			else if (type == Boolean.class) {
				return Boolean.valueOf(value);
			}
			else if (type == Byte.class) {
				return Byte.valueOf(value);
			}
			else if (type == Character.class) {
				return value.charAt(0);
			}
			else if (type == Date.class) {
				return _dateParamConverter.fromString(value);
			}
			else if (type == Double.class) {
				return Double.valueOf(value);
			}
			else if (type == Float.class) {
				return Float.valueOf(value);
			}
			else if (type == Integer.class) {
				return Integer.valueOf(value);
			}
			else if (type == LocalDate.class) {
				return LocalDate.parse(value);
			}
			else if (type == LocalDateTime.class) {
				return LocalDateTime.parse(value);
			}
			else if (type == Long.class) {
				return Long.valueOf(value);
			}
			else if (type == Short.class) {
				return Short.valueOf(value);
			}
			else if (type == String.class) {
				return value;
			}
			else {
				throw new IllegalArgumentException(
					String.format(
						"value %s cannot be converted to %s", value, type));
			}
		}

		private Parameter[] _getClassMethodParameters(
			Class<?> clazz, Method method) {

			Method classMethod = null;

			try {
				classMethod = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());
			}
			catch (NoSuchMethodException nsme) {
				return null;
			}

			return classMethod.getParameters();
		}

		private <T> Object _getContext(Class<T> contextClass, Message message) {
			ContextProvider<?> contextProvider = _getContextProvider(
				contextClass, message);

			if (contextProvider != null) {
				return contextProvider.createContext(message);
			}

			return null;
		}

		private <T> ContextProvider<T> _getContextProvider(
			Class<T> contextClass, Message message) {

			ProviderFactory providerFactory = getProviderFactory(message);

			return providerFactory.createContextProvider(contextClass, message);
		}

		private Object _getFieldValue(String fieldName, Object item)
			throws Exception {

			List<Class> itemClasses = new ArrayList<>();

			Class<?> itemClass = item.getClass();

			itemClasses.add(itemClass);

			itemClasses.add(itemClass.getSuperclass());

			for (Class<?> curItemClass : itemClasses) {
				try {
					Field itemField = curItemClass.getDeclaredField(fieldName);

					if (itemField != null) {
						itemField.setAccessible(true);

						return itemField.get(item);
					}
				}
				catch (NoSuchFieldException nsfe) {
					if (_log.isDebugEnabled()) {
						_log.debug(nsfe.getMessage());
					}
				}
			}

			return null;
		}

		private Object[] _getMethodArgs(
				String fieldName, Object item, Method method,
				NestedFieldsContext nestedFieldsContext, Class<?> resourceClass)
			throws Exception {

			Object[] args = new Object[method.getParameterCount()];

			MultivaluedMap<String, String> pathParameters =
				nestedFieldsContext.getPathParameters();
			Parameter[] resourceBaseClassParameters = _getClassMethodParameters(
				resourceClass.getSuperclass(), method);
			Parameter[] resourceClassParameters = _getClassMethodParameters(
				resourceClass, method);
			MultivaluedMap<String, String> queryParameters =
				nestedFieldsContext.getQueryParameters();

			for (int i = 0; i < method.getParameterCount(); i++) {
				Parameter resourceBaseClassParameter = _getParameter(
					i, resourceBaseClassParameters);

				args[i] = _getMethodArgValueFromRequest(
					fieldName, nestedFieldsContext, pathParameters,
					queryParameters, resourceBaseClassParameter);

				if ((args[i] == null) && (resourceClassParameters != null)) {
					args[i] = _getMethodArgValueFromItem(
						item, resourceBaseClassParameter,
						resourceClassParameters[i]);
				}
			}

			return args;
		}

		private Object _getMethodArgValueFromItem(
				Object item, Parameter resourceBaseClassParameter,
				Parameter resourceClassParameter)
			throws Exception {

			Annotation[] annotations = resourceClassParameter.getAnnotations();

			if (annotations.length > 0) {
				for (Annotation annotation : annotations) {
					if (annotation instanceof NestedFieldId) {
						NestedFieldId nestedFieldId = (NestedFieldId)annotation;

						return _getFieldValue(nestedFieldId.value(), item);
					}
				}
			}

			if (resourceBaseClassParameter == null) {
				return null;
			}

			annotations = resourceBaseClassParameter.getAnnotations();

			if (annotations.length == 0) {
				return null;
			}

			for (Annotation annotation : annotations) {
				if (annotation instanceof PathParam) {
					PathParam pathParam = (PathParam)annotation;

					return _getFieldValue(pathParam.value(), item);
				}
			}

			return null;
		}

		private Object _getMethodArgValueFromRequest(
			String fieldName, NestedFieldsContext nestedFieldsContext,
			MultivaluedMap<String, String> pathParameters,
			MultivaluedMap<String, String> queryParameters,
			Parameter resourceBaseClassParameter) {

			if (resourceBaseClassParameter == null) {
				return null;
			}

			Annotation[] annotations =
				resourceBaseClassParameter.getAnnotations();

			if (annotations.length == 0) {
				return null;
			}

			Object argValue = null;

			for (Annotation annotation : annotations) {
				if (annotation instanceof Context) {
					Message message = _getNestedFieldsAwareMessage(
						fieldName, nestedFieldsContext.getMessage());

					argValue = _getContext(
						resourceBaseClassParameter.getType(), message);

					_resetNestedAwareMessage(message);

					break;
				}
				else if (annotation instanceof PathParam) {
					PathParam pathParam = (PathParam)annotation;

					argValue = _convert(
						pathParameters.getFirst(pathParam.value()),
						resourceBaseClassParameter.getType());

					break;
				}
				else if (annotation instanceof QueryParam) {
					QueryParam queryParam = (QueryParam)annotation;

					argValue = _convert(
						queryParameters.getFirst(
							fieldName + "." + queryParam.value()),
						resourceBaseClassParameter.getType());

					break;
				}
			}

			return argValue;
		}

		private Message _getNestedFieldsAwareMessage(
			String fieldName, Message message) {

			message.put(
				"HTTP.REQUEST",
				new NestedFieldsHttpServletRequestWrapper(
					fieldName, getHttpServletRequest(message)));

			return message;
		}

		private Object _getNestedFieldValue(
				String fieldName, Object item, Method method,
				NestedFieldsContext nestedFieldsContext,
				ServiceObjects<Object> serviceObjects)
			throws Exception {

			Object resource = serviceObjects.getService();

			Class<?> resourceClass = resource.getClass();

			try {
				_setResourceContexts(
					nestedFieldsContext.getMessage(), resource);

				Object[] args = _getMethodArgs(
					fieldName, item, method, nestedFieldsContext,
					resourceClass);

				return method.invoke(resource, args);
			}
			finally {
				serviceObjects.ungetService(resource);
			}
		}

		private Parameter _getParameter(
			int index, Parameter[] resourceBaseClassParameters) {

			Parameter parameter = null;

			if (resourceBaseClassParameters != null) {
				parameter = resourceBaseClassParameters[index];
			}

			return parameter;
		}

		private String _getResourceVersion(Class<?> resourceBaseClass) {
			Annotation[] annotations = resourceBaseClass.getAnnotations();

			for (Annotation annotation : annotations) {
				if (annotation instanceof Path) {
					Path path = (Path)annotation;

					String resourceVersion = path.value();

					return resourceVersion.substring(1);
				}
			}

			return null;
		}

		private void _resetNestedAwareMessage(Message message) {
			NestedFieldsHttpServletRequestWrapper
				nestedFieldsHttpServletRequestWrapper =
					(NestedFieldsHttpServletRequestWrapper)
						message.getContextualProperty("HTTP.REQUEST");

			message.put(
				"HTTP.REQUEST",
				nestedFieldsHttpServletRequestWrapper.getRequest());
		}

		private void _setResourceContexts(Message message, Object resource)
			throws Exception {

			Class<?> resourceClass = resource.getClass();

			_setResourceFields(
				resourceClass.getDeclaredFields(), message, resource);

			Class<?> superClass = resourceClass.getSuperclass();

			_setResourceFields(
				superClass.getDeclaredFields(), message, resource);
		}

		private void _setResourceFields(
				Field[] fields, Message message, Object resource)
			throws IllegalAccessException {

			for (Field field : fields) {
				Annotation[] annotations = field.getAnnotations();

				if (annotations.length == 0) {
					continue;
				}

				if (annotations[0] instanceof Context) {
					field.setAccessible(true);

					field.set(resource, _getContext(field.getType(), message));
				}
			}
		}

		private final BundleContext _bundleContext;
		private final DateParamConverter _dateParamConverter =
			new DateParamConverter();
		private final Map
			<String,
			 UnsafeTriFunction<String, Object, NestedFieldsContext, Object>>
				_unsafeTriFunctions = new ConcurrentHashMap<>();

	}

	@FunctionalInterface
	protected interface UnsafeTriFunction<A, B, C, R> {

		public R apply(A a, B b, C c) throws Exception;

	}

	private Object _adaptToFieldType(Class<?> fieldType, Object value) {
		if (value instanceof Page) {
			Page page = (Page)value;

			value = page.getItems();
		}

		if (fieldType.isArray() && (value instanceof Collection)) {
			Collection collection = (Collection)value;

			value = Array.newInstance(
				fieldType.getComponentType(), collection.size());

			int i = 0;

			for (Object object : collection) {
				Array.set(value, i++, object);
			}
		}

		return value;
	}

	private Field _getField(Class<?> entityClass, String fieldName) {
		List<Field> fields = new ArrayList<>(
			Arrays.asList(entityClass.getDeclaredFields()));

		Class<?> superClass = entityClass.getSuperclass();

		if (superClass != null) {
			Collections.addAll(fields, superClass.getDeclaredFields());
		}

		for (Field field : fields) {
			if (Objects.equals(field.getName(), fieldName) ||
				Objects.equals(field.getName(), "_" + fieldName)) {

				return field;
			}
		}

		return null;
	}

	private List<Object> _getItems(Object entity) {
		List<Object> items = new ArrayList<>();

		if (entity instanceof Collection) {
			items.addAll((Collection)entity);
		}
		else if (entity instanceof Page) {
			Page page = (Page)entity;

			items.addAll(page.getItems());
		}
		else if (_isArray(entity)) {
			Collections.addAll(items, (Object[])entity);
		}
		else {
			items.add(entity);
		}

		return items;
	}

	private UnsafeTriFunction<String, Object, NestedFieldsContext, Object>
		_getUnsafeTriFunction(
			NestedFieldsContext nestedFieldsContext, String fieldName,
			Class<?> itemClass) {

		Class<?>[] parentClasses = new Class<?>[] {
			Void.class, itemClass, itemClass.getSuperclass()
		};

		for (Class<?> parentClass : parentClasses) {
			String key = StringBundler.concat(
				fieldName, StringPool.POUND, parentClass.getName(),
				StringPool.POUND, nestedFieldsContext.getResourceVersion());

			UnsafeTriFunction<String, Object, NestedFieldsContext, Object>
				unsafeTriFunction =
					_nestedFieldServiceTrackerCustomizer._unsafeTriFunctions.
						get(key);

			if (unsafeTriFunction != null) {
				return unsafeTriFunction;
			}
		}

		return null;
	}

	private boolean _isArray(Object object) {
		Class<?> objectClass = object.getClass();

		return objectClass.isArray();
	}

	private void _setFieldValues(
			Object entity, List<String> fieldNames,
			NestedFieldsContext nestedFieldsContext)
		throws Exception {

		List<Object> items = _getItems(entity);

		for (String fieldName : fieldNames) {
			String nestedField = null;

			int index = fieldName.indexOf(".");

			if (index != -1) {
				nestedField = fieldName.substring(index + 1);

				fieldName = fieldName.substring(0, index);
			}

			for (Object item : items) {
				Class<?> itemClass = item.getClass();

				Field field = _getField(itemClass, fieldName);

				if (field == null) {
					continue;
				}

				field.setAccessible(true);

				UnsafeTriFunction<String, Object, NestedFieldsContext, Object>
					unsafeTriFunction = _getUnsafeTriFunction(
						nestedFieldsContext, fieldName, itemClass);

				if (unsafeTriFunction == null) {
					continue;
				}

				Object value = _adaptToFieldType(
					field.getType(),
					unsafeTriFunction.apply(
						fieldName, item, nestedFieldsContext));

				field.set(item, value);

				if (nestedField != null) {
					_setFieldValues(
						value, Collections.singletonList(nestedField),
						nestedFieldsContext);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NestedFieldsWriterInterceptor.class);

	private final NestedFieldServiceTrackerCustomizer
		_nestedFieldServiceTrackerCustomizer;
	private final ServiceTracker<Object, List<String>> _serviceTracker;

}