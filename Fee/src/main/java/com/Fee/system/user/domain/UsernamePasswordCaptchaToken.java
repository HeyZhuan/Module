package com.Fee.system.user.domain;


import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 扩展添加验证码-继承用户验证类
 * @author ty
 * @date 2014年12月2日 下午10:45:57
 */
public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	
	private String securitycode;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getSecuritycode() {
		return securitycode;
	}

	public void setSecuritycode(String securitycode) {
		this.securitycode = securitycode;
	}

	public UsernamePasswordCaptchaToken() {
		super();
	}

	public UsernamePasswordCaptchaToken(String username, char[] password,boolean rememberMe, String host, String captcha,String securitycode) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.securitycode = securitycode;
	}

}