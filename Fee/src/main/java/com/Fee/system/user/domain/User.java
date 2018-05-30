package com.Fee.system.user.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.Fee.business.customer.domain.Customer;
import com.Fee.common.time.TimeUtils;


@Table("sys_user")
public class User {

	@Id
	private int id;
	private String name;
	private int status;
	private String account;
	private String password;
	private String salt;
	private String email;
	private String phone;
	private int addTime;
	private String plainPassword;
	private String remark;
	private int type;//0 内部人员  1 采购商
	
	
	public User(Customer customer) {
		super();
		this.name = customer.getName();
		this.status = customer.getStatus();
		this.account = customer.getLoginName();
		this.email = customer.getEmail();
		this.phone = customer.getPhone();
		this.addTime = TimeUtils.getTimeStamp();
		this.plainPassword =customer.getPassword();
		this.type = 1;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAddTime() {
		return addTime;
	}
	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}
	public String getPlainPassword() {
		return plainPassword;
	}
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
}
