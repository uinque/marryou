package com.marryou.metadata.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linhy on 2018/6/3.
 */
public class SpDto implements Serializable {

	private static final long serialVersionUID = 595244228988143611L;

	private String str;

	private List<StandardParamsDto> list;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public List<StandardParamsDto> getList() {
		return list;
	}

	public void setList(List<StandardParamsDto> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "SpDto{" +
				"str='" + str + '\'' +
				", list=" + list +
				'}';
	}
}
