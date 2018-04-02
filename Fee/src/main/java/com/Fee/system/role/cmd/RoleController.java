package com.Fee.system.role.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Fee.common.db.CommonDao;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.role.domain.Role;
import com.Fee.system.role.service.RoleService;
import com.Fee.system.user.domain.User;
import com.Fee.system.user.service.UserService;

@Controller
@RequestMapping("system/role")
public class RoleController {
	private static Logger log = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;

	@Autowired
	private BaseService baseService;

	/**
	 * 默认页面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/role/roleList";
	}

	/**
	 * 角色集合(JSON)
	 * 获取业务角色信息
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(Role.class, cnd, pager);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", JsonUtils.json2Obj(JsonUtils.obj2Json(result.getList()), List.class));
			map.put("total", result.getPager().getRecordCount());
			return ParamUtils.getEasyUIData(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加角色跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model, HttpSession session) {
		Role role = new Role();
		model.addAttribute("role", role);
		model.addAttribute("action", "create");
		return "system/role/roleForm";
	}

	/**
	 * 添加角色---shopId buxuyao
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated Role role) {
		role.setAddTime(TimeUtils.getTimeStamp());
		baseService.save(role);
		return "success";
	}

	/**
	 * 修改角色跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("role", baseService.get(Role.class, id));
		model.addAttribute("action", "update");
		return "system/role/roleForm";
	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute("role") Role role, HttpSession session) {
		baseService.update(role);
		return "success";
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id, HttpSession session) {
		baseService.delete(Role.class, id);
		return "success";
	}

	/**
	 * 获取角色拥有的权限ID集合
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:permView")
	@RequestMapping("{id}/json")
	@ResponseBody
	public List<Integer> getRoleMenus(@PathVariable("id") Integer id) {
		List<Integer> menuIdList = roleService.getRoleMenuIds(id);
		return menuIdList;
	}

	/**
	 * 修改角色权限
	 * 
	 * @param id
	 *            角色Id
	 * @param newRoleList
	 *            权限集合
	 * @return
	 */
	@RequiresPermissions("sys:role:permUpd")
	@RequestMapping(value = "{id}/updateMenu")
	@ResponseBody
	public String updateRolePermission(@PathVariable("id") Integer id, @RequestBody List<Integer> newRoleIdList, HttpSession session) {
		roleService.updateRoleMenu(id, newRoleIdList);
		return "success";
	}
}
