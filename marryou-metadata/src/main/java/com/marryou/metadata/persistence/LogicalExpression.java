package com.marryou.metadata.persistence;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Lists;

/**
 * Created by linhy on 2016/1/17.
 */
public class LogicalExpression implements SearchFilter {
	private List<SearchFilter[]> filters;
	private SearchOperator operator;

	public LogicalExpression(List<SearchFilter[]> filters, SearchOperator operator) {
		this.filters = filters;
		this.operator = operator;
	}

	public LogicalExpression(SearchFilter[] filters, SearchOperator operator) {
		List<SearchFilter[]> filter = Lists.newArrayList();
		filter.add(filters);
		this.filters = filter;
		this.operator = operator;
	}

	@Override
	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = Lists.newArrayList();
		for (SearchFilter[] filter : filters) {
			List<Predicate> localPredicates = Lists.newArrayList();
			for (SearchFilter searchFilter : filter) {
				localPredicates.add(searchFilter.toPredicate(root, query, builder));
			}
			if(operator == SearchOperator.OR){
            	predicates.add(builder.or(localPredicates.toArray(new Predicate[localPredicates.size()])));
            }else{
            	predicates.add(builder.and(localPredicates.toArray(new Predicate[localPredicates.size()])));
            }
		}
		switch (operator) {
		case OR:
			return builder.or(predicates.toArray(new Predicate[predicates.size()]));
		default:
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		}

	}
}
