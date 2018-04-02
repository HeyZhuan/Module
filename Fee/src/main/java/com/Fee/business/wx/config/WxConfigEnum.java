package com.Fee.business.wx.config;

public enum WxConfigEnum {
	
	APP_ID("1"),
	APP_SECRET("1"),
	APP_PAY_SECRET("1"),
	APP_SHOP_ID("1"),
	APP_CERT_ADRESS("1"),
	CALL_BACK_URL("1"),
	CHARGE_HTML_RUL("1");
	
	
	public String opt;
	private WxConfigEnum(String i){
		this.opt=i;
	}
}
