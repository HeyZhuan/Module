package com.Fee.common.enums;

public enum WxConfigEnum {
	
	APP_ID("wxc0a2c18434388153"),
	APP_SECRET("673f0ff9f28903cc2d40e584b9f7d658"),
	PAY_SECRET("huishengzhifumiyao13726902126666"),
	MCH_ID("1504169681"),
	APP_CERT_ADRESS("F:/cert/apiclient_cert.p12"),//  /home/tomcat/cert/apiclient_cert.p12
	WX_ID("gh_fa43a7d5103e"),//原始id
	DOMAIN_NAME("http://zhuansman.ticp.io"),//域名
	CALL_BACK_URL("/weixinpay_callback.do"),
	CHARGE_HTML_RUL("/static/fee.html"),
	//zhuansman.top
	/**
	 * 微信IP
	 */
	IP("121.46.20.204"),
	
	/**
	 * ------- 交易类型 S -------
	 */
	JSAPI("JSAPI"),
	NATIVE("NATIVE"),
	APP("APP");
	
	
	public String opt;
	private WxConfigEnum(String i){
		this.opt=i;
	}
}

