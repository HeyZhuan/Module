package com.Fee.common.enums;

public enum StatusType {
	/**
	 * 冻结
	 */
	FREEZE(0),
	/**
	 * 正常
	 */
	NORMAL(1);
	
	public int opt;
	private StatusType(int i){
		this.opt=i;
	}
}
