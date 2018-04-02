package com.Fee.common.enums;

public enum ContentTypeEnum {
	
	PRODUCT(1),
	SUPPLIER(2),
	CUSTOMER(3),
	WAREHOUSES(4),
	CATE(5),
	IN(6),//入库
	OUT(7),//出库
	ADDPRICE(8),//添加资金入资金池
	OUTPRICE(9),//提取资金从资金池
	UPDATE_STOCK(10);//更新产品库存
	
	
	public int opt;
	private ContentTypeEnum(int i){
		this.opt=i;
	}
}
