package com.Fee.business.supplierChargeRecord.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.Fee.common.time.TimeUtils;

import lombok.Data;
/**
 * 供货商充值记录信息
 * @author Zhuan
 *
 */
@Table("fee_supplier_charge_record")
@Data
public class SupplierChargeRecord {

	@Id
	private int id;
	private int supplierId;
	private int orderId;
	private int type;//0人工注资   1人工扣款  2充值扣款  3失败还款
	private int addTime;
	private double price;
	private String remark;
	public SupplierChargeRecord(int supplierId, int type, double price) {
		super();
		this.supplierId = supplierId;
		this.type = type;
		this.addTime = TimeUtils.getTimeStamp();
		this.price = price;
	}
	public SupplierChargeRecord() {
		super();
	}

	
	
	
}
