package com.Fee.system.role.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("sys_role_menu")
public class RoleMenu {
	
	@Id
	private int id;
	private int roleId;
	private int menuId;
	private int addTime;
	
	public RoleMenu() {
		// TODO Auto-generated constructor stub
	}
	
	public RoleMenu(int menuId, int roleId,int addTime) {
		this.menuId = menuId;
		this.roleId = roleId;
		this.addTime = addTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getAddTime() {
		return addTime;
	}

	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}

	
	
	
}
