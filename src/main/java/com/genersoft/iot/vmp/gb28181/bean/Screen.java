package com.genersoft.iot.vmp.gb28181.bean;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 国标设备/平台
 * @author lin
 */
@Schema(description = "国标设备/平台")
public class Screen {

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private int Id;

	/**
	 * 设备名
	 */
	@Schema(description = "地址")
	private String Address;

	/**
	 * 生产厂商
	 */
	@Schema(description = "序号")
	private int Index;



	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String name) {
		this.Address = Address;
	}

	public int getIndex() {
		return Index;
	}

	public void setIndex(int Id) {
		this.Index = Index;
	}
}
