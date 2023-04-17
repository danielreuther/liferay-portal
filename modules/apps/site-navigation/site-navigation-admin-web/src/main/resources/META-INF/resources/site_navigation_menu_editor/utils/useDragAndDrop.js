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

import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {ACCEPTING_ITEM_TYPE} from '../constants/acceptingItemType';
import {NESTING_MARGIN} from '../constants/nestingMargin';
import {useConstants} from '../contexts/ConstantsContext';
import {useItems, useSetItems} from '../contexts/ItemsContext';
import updateItemParent from '../utils/updateItemParent';
import getDescendantsCount from './getDescendantsCount';
import getItemPath from './getItemPath';
import moveItem from './moveItem';

const DIRECTIONS = {
	down: 'down',
	inside: 'inside',
	outside: 'outside',
	up: 'up',
};

const DragDropContext = React.createContext({});

export function DragDropProvider({children}) {
	const [parentId, setParentId] = useState(null);
	const [horizontalOffset, setHorizontalOffset] = useState(0);
	const [targetItemId, setTargetItemId] = useState(null);
	const [verticalOffset, setVerticalOffset] = useState(0);

	const dragDropValues = {
		horizontalOffset,
		parentId,
		setHorizontalOffset,
		setParentId,
		setTargetItemId,
		setVerticalOffset,
		targetItemId,
		verticalOffset,
	};

	return (
		<DragDropContext.Provider value={dragDropValues}>
			{children}
		</DragDropContext.Provider>
	);
}

export function useDragItem(item, onDragEnd) {
	const {parentSiteNavigationMenuItemId, siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);

	const {portletNamespace: namespace} = useConstants();

	const {
		parentId,
		setHorizontalOffset,
		setParentId,
		setTargetItemId,
		setVerticalOffset,
	} = useContext(DragDropContext);

	const [{isDragging}, handlerRef, previewRef] = useDrag({
		begin() {
			setParentId(parentSiteNavigationMenuItemId);
		},
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		end() {
			onDragEnd(item.siteNavigationMenuItemId, parentId);

			setHorizontalOffset(0);
			setParentId(null);
			setTargetItemId(null);
			setVerticalOffset(null);
		},
		isDragging(monitor) {
			return itemPath.includes(monitor.getItem().id);
		},
		item: {
			id: siteNavigationMenuItemId,
			namespace,
			type: ACCEPTING_ITEM_TYPE,
		},
	});

	useEffect(() => {
		previewRef(getEmptyImage(), {captureDraggingState: true});
	}, [previewRef]);

	return {
		handlerRef,
		isDragging,
	};
}

export function useDropTarget(item) {
	if (Liferay.FeatureFlags['LPS-134527']) {
		// eslint-disable-next-line react-hooks/rules-of-hooks
		return useNewDropTarget(item);
	}
	else {
		// eslint-disable-next-line react-hooks/rules-of-hooks
		return useOldDropTarget(item);
	}
}

function useNewDropTarget(item) {
	const {siteNavigationMenuItemId} = item;

	const [isNested, setIsNested] = useState(false);
	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);
	const targetRef = useRef();

	const {setTargetItemId, targetItemId} = useContext(DragDropContext);

	const [, dndTargetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(source, monitor) {
			return monitor.isOver();
		},
		hover(source, monitor) {
			if (monitor.canDrop(source, monitor)) {
				if (!targetRef.current || itemPath.includes(source.id)) {
					return;
				}

				const itemPosition = monitor.getSourceClientOffset();
				const targetRect = targetRef.current.getBoundingClientRect();

				// We need to check min and max X values to correctly support
				// both LTR and RTL languages.

				const max = Math.max(targetRect.left, targetRect.right);
				const min = Math.min(targetRect.left, targetRect.right);

				const normalizedPosition = (itemPosition.x - min) / (max - min);

				setTargetItemId(siteNavigationMenuItemId);
				setIsNested(normalizedPosition > 0.4);
			}
		},
	});

	const updateTargetRef = useCallback(
		(nextTargetElement) => {
			dndTargetRef(nextTargetElement);
			targetRef.current = nextTargetElement;
		},
		[dndTargetRef]
	);

	return {
		isNested,
		isOver: targetItemId === siteNavigationMenuItemId,
		targetRef: updateTargetRef,
	};
}

