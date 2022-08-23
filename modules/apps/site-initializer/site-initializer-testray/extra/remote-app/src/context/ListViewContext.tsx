/* eslint-disable no-case-declarations */
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

import {ReactNode, createContext, useReducer} from 'react';

import {ActionMap, SortDirection, SortOption} from '../types';

export type Sort = {
	direction: SortDirection;
	key: string;
};

export type Entry = {
	label: string;
	name: string;
	value: string;
};

export type InitialState = {
	checkAll: boolean;
	columns: {
		[key: string]: boolean;
	};
	filters: {
		entries: Entry[];
		filter: {
			[key: string]: string;
		};
	};
	keywords: string;
	page: number;
	pageSize: number;
	selectedRows: number[];
	sort: Sort;
};

const initialState: InitialState = {
	checkAll: false,
	columns: {},
	filters: {
		entries: [],
		filter: {},
	},
	keywords: '',
	page: 1,
	pageSize: 20,
	selectedRows: [],
	sort: {direction: SortOption.ASC, key: ''},
};

export enum ListViewTypes {
	SET_CHECKED_ALL_ROWS = 'SET_CHECKED_ALL_ROWS',
	SET_CHECKED_ROW = 'SET_CHECKED_ROW',
	SET_CLEAR = 'SET_CLEAR',
	SET_COLUMNS = 'SET_COLUMNS',
	SET_PAGE = 'SET_PAGE',
	SET_PAGE_SIZE = 'SET_PAGE_SIZE',
	SET_REMOVE_FILTER = 'SET_REMOVE_FILTER',
	SET_SEARCH = 'SET_SEARCH',
	SET_SORT = 'SET_SORT',
	SET_UPDATE_FILTERS_AND_SORT = 'SET_UPDATE_FILTERS_AND_SORT',
}

type ListViewPayload = {
	[ListViewTypes.SET_CHECKED_ALL_ROWS]: boolean;
	[ListViewTypes.SET_CHECKED_ROW]: number | number[];
	[ListViewTypes.SET_CLEAR]: null;
	[ListViewTypes.SET_COLUMNS]: {columns: any};
	[ListViewTypes.SET_PAGE]: number;
	[ListViewTypes.SET_PAGE_SIZE]: number;
	[ListViewTypes.SET_REMOVE_FILTER]: string;
	[ListViewTypes.SET_SEARCH]: string;
	[ListViewTypes.SET_SORT]: Sort;
	[ListViewTypes.SET_UPDATE_FILTERS_AND_SORT]: {filters?: any};
};

type AppActions = ActionMap<ListViewPayload>[keyof ActionMap<ListViewPayload>];

export const ListViewContext = createContext<
	[InitialState, (param: AppActions) => void]
>([initialState, () => null]);

const reducer = (state: InitialState, action: AppActions) => {
	switch (action.type) {
		case ListViewTypes.SET_CHECKED_ROW:
			const rowIds = action.payload;

			let selectedRows = [...state.selectedRows];

			if (Array.isArray(rowIds)) {
				selectedRows = state.checkAll ? [] : rowIds;

				state.checkAll = !state.checkAll;
			}
			else {
				const rowAlreadyInserted = state.selectedRows.includes(
					rowIds as number
				);

				if (rowAlreadyInserted) {
					selectedRows = selectedRows.filter((row) => row !== rowIds);
				}
				else {
					selectedRows = [...selectedRows, rowIds as number];
				}
			}

			return {
				...state,
				selectedRows,
			};

		case ListViewTypes.SET_CHECKED_ALL_ROWS:
			return {
				...state,
				checkAll: action.payload,
			};

		case ListViewTypes.SET_CLEAR:
			return {
				...state,
				filters: initialState.filters,
				keywords: '',
			};

		case ListViewTypes.SET_COLUMNS:
			return {
				...state,
				columns: action.payload.columns,
			};

		case ListViewTypes.SET_PAGE:
			return {
				...state,
				page: action.payload,
			};

		case ListViewTypes.SET_PAGE_SIZE:
			return {
				...state,
				page: 1,
				pageSize: action.payload,
			};

		case ListViewTypes.SET_REMOVE_FILTER: {
			const filterKey = action.payload;
			const updatedFilters = {...state.filters};

			delete updatedFilters.filter[filterKey];

			const filterEntries = updatedFilters.entries.filter(
				({name}) => name !== filterKey
			);

			return {
				...state,
				filters: {
					entries: filterEntries,
					filter: updatedFilters.filter,
				},
			};
		}

		case ListViewTypes.SET_SEARCH:
			return {
				...state,
				keywords: action.payload,
				page: 1,
			};

		case ListViewTypes.SET_SORT:
			return {
				...state,
				sort: action.payload,
			};

		case ListViewTypes.SET_UPDATE_FILTERS_AND_SORT:
			return {
				...state,
				filters: action.payload.filters || state.filters,
				page: 1,
			};

		default:
			return state;
	}
};

export type ListViewContextProviderProps = Partial<InitialState>;

const ListViewContextProvider: React.FC<
	ListViewContextProviderProps & {children: ReactNode}
> = ({children, ...initialStateProps}) => {
	const [state, dispatch] = useReducer(reducer, {
		...initialState,
		...initialStateProps,
	});

	return (
		<ListViewContext.Provider value={[state, dispatch]}>
			{children}
		</ListViewContext.Provider>
	);
};

export default ListViewContextProvider;
