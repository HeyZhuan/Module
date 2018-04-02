package com.Fee.system.menu.service;

import java.util.List;

import com.Fee.system.menu.domain.Menu;

public interface MenuService {

	/**
	 * 获取系统所有菜单
	 * @return
	 */
	public List<Menu> getMenus(Object obj);
	
	/**
	 * 获取系统所有菜单
	 * @return
	 */
	public List<Menu> getMenus();

	/**
	 * 添加菜单
	 * @param menu
	 */
	public void addMenu(Menu menu);
	
	/**
	 * 更新菜单
	 * @param menu
	 * @return
	 */
	public int updateMenu(Menu menu);

	public Menu getIdByNameAndPid(String string, int i);
}
