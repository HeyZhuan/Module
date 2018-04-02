package com.Fee.system.role.service;

import java.util.List;

import org.apache.shiro.subject.PrincipalCollection;

public interface RoleService {

	/**
	 * 获取角色菜单Id集合
	 * @param roleId
	 * @return
	 */
	public List<Integer> getRoleMenuIds(Object obj);
	
	/**
	 * 授权相关业务角色
	 * @param roleId
	 * @param newMenus
	 */
	public void updateRoleMenu(int roleId,List<Integer> newMenus);
	
	/**
	 * 清空角色权限缓存
	 * @param pc
	 */
	public void clearUserPermissionCache(PrincipalCollection pc);
	
	/**
	 * 删除对应角色菜单中所有的对应菜单的信息
	 * @param menuId
	 */
	public void deleteRoleMenu(int menuId);
	
}
