package com.Fee.business.logInfo.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.Fee.business.logInfo.domain.LogInfo;
import com.Fee.business.logInfo.service.LogInfoService;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;

import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;


@Controller
@RequestMapping("wareHouses/logInfo")
public class LogInfoCmd {
	private static Logger log = LoggerFactory.getLogger(LogInfoCmd.class);
	
	@Autowired
	private LogInfoService logInfoService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "wareHouses/logInfo/logInfoList";
	}

	/**
	 * 获取操作日志信息 数据
	 */
	@RequestMapping(value = "logInfoList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(LogInfo.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加操作日志信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("wareHouses:logInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		LogInfo logInfo = new LogInfo();
		model.addAttribute("logInfo", logInfo);
		model.addAttribute("action", "create");
		return "wareHouses/logInfo/logInfoForm";
	}

	/**
	 * 添加操作日志信息
	 * 
	 * @param logInfo
	 * @param model
	 */
	@RequiresPermissions("wareHouses:logInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated LogInfo logInfo) {
		logInfoService.addLogInfo(logInfo);
		return "success";
	}

	/**
	 * 修改操作日志信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wareHouses:logInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		LogInfo logInfo = logInfoService.getLogInfo(id);
		model.addAttribute("logInfo", logInfo);
		model.addAttribute("action", "update");
		return "wareHouses/logInfo/logInfoForm";
	}

	/**
	 * 修改操作日志信息
	 * 
	 * @param logInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wareHouses:logInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody LogInfo logInfo) {
		logInfoService.updateLogInfo(logInfo);
		return "success";
	}

	/**
	 * 删除操作日志信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("wareHouses:logInfo:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		logInfoService.deleteLogInfo(ids);
		return "success";
	}
}
