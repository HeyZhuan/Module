package com.Fee.business.wxUser.cmd;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.Fee.business.wxUser.domain.WxUser;
import com.Fee.business.wxUser.service.WxUserService;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/wxUser")
public class WxUserCmd {
	private static Logger log = LoggerFactory.getLogger(WxUserCmd.class);
	
	@Autowired
	private WxUserService wxUserService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/wxUser/wxUserList";
	}

	/**
	 * 获取微信用户信息 数据
	 */
	@RequestMapping(value = "wxUserList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(WxUser.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加微信用户信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:wxUser:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		WxUser wxUser = new WxUser();
		model.addAttribute("wxUser", wxUser);
		model.addAttribute("action", "create");
		return "fee/wxUser/wxUserForm";
	}

	/**
	 * 添加微信用户信息
	 * 
	 * @param wxUser
	 * @param model
	 */
	@RequiresPermissions("fee:wxUser:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated WxUser wxUser) {
		wxUserService.addWxUser(wxUser);
		return "success";
	}

	/**
	 * 修改微信用户信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:wxUser:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		WxUser wxUser = wxUserService.getWxUser(id);
		model.addAttribute("wxUser", wxUser);
		model.addAttribute("action", "update");
		return "fee/wxUser/wxUserForm";
	}

	/**
	 * 修改微信用户信息
	 * 
	 * @param wxUser
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:wxUser:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody WxUser wxUser) {
		wxUserService.updateWxUser(wxUser);
		return "success";
	}

	/**
	 * 删除微信用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:wxUser:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		wxUserService.deleteWxUser(ids);
		return "success";
	}
}
