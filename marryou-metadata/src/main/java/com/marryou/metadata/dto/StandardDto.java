package com.marryou.metadata.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by linhy on 2018/6/3.
 */
public class StandardDto implements Serializable {

	private static final long serialVersionUID = 8885031214208412966L;

	private Long id;

	private String name;

	private String oneLevel;

	private String twoLevel;

	private String threeLevel;

	private Long productId;

	private Integer pointNum;
	/**
	 * 0=小于等于，1=大于等于
	 */
	private Integer type;

	public StandardDto() {
	}

	public StandardDto(Long id, String name, String oneLevel, String twoLevel, String threeLevel,
			Long productId, Integer pointNum,Integer type) {
		this.id = id;
		this.name = name;
		this.oneLevel = oneLevel;
		this.twoLevel = twoLevel;
		this.threeLevel = threeLevel;
		this.productId = productId;
		this.pointNum = pointNum;
		this.type = type;
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

	public String getOneLevel() {
		return oneLevel;
	}

	public void setOneLevel(String oneLevel) {
		this.oneLevel = oneLevel;
	}

	public String getTwoLevel() {
		return twoLevel;
	}

	public void setTwoLevel(String twoLevel) {
		this.twoLevel = twoLevel;
	}

	public String getThreeLevel() {
		return threeLevel;
	}

	public void setThreeLevel(String threeLevel) {
		this.threeLevel = threeLevel;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getPointNum() {
		return pointNum;
	}

	public void setPointNum(Integer pointNum) {
		this.pointNum = pointNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "StandardDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", oneLevel=" + oneLevel +
				", twoLevel=" + twoLevel +
				", threeLevel=" + threeLevel +
				", productId=" + productId +
				", pointNum=" + pointNum +
				", type=" + type +
				'}';
	}
}
