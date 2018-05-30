package com.Fee.business.phoneNumber.domain;

import org.nutz.dao.entity.annotation.Table;

import lombok.Data;

@Table("fee_phone_number")
@Data
public class PhoneNumber {

	
	private int id;
	private String phone;//7 位
	private String province;
	private String city;
	private String operator;
	
}
