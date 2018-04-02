package com.Fee.system.user.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("sys_user_role")
public class UserRole {

	@Id
	private int id;	
	private int userId;
	private int roleId;
	private int addTime;
	
	public UserRole() {
	}

	
	public UserRole(int userId, int roleId, int addTime) {
		this.userId = userId;
		this.roleId = roleId;
		this.addTime = addTime;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getAddTime() {
		return addTime;
	}

	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}
	
	
	
	
	
}
