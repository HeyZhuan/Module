package com.Fee.business.customer.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import lombok.Data;
/**
 * 采购商信息
 * @author Zhuan
 *
 */
@Table("fee_customer")
@Data
public class Customer {

	@Id
	private int id;
	private String name;
	private String phone;
	private String contacts;
	private double balance;
	private int addTime;
	private String address;
	private String apiKey;
	private int status;
	private String allowIp;
	private String loginName;
	private String email;
	private String password;
	private String remark;

	
}
