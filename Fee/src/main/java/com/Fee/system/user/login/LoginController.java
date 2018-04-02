package com.Fee.system.user.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Fee.common.service.BaseService;
import com.Fee.system.user.domain.User;
import com.Fee.system.user.realm.UserRealm.ShiroUser;
import com.Fee.system.user.service.UserService;
import com.Fee.system.user.util.UserUtil;

/**
 * 
 * @author cm
 * 
 *         2016年8月16日 上午10:42:35
 */
@Controller
@RequestMapping(value = "/a")
public class LoginController {
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private BaseService baseService;

	/**
	 * 默认页面
	 * 
	 * @return
	 */

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		
		if (subject.isAuthenticated() || subject.isRemembered()) {
			Session session = subject.getSession();
			User user = null;
			if (session != null) {
				user = (User) session.getAttribute("user");

				if (user == null) {
					// 不知道为什么,session在tomcat重启的时候user会丢失
					ShiroUser shiroUser = UserUtil.getCurrentShiroUser();
					if (shiroUser != null) {
						user = userService.getUser(shiroUser.getLoginName(), false);
						session.setAttribute("user", user);
					}
				}
				return "redirect:/a";
			}
		
		}
		return "system/login";
	}

	/**
	 * 登录失败
	 * 
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "system/login";
	}

	/**
	 * 登出
	 * 
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logout")
	public String logout(Model model) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "system/login";
	}

}
