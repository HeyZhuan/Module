package com.Fee.system.user.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Chain;
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

import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;
import com.Fee.system.role.domain.Role;
import com.Fee.system.user.domain.User;
import com.Fee.system.user.service.UserService;

@Controller
@RequestMapping("system/user")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;


	@Autowired
	private BaseService baseService;

	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public String updateStatus(HttpServletRequest request, Integer[] ids, Integer status) {
		if (status == 0 || status == 1) {// 开通,冻结
			baseService.update(User.class, Chain.make("status", status), Cnd.where("id", NutzType.IN.opt, ids));
		}
		return "success";
	}

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/user/userList";
	}

	/**
	 * 获取用户json
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(User.class, cnd, pager);
			
			List<User> users = result.getList(User.class);
			for (User user : users) {
				user.setPassword("");
				user.setSalt("");
				user.setPlainPassword("");
			}
			return ParamUtils.getEasyUIData(result);
		} catch (Exception e) {
			log.info(e.getMessage(),e);
			log.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 添加用户跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model, HttpSession session) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("action", "create");
		List<Role> all = baseService.getAll(Role.class);
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		for (Role role : all) {
			roleMap.put(role.getId(), role.getName());
		}
		model.addAttribute("roleMap", roleMap);
		return "system/user/userForm";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated User user, Model model, HttpServletRequest req, Integer roleId) {
		try {
			
			userService.addUser(user);
			userService.addUserRole(user.getId(), roleId);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, JsonUtils.obj2Json(user)+",--roleId:"+roleId, e);
		}
		return "success";
	}

	/**
	 * 修改用户跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", baseService.get(User.class, id));
		model.addAttribute("action", "update");
		return "system/user/userForm";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody User user, Model model) {
		baseService.update(user);
		return "success";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id,HttpSession session) {
		baseService.delete(User.class, Cnd.where("id", NutzType.EQ.opt, id));
		// 将对应权限删除
		userService.deleteUserRole(id);
		return "success";
	}

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "userInfo", method = RequestMethod.GET)
	public String updatePwdPage() {
		return "system/user/userInfo";
	}

	/**
	 * 修改密码跳转
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.GET)
	public String updatePwdForm(Model model, HttpSession session) {
		model.addAttribute("user", (User) session.getAttribute("user"));
		return "system/user/updatePwd";
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public String updatePwd(String oldPassword, @Validated @ModelAttribute @RequestBody User user, HttpSession session) {
		User use = (User)session.getAttribute("user");
		if(use.getAccount()=="csz"){
			return "原密码错误！";
		}
		if (userService.checkPawd(use, oldPassword)) {
			userService.updateUser(user, true);
			session.setAttribute("user", user);
			return "success";
		} else {
			return "原密码错误！";
		}

	}

	/**
	 * 弹窗页-用户拥有的角色
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{userId}/userRole")
	public String getUserRole(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/user/userRoleList";
	}

	/**
	 * 获取用户拥有的角色ID集合
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{id}/role")
	@ResponseBody
	public List<Integer> getRoleIdList(@PathVariable("id") Integer id) {
		return userService.getUserRoleIds(id);
	}

	/**
	 * 修改用户拥有的角色
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:roleUpd")
	@RequestMapping(value = "{id}/updateRole")
	@ResponseBody
	public String updateUserRole(@PathVariable("id") Integer id, @RequestBody List<Integer> newRoleList) {
		try {
			userService.updateUserRole(id, newRoleList);
		} catch (Exception e) {
			log.info(e.getMessage(),e);
			log.error(e.getMessage(),e);
		}
		return "success";
	}
	
	/**
	 * 重置密码为123456
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequestMapping(value = "resetPwd")
	@ResponseBody
	public String resetPwd(Integer id) {
		try {
			User user = baseService.get(User.class, id);
			if(user!=null){
				user.setPlainPassword("123456");
				userService.updateUser(user, true);
			}
			
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
		return "success";
	}
}
