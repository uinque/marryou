package com.marryou.metadata.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by linhy on 2016/1/17.
 */
public class SimpleExpression implements SearchFilter {
	private String fileName;//名称
	private Object value;//值
	private SearchOperator operator;//计算操作符

	public SimpleExpression(String fileName, Object value, SearchOperator operator) {
		this.fileName = fileName;
		this.value = value;
		this.operator = operator;
	}

	@Override
	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Path expression = null;
		if (fileName.contains(".")) {
			String[] names = StringUtils.split(fileName,".");
			expression = root.get(names[0]);
			for (int i = 1; i < names.length; i++) {
				expression = expression.get(names[i]);
			}
		} else {
			expression = root.get(fileName);
		}
		switch (operator) {
		case EQ:
			return builder.equal(expression, value);
		case NE:
			return builder.notEqual(expression, value);
		case LIKE:
			return builder.like((Expression<String>) expression, "%" + value + "%");
		case LT:
			return builder.lessThan(expression, (Comparable) value);
		case GT:
			return builder.greaterThan(expression, (Comparable) value);
		case LTE:
			return builder.lessThanOrEqualTo(expression, (Comparable) value);
		case GTE:
			return builder.greaterThanOrEqualTo(expression, (Comparable) value);
		default:
			return null;
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public SearchOperator getOperator() {
		return operator;
	}

	public void setOperator(SearchOperator operator) {
		this.operator = operator;
	}
}
