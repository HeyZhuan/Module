package com.Fee.business.customerChargeRecord.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.Fee.common.time.TimeUtils;

import lombok.Data;
/**
 * 采购商充值信息记录
 * @author Zhuan
 *
 */
@Table("fee_customer_charge_record")
@Data
public class CustomerChargeRecord {

	
	public enum CustomerChargeRecordEnum{
		
		ZHUZI(0),
		KOUKUAN(1),
		SHOUXIN(2),
		CHARGE_KOUKUAN(3),
		CHARGE_RETURN(4);
		
		public int opt;
		CustomerChargeRecordEnum(int opt){
			this.opt=opt;
		}
	}
	
	@Id
	private int id;
	private int customerId;
	private double price;
	private int type;//0注资   1人工扣款  2授信  3充值扣款   4失败返还
	private int addTime;
	private String orderId;
	private String remark;
	public CustomerChargeRecord(int customerId, double price, int type) {
		super();
		this.customerId = customerId;
		this.price = price;
		this.type = type;
		this.addTime = TimeUtils.getTimeStamp();

	}
	public CustomerChargeRecord() {
		super();
	}
	
}
