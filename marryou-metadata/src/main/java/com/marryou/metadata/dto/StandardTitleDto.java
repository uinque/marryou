package com.marryou.metadata.dto;

import java.io.Serializable;

/**
 * Created by linhy on 2018/6/3.
 */
public class StandardTitleDto implements Serializable {

	private static final long serialVersionUID = 1104426400754540511L;

	private Long id;

	/**
	 * 指标标题名称
	 */
	private String name;

	/**
	 * 0=row 1=column
	 */
	private Integer type;

	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 顺序
	 */
	private Integer orderSort;

	public StandardTitleDto() {
	}

	public StandardTitleDto(Long id, String name, Integer type, Long productId, Integer orderSort) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.productId = productId;
		this.orderSort = orderSort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getOrderSort() {
		return orderSort;
	}

	public void setOrderSort(Integer orderSort) {
		this.orderSort = orderSort;
	}

	@Override
	public String toString() {
		return "StandardTitleDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", type=" + type +
				", productId=" + productId +
				", orderSort=" + orderSort +
				'}';
	}
}
