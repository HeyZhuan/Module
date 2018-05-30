package com.Fee.common.enums;

public enum OrderPayMentStatusEnum {
	/**
	 * 等待确认支付
	 */
	PAY_WAIT(0),
	PAY_SUCCESS(1),
	PAY_FAILE(2),
	PAY_MANUAL(3),//支付遇到问题
	PAY_REFUND_APPLICATION_CHARGE(4),//退款申请中
	PAY_REFUND_APPLICATION_SUCCESS(5),//退款申请成功
	PAY_REFUND_APPLICATION_FAIL(6),//退款申请失败
	PAY_REFUND_SUCCESS(7),//退款成功
	PAY_REFUND_FAIL(8),//退款失败
	PAY_REFUND_EXCEPTION(9),//退款异常
	PAY_CLOSE(10),//支付关闭
	;
	
	public int opt;

	private OrderPayMentStatusEnum(int opt) {
		this.opt = opt;
	}
}