function useOldDropTarget(item) {
	const {siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);
	const setItems = useSetItems();

	const {languageId} = useConstants();
	const rtl = Liferay.Language.direction[languageId] === 'rtl';

	const {
		horizontalOffset,
		setHorizontalOffset,
		setParentId,
		setVerticalOffset,
		verticalOffset,
	} = useContext(DragDropContext);

	const [, targetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(source, monitor) {
			return monitor.isOver();
		},
		hover(source, monitor) {
			if (monitor.canDrop(source, monitor)) {
				if (itemPath.includes(source.id)) {
					const data = computeHoverItself({
						initialOffset: horizontalOffset,
						items,
						monitor,
						rtl,
						source,
					});

					if (data) {
						const {currentOffset, newParentId} = data;

						setParentId(newParentId);
						setHorizontalOffset(currentOffset);

						const newItems = updateItemParent(
							items,
							source.id,
							newParentId
						);

						setItems(newItems);
					}
				}
				else {
					const {
						currentOffset,
						direction,
						newIndex,
						newParentId,
					} = computeHoverAnotherItem({
						initialOffset: verticalOffset,
						items,
						monitor,
						source,
						targetId: siteNavigationMenuItemId,
					});

					if (newParentId) {
						setParentId(newParentId);
						setHorizontalOffset(0);
						setVerticalOffset(currentOffset);

						const newItems = moveItem(
							items,
							source.id,
							newParentId,
							newIndex,
							direction
						);

						setItems(newItems);
					}
				}
			}
		},
	});

	return {
		targetRef,
	};
}

function getHorizontalMovementDirection(initialOffset, currentOffset, rtl) {
	if (rtl) {
		return initialOffset < currentOffset
			? DIRECTIONS.outside
			: DIRECTIONS.inside;
	}
	else {
		return initialOffset > currentOffset
			? DIRECTIONS.outside
			: DIRECTIONS.inside;
	}
}

function computeHoverItself({initialOffset, items, monitor, rtl, source}) {
	const currentOffset = monitor.getDifferenceFromInitialOffset().x;

	if (Math.abs(initialOffset - currentOffset) < NESTING_MARGIN) {
		return;
	}

	const direction = getHorizontalMovementDirection(
		initialOffset,
		currentOffset,
		rtl
	);

	const sourceItem = items.find(
		(item) => item.siteNavigationMenuItemId === source.id
	);
	const sourceItemIndex = items.indexOf(sourceItem);

	let newParentId;

	if (direction === DIRECTIONS.inside) {
		const previousSibling = items
			.filter(
				(item, index) =>
					item.parentSiteNavigationMenuItemId ===
						sourceItem.parentSiteNavigationMenuItemId &&
					item.siteNavigationMenuItemId !== source.id &&
					index < sourceItemIndex
			)
			.pop();

		newParentId = previousSibling?.siteNavigationMenuItemId;
	}
	else {
		const nextSiblings = items.filter(
			(item, index) =>
				item.parentSiteNavigationMenuItemId ===
					sourceItem.parentSiteNavigationMenuItemId &&
				item.siteNavigationMenuItemId !== source.id &&
				index > sourceItemIndex
		);

		if (!nextSiblings.length) {
			const parent = items.find(
				(item) =>
					item.siteNavigationMenuItemId ===
					sourceItem.parentSiteNavigationMenuItemId
			);

			newParentId = parent?.parentSiteNavigationMenuItemId;
		}
	}

	if (
		!newParentId ||
		newParentId === sourceItem.siteNavigationMenuItemId ||
		itemIsDynamic(newParentId, items)
	) {
		return;
	}

	return {currentOffset, newParentId};
}

function computeHoverAnotherItem({
	initialOffset,
	items,
	monitor,
	source,
	targetId,
}) {
	const sourceItem = items.find(
		(item) => item.siteNavigationMenuItemId === source.id
	);
	const targetItem = items.find(
		(item) => item.siteNavigationMenuItemId === targetId
	);

	const currentOffset = monitor.getDifferenceFromInitialOffset().y;

	if (initialOffset === currentOffset) {
		return;
	}

	const direction =
		initialOffset > currentOffset ? DIRECTIONS.up : DIRECTIONS.down;

	const newIndex = items.indexOf(targetItem);

	if (newIndex === items.indexOf(sourceItem)) {
		return;
	}

	let newParentId;

	if (direction === DIRECTIONS.up) {
		newParentId = targetItem.parentSiteNavigationMenuItemId;
	}

	if (direction === DIRECTIONS.down) {
		const targetItemDescendantsCount = getDescendantsCount(items, targetId);

		newParentId = targetItemDescendantsCount
			? targetItem.siteNavigationMenuItemId
			: targetItem.parentSiteNavigationMenuItemId;
	}

	if (itemIsDynamic(newParentId, items)) {
		return;
	}

	return {
		currentOffset,
		direction,
		newIndex,
		newParentId,
	};
}

function itemIsDynamic(siteNavigationMenuItemId, items) {
	const item = items.find(
		(item) => item.siteNavigationMenuItemId === siteNavigationMenuItemId
	);

	return item?.dynamic;
}
