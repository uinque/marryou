package com.marryou.metadata.dto;

import java.io.Serializable;

/**
 * Created by linhy on 2018/6/3.
 */
public class StandardParamsDto implements Serializable {

	private static final long serialVersionUID = 4418071748306879674L;

	private String rowTitle;

	private Long id;

	private Long rowId;

	private Long columnId;

	/**
	 * 0=小于等于，1=大于等于
	 */
	private Integer type;

	private Long productId;

	private String val;

	private Integer pointNum;

	public StandardParamsDto() {
	}

	public StandardParamsDto(Long rowId, Long columnId, Integer type, Long productId, String val, Integer pointNum) {
		this.rowId = rowId;
		this.columnId = columnId;
		this.type = type;
		this.productId = productId;
		this.val = val;
		this.pointNum = pointNum;
	}

	public String getRowTitle() {
		return rowTitle;
	}

	public void setRowTitle(String rowTitle) {
		this.rowTitle = rowTitle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Integer getPointNum() {
		return pointNum;
	}

	public void setPointNum(Integer pointNum) {
		this.pointNum = pointNum;
	}

	@Override
	public String toString() {
		return "StandardParamsDto{" +
				"rowTitle='" + rowTitle + '\'' +
				", id=" + id +
				", rowId=" + rowId +
				", columnId=" + columnId +
				", type=" + type +
				", productId=" + productId +
				", val='" + val + '\'' +
				", pointNum=" + pointNum +
				'}';
	}
}
