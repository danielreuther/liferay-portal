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

import {ClayCheckbox} from '@clayui/form';
import {COOKIE_TYPES} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import CommerceCookie from '../../utilities/cookies';
import {
	ITEM_REMOVED_FROM_COMPARE,
	PRODUCT_COMPARISON_TOGGLED,
	TOGGLE_ITEM_IN_PRODUCT_COMPARISON,
} from '../../utilities/eventsDefinitions';

const compareCookie = new CommerceCookie(
	'COMMERCE_COMPARE_cpDefinitionIds_',
	COOKIE_TYPES.FUNCTIONAL
);

function CompareCheckbox({
	commerceChannelGroupId,
	disabled: isDisabled,
	inCompare: isInCompare,
	itemId,
	label,
	onUpdate,
	pictureUrl,
	refreshOnRemove,
}) {
	const [inCompare, setInCompare] = useState(isInCompare);
	const [disabled, setDisabled] = useState(isDisabled);

	const toggleStatus = () => {
		if (commerceChannelGroupId && inCompare && refreshOnRemove) {
			const value = compareCookie.getValue(commerceChannelGroupId);

			const cpDefinitionIds = value ? value.split(':') : [];

			const index = cpDefinitionIds.indexOf(itemId);

			if (index !== -1) {
				cpDefinitionIds.splice(index, 1);
			}

			compareCookie.setValue(
				commerceChannelGroupId,
				cpDefinitionIds.join(':')
			);

			window.location.reload();

			return;
		}
		setInCompare((currentlyInCompare) => {
			Liferay.fire(TOGGLE_ITEM_IN_PRODUCT_COMPARISON, {
				id: itemId,
				thumbnail: pictureUrl,
			});

			return !currentlyInCompare;
		});
	};

	const toggleCompare = useCallback(
		({disabled = true}) => {
			setDisabled(disabled);

			onUpdate({disabled, inCompare});
		},
		[inCompare, onUpdate]
	);

	const removeFromCompare = useCallback(
		({id}) => {
			if (id === itemId) {
				setInCompare(() => {
					const inCompare = false;

					onUpdate({disabled, inCompare});

					return inCompare;
				});
			}
		},
		[disabled, itemId, onUpdate]
	);

	useEffect(() => {
		Liferay.on(ITEM_REMOVED_FROM_COMPARE, removeFromCompare);
		Liferay.on(PRODUCT_COMPARISON_TOGGLED, toggleCompare);

		return () => {
			Liferay.detach(ITEM_REMOVED_FROM_COMPARE, removeFromCompare);
			Liferay.detach(PRODUCT_COMPARISON_TOGGLED, toggleCompare);
		};
	}, [removeFromCompare, toggleCompare]);

	return (
		<ClayCheckbox
			aria-label={label}
			checked={inCompare}
			className="compare-checkbox-component"
			disabled={disabled && !inCompare}
			label={label}
			onChange={() => toggleStatus()}
		/>
	);
}

CompareCheckbox.defaultProps = {
	commerceChannelGroupId: 0,
	disabled: false,
	inCompare: false,
	label: '',
	onUpdate: () => {},
	pictureUrl: '',
	refreshOnRemove: false,
};

CompareCheckbox.propTypes = {
	commerceChannelGroupId: PropTypes.number,
	disabled: PropTypes.bool,
	inCompare: PropTypes.bool,
	itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		.isRequired,
	label: PropTypes.string,
	onUpdate: PropTypes.func,
	pictureUrl: PropTypes.string,
	refreshOnRemove: PropTypes.bool,
};

export default CompareCheckbox;
