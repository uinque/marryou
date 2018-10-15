package com.marryou.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by linhy on 2018/6/3.
 */
public class PageResponse<T> implements Serializable {

	private static final long serialVersionUID = -1657510616167719668L;

	private Long total;

	private int totalPage;

	private int currentPage;

	private int pageSize;

	private List<T> rows;

	public PageResponse() {
	}

	public PageResponse(Long total, int totalPage, int currentPage, int pageSize, List<T> rows) {
		this.total = total;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageResponse{" +
				"total=" + total +
				", totalPage=" + totalPage +
				", currentPage=" + currentPage +
				", pageSize=" + pageSize +
				", rows=" + rows +
				'}';
	}
}
