package com.Fee.common.enums;

/**
 * 订单状态 
 * @author Zhuan
 *
 */
public enum OrderStatusEnum {

	ORDER_ADD(0),//新增---入库
	ORDER_CHARGE(1),//充值中---进入发送线程
	ORDER_WAIT(2),//等待确认---收到供货商的确认信息
	ORDER_SUCCESS(3),
	ORDER_FAILE(4),
	ORDER_MANUAL(5),//手工处理,订单遇到问题
	;
	
	public int opt;

	private OrderStatusEnum(int opt) {
		this.opt = opt;
	}
	
	
	public static  OrderStatusEnum getOrderStatys(int status){
		for (OrderStatusEnum os : OrderStatusEnum.values()) {
			if(os.opt==status){
				return os;
			}
		}
		return null;
	}
}
