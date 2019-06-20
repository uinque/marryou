package com.marryou.metadata.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhy on 2018/6/3.
 */
public class StandardTableDto implements Serializable {

	private static final long serialVersionUID = 6191963037996846450L;

	private Long rowId;

	private String rowTitle;

	private Integer orderSort;

	List<StandardParamsDto> params;

	public StandardTableDto() {
	}

	public StandardTableDto(Long rowId, String rowTitle, Integer orderSort) {
		this.rowId = rowId;
		this.rowTitle = rowTitle;
		this.orderSort = orderSort;
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public String getRowTitle() {
		return rowTitle;
	}

	public void setRowTitle(String rowTitle) {
		this.rowTitle = rowTitle;
	}

	public Integer getOrderSort() {
		return orderSort;
	}

	public void setOrderSort(Integer orderSort) {
		this.orderSort = orderSort;
	}

	public List<StandardParamsDto> getParams() {
		return params;
	}

	public void setParams(List<StandardParamsDto> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "StandardTableDto{" +
				"rowId=" + rowId +
				", rowTitle='" + rowTitle + '\'' +
				", orderSort=" + orderSort +
				", params=" + params +
				'}';
	}
}
