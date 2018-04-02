package com.Fee.system.user.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.Fee.system.user.domain.User;
import com.Fee.system.user.realm.UserRealm.ShiroUser;
import com.Fee.system.user.service.UserService;

public class UserUtil {
	@Autowired
	private static UserService userService;

	/**
	 * 获取当前用户对象shiro
	 * 
	 * @return shirouser
	 */
	public static ShiroUser getCurrentShiroUser() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user;
	}

	/**
	 * 获取当前用户session
	 * 
	 * @return session
	 */
	public static Session getSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;
	}

	/**
	 * 获取当前用户httpsession
	 * 
	 * @return httpsession
	 */
	public static Session getHttpSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;
	}

	public static String getUserName() {
		return getCurrentUser().getName();
	}

	/**
	 * 获取当前用户对象
	 * 
	 * @return user
	 */
	public static User getCurrentUser() {
		Session session = SecurityUtils.getSubject().getSession();
		if (null != session) {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				ShiroUser shiroUser = UserUtil.getCurrentShiroUser();
				user = userService.getUser(shiroUser.getName(), false);
				session.setAttribute("user", user);
			}
			return user;
		} else {
			return null;
		}
	}
}
