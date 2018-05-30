package com.Fee.business.order.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 订单信息
 * @author Zhuan
 *
 */
@Table("fee_order")
@Data
public class Order {

	@Id
	private int id;
	private int customerId;
	private int cusProductId;//采购商产品
	private int supplierId;
	private int supProductId;
	
	private String province;//手机省份
	private String operator;//手机运营商
	private int payStatus;//支付状态 OrderPayMentStatusEnum  0等待支付   1支付成功   2支付失败  3支付手工  4退款申请中  5退款申请成功   6退款申请失败   7 退款成功  8退款失败  9退款异常  10支付关闭
	private int status;//0新增  1充值中  2 等待确认  3 成功  4失败  5手工  OrderStatusEnum
	private int addTime;
	private double price;//售价
	private double cost;//成本
	private int finishTime;
	private String remark;

	private String phone;
	private String openId;
	private int chargeTimes;
	private String wxOrderId;
	
	private String callbackUrl;//回调地址
	private String wxAppId;
	
	/**
	 * 获取发送到wx 的订单id
	 * @return
	 */
	public String getSendOrderId(){
		return "C"+this.supProductId+this.chargeTimes+"O"+id+"O"+this.addTime;
	}
	
	public static int getOwnOrderId(String outOrderId){
		String[] split = outOrderId.split("O");
		return Integer.parseInt(split[1]);
	}
}
