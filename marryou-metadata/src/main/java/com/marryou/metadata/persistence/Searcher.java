package com.marryou.metadata.persistence;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.MatchMode;

/**
 * 条件构造器
 * Created by wuwei on 2016/1/17.
 */
public class Searcher {

	private Searcher() {
	}

	/**
	 * 等于
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression eq(String fieldName, Object value) {
		return new SimpleExpression(fieldName, value, SearchOperator.EQ);
	}

	/**
	 * 不等于
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression ne(String fieldName, Object value) {
		return new SimpleExpression(fieldName, value, SearchOperator.NE);
	}

	/**
	 * 模糊匹配
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression like(String fieldName, String value) {
		return new SimpleExpression(fieldName, value, SearchOperator.LIKE);
	}

	/**
	 *
	 * @param fieldName
	 * @param value
	 * @param matchMode
	 * @return
	 */
	public static SimpleExpression like(String fieldName, String value, MatchMode matchMode) {
		return null;
	}

	/**
	 * 大于
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression gt(String fieldName, Object value) {
		return new SimpleExpression(fieldName, value, SearchOperator.GT);
	}

	/**
	 * 小于
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression lt(String fieldName, Object value) {
		return new SimpleExpression(fieldName, value, SearchOperator.LT);
	}

	/**
	 * 小于等于
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression lte(String fieldName, Object value) {
		return new SimpleExpression(fieldName, value, SearchOperator.LTE);
	}

	/**
	 * 大于等于
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression gte(String fieldName, Object value) {
		return new SimpleExpression(fieldName, value, SearchOperator.GTE);
	}

	/**
	 * 并且
	 * @param searchFilters
	 * @return
	 */
	public static LogicalExpression and(SearchFilter... searchFilters) {
		return new LogicalExpression(searchFilters, SearchOperator.AND);
	}

	/**
	 * 或者
	 * @param searchFilters
	 * @return
	 */
	public static LogicalExpression or(SearchFilter... searchFilters) {
		return new LogicalExpression(searchFilters, SearchOperator.OR);
	}

	/**
	 * 或者
	 * @param searchFilters
	 * @return
	 */
	public static LogicalExpression or(List<SearchFilter[]> searchFilters) {
		return new LogicalExpression(searchFilters, SearchOperator.OR);
	}

    /**
     * 包含于
     * @param fieldName
     * @param value
     * @return
     */
    public static InExpression in(String fieldName, Collection value) {
        return new InExpression(fieldName,value);
    }
}
