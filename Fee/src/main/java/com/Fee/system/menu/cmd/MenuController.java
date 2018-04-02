package com.Fee.system.menu.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Fee.common.json.JsonUtils;
import com.Fee.common.service.BaseService;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.menu.domain.Menu;
import com.Fee.system.menu.service.MenuService;
import com.Fee.system.role.domain.Role;
import com.Fee.system.role.service.RoleService;
import com.Fee.system.user.domain.User;
import com.Fee.system.user.service.UserService;
import com.Fee.system.user.util.UserUtil;

@Controller
@RequestMapping("system/menu")
public class MenuController {
	public static Logger log = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@Autowired
	private BaseService baseService;

	/**
	 * 菜单页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String menuPage() {
		return "system/menu/menuList";
	}

	/**
	 * 获取当前登录用户的菜单
	 * 
	 * @return
	 */
	@RequestMapping("i/json")
	@ResponseBody
	public List<Menu> getCurrentUserMenus() {
		User user = UserUtil.getCurrentUser();
		List<Integer> userRoleIds = userService.getUserRoleIds(user.getId());
		List<Integer> userRoleMenusIds = roleService.getRoleMenuIds(userRoleIds);
		List<Menu> menuList = menuService.getMenus(userRoleMenusIds);
		return menuList;
	}

	/**
	 * 菜单集合(JSON)
	 */
	@RequiresPermissions("sys:perm:view")
	@RequestMapping(value = "menu/json", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> menuDate(HttpSession session) {
		List<Menu> menuList = new ArrayList<Menu>();
		menuList = menuService.getMenus();

		List<Integer> pidList = new ArrayList<Integer>();
		for (Menu menu : menuList) {
			if (menu.getPid() != 0) // 0为总的父id
				pidList.add(menu.getPid());
		}
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		for (Menu menu : menuList) {

			Map<String, Object> json2Obj = JsonUtils.json2Obj(JsonUtils.obj2Json(menu), Map.class);
			Integer id = (Integer) json2Obj.get("id");
			Boolean hasSun = false;
			for (Integer pid : pidList) {
				if (pid.toString().equals(id.toString())) {
					hasSun = true;
					break;
				}
			}
			json2Obj.put("state_", json2Obj.get("status"));
			if (hasSun) {
				json2Obj.put("state", "closed");
			}
			mList.add(json2Obj);
		}

		return mList;
	}

	/**
	 * 菜单集合(JSON),用于角色菜单查看
	 */
	@RequestMapping(value = "menuJson", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> menuDateJson(HttpSession session) {
		List<Menu> menuList = new ArrayList<Menu>();
		menuList = menuService.getMenus();

		List<Integer> pidList = new ArrayList<Integer>();
		for (Menu menu : menuList) {
			if (menu.getPid() != 0) // 0为总的父id
				pidList.add(menu.getPid());
		}
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		for (Menu menu : menuList) {

			Map<String, Object> json2Obj = JsonUtils.json2Obj(JsonUtils.obj2Json(menu), Map.class);
			Integer id = (Integer) json2Obj.get("id");
			Boolean hasSun = false;
			for (Integer pid : pidList) {
				if (pid.toString().equals(id.toString())) {
					hasSun = true;
					break;
				}
			}
			json2Obj.put("state_", json2Obj.get("status"));
			if (hasSun) {
				json2Obj.put("state", "closed");
			}
			mList.add(json2Obj);
		}

		return mList;
	}

	/**
	 * 添加菜单跳转
	 */
	@RequestMapping(value = "menu/create", method = RequestMethod.GET)
	public String menuCreateForm(Model model) {
		Menu menu = new Menu();
		model.addAttribute("menu", menu);
		model.addAttribute("action", "create");
		return "system/menu/menuForm";
	}

	/**
	 * 添加权限/菜单
	 */
	@RequiresPermissions("sys:perm:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated Menu menu, Model model, HttpSession session) {
		System.out.println(JsonUtils.obj2Json(menu));
		menu.setAddTime(TimeUtils.getTimeStamp());
		menuService.addMenu(menu);
		// TODO 应该选择该店铺里面的超级管理员
		String sql = "select * from sys_role where name = 'admin'";
		List<Role> list = baseService.query(Role.class, sql, null);

		// 自动将添加的菜单放到admin的权限中
		if (list.size() == 1) {
			Role role = list.get(0);
			if (role != null) {
				if (menu.getId() != 0)
					baseService.save(
							"insert sys_role_menu(menuId,roleId,addTime) values(" + menu.getId() + "," + role.getId()+ "," + TimeUtils.getTimeStamp() + ")", null);
			}
		}
		return "success";
	}

	/**
	 * 删除菜单
	 */
	@RequiresPermissions("sys:perm:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		baseService.delete(Menu.class, id);

		// 删除对应角色的菜单
		roleService.deleteRoleMenu(id);

		return "success";
	}

	/**
	 * 修改菜单跳转
	 */
	@RequestMapping(value = "menu/update/{id}", method = RequestMethod.GET)
	public String updateMenuForm(@PathVariable("id") Integer id, Model model) {
		List<Menu> list = menuService.getMenus(id);
		model.addAttribute("menu", list.get(0));
		model.addAttribute("action", "update");
		return "system/menu/menuForm";
	}
	
	/**
	 * 修改菜单跳转
	 */
	@RequestMapping(value = "menu/update", method = RequestMethod.POST)
	public String updateMenu(Menu menu) {
		menuService.updateMenu(menu);
		return "success";
	}
}
