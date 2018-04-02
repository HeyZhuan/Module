package com.Fee.common.enums;

public enum WorkTypeEnum {
	
	ADD(0),
	UPDATE(1),
	DELETE(2),
	WORK(3),//资金池操作
	LOSE_PRODUCT(4);//破损/退货
	
	
	public int opt;
	private WorkTypeEnum(int i){
		this.opt=i;
	}
}
