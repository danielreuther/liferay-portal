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

package com.liferay.dynamic.data.mapping.expression.model;

import com.liferay.dynamic.data.mapping.expression.constants.DDMExpressionConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class FunctionCallExpression extends Expression {

	public FunctionCallExpression(
		String functionName, List<Expression> parameterExpressions) {

		_functionName = functionName;
		_parameterExpressions = parameterExpressions;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor) {
		return expressionVisitor.visit(this);
	}

	public int getArity() {
		return _parameterExpressions.size();
	}

	public String getFunctionName() {
		return _functionName;
	}

	public List<Expression> getParameterExpressions() {
		return _parameterExpressions;
	}

	public boolean hasNestedFunctions() {
		for (Expression parameterExpression : _parameterExpressions) {
			String parameterExpressionString = parameterExpression.toString();

			if (parameterExpressionString.matches(
					DDMExpressionConstants.NESTED_FUNCTION_REGEX)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(_functionName);
		sb.append("(");
		sb.append(
			StringUtil.merge(
				_parameterExpressions, StringPool.COMMA_AND_SPACE));
		sb.append(")");

		return sb.toString();
	}

	private final String _functionName;
	private final List<Expression> _parameterExpressions;

}