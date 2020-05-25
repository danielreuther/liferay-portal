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

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {Suspense, lazy, useCallback, useRef, useState} from 'react';

import './Field.scss';
import {usePage} from '../../hooks/usePage.es';
import {ErrorBoundary} from '../ErrorBoundary.es';

const getModule = (fieldTypes, fieldType) => {
	const field = fieldTypes.find((field) => field.name === fieldType);

	return field.javaScriptModule;
};

const load = (fieldModule) => {
	return new Promise((resolve, reject) => {
		Liferay.Loader.require(
			[fieldModule],
			(Field) => resolve(Field),
			(error) => reject(error)
		);
	});
};

const useLazy = () => {

	// To have a better effect, we need to move this to a higher
	// component above PageRenderer or FormRenderer because we
	// can have two instances of it on the same page and they
	// are destroyed all the time.

	const components = useRef(new Map());

	return useCallback((fieldModule) => {
		if (!components.current.has(fieldModule)) {
			const Component = lazy(() => {
				return load(fieldModule).then((instance) => {
					return {
						default: instance ? instance.default : null,
					};
				});
			});

			components.current.set(fieldModule, Component);
		}

		return components.current.get(fieldModule);
	}, []);
};

/**
 * This only assembles the expected structure of the Forms field
 * event, creates a makeup to maintain compatibility with the
 * mechanism, the fields in React do not need to assemble this
 * structure, they must only provide a native event or value in
 * the case of an onChange
 */
const mountStruct = (event, field, value = null) => {
	return {
		fieldInstance: {
			...field,

			// Fake Function

			isDisposed: () => false,
		},
		originalEvent: event,
		value: value !== null ? value : event.target.value,
	};
};

export const Field = ({field, onBlur, onChange, onFocus, ...otherProps}) => {
	const {
		store: {fieldTypes},
	} = usePage();
	const [hasError, setHasError] = useState(false);
	const loadField = useLazy();

	if (!fieldTypes) {
		return <ClayLoadingIndicator />;
	}

	const fieldModule = getModule(fieldTypes, field.type);
	const FieldLazy = loadField(fieldModule);

	if (hasError) {
		return (
			<div className="ddm-field-renderer--error">
				<p className="ddm-field-renderer--title">
					Oops! An error happening.
				</p>
				<ClayButton
					displayType="secondary"
					onClick={() => setHasError(false)}
					small
				>
					Try Again!
				</ClayButton>
			</div>
		);
	}

	return (
		<ErrorBoundary onError={() => setHasError(true)}>
			<Suspense fallback={<ClayLoadingIndicator />}>
				<div className="ddm-field" data-field-name={field.fieldName}>
					<FieldLazy
						visible
						{...field}
						{...otherProps}
						onBlur={(event) => onBlur(mountStruct(event, field))}
						onChange={(event, value) =>
							onChange(mountStruct(event, field, value))
						}
						onFocus={(event) => onFocus(mountStruct(event, field))}
					/>
				</div>
			</Suspense>
		</ErrorBoundary>
	);
};
