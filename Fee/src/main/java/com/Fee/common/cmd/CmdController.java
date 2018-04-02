package com.Fee.common.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imlianai.common.exception.CommandException;
import com.imlianai.common.executor.CommandExecutor;

/**
 * 指令执行入口
 * 
 * @author RUIZ
 */
@Controller
public class CmdController{

	private static Logger exptLogger = Logger.getLogger(CmdController.class);

	private static String PNAME_CMD = "cmd";

	@Autowired
	private CommandExecutor commandExecutor;

	@RequestMapping("/call.do")
	public String call(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String page = null;
		String cmd = request.getParameter(PNAME_CMD);
		try {
			page = commandExecutor.execute(cmd, request, response);
		} catch(CommandException e) {			
			exptLogger.error("cmd:" + cmd, e);
		} catch(Throwable t) {
			exptLogger.error("cmd:" + cmd, t);
		}
		if(page == null) {
			return null;
		}
		return StringUtils.isNotBlank(page) ? page : null;
	}
	
}
