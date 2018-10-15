package com.marryou.metadata.persistence;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;

/**
 * Created by linhy on 2016/1/17.
 */
public class SearchFilters<T> implements Specification<T> {
	private List<SearchFilter> searchFilters = Lists.newArrayList();

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (!searchFilters.isEmpty()) {
			List<Predicate> predicates = Lists.newArrayList();
			for (SearchFilter searchFilter : searchFilters) {
				predicates.add(searchFilter.toPredicate(root, query, builder));
			}
			if (predicates.size() > 0) {
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}
		return builder.conjunction();
	}

	/**
	 * 添加简单条件表达式
	 * @param searchFilter
	 */
	public SearchFilters<T> add(SearchFilter searchFilter) {
		if (null != searchFilter){
			searchFilters.add(searchFilter);
		}
		return this;
	}

}
