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

import md5 from 'md5';

import {getInitials} from '../../utils/util';

const AVATAR_SIZE_IN_PX = 40;

export function Avatar({
	emailAddress,
	gravatarAPI,
	initialImage,
	userName,
}: {
	emailAddress: string;
	gravatarAPI: string;
	initialImage?: string;
	userName: string;
}) {
	let emailAddressMD5;
	let uiAvatarURL = '';

	if (!initialImage) {
		emailAddressMD5 = md5(emailAddress);
		uiAvatarURL = `https://ui-avatars.com/api/${getInitials(
			userName
		)}/128/0B5FFF/FFFFFF/2/0.33/true/true/true`;
	}

	return (
		<img
			height={AVATAR_SIZE_IN_PX}
			src={
				initialImage ??
				`${gravatarAPI}/${emailAddressMD5}?d=${encodeURIComponent(
					uiAvatarURL
				)}`
			}
			width={AVATAR_SIZE_IN_PX}
		/>
	);
}
