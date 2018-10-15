package com.marryou.dto.request;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by linhy on 2018/6/3.
 */
public class BasePageRequest<T> implements Serializable {

	private static final long serialVersionUID = 7404255557983898201L;

	private int pageIndex = 0;

	private int pageSize = 20;

	private String sortField;

	private String sortOrder;

	private T params;

	public BasePageRequest() {
	}

	public BasePageRequest(int pageIndex, int pageSize, String sortField, String sortOrder) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	public BasePageRequest(int pageIndex, int pageSize, String sortField, String sortOrder, T params) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.params = params;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public T getParams() {
		return params;
	}

	public void setParams(T params) {
		this.params = params;
	}

	public PageRequest toPageRequest() {
		PageRequest pageRequest = null;
		if (StringUtils.isNotBlank(getSortField()) && StringUtils.isNotBlank(getSortOrder())) {
			Sort sort = new Sort(Sort.Direction.fromString(getSortOrder()), getSortField());
			sort.and(new Sort(Sort.Direction.DESC, "createTime"));
			pageRequest = new PageRequest(getPageIndex(), getPageSize(), sort);
		} else {
			Sort sort = new Sort(Sort.Direction.DESC, "createTime");
			pageRequest = new PageRequest(getPageIndex(), getPageSize(),sort);
		}
		return pageRequest;
	}

	@Override
	public String toString() {
		return "BasePageRequest{" +
				"pageIndex=" + pageIndex +
				", pageSize=" + pageSize +
				", sortField='" + sortField + '\'' +
				", sortOrder='" + sortOrder + '\'' +
				", params=" + params +
				'}';
	}
}
