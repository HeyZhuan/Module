package com.Fee.business.customerAudit.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 采购商注资审核信息
 * @author Zhuan
 *
 */
@Table("fee_customer_audit")
@Data
public class CustomerAudit {

	@Id
	private int id;
	private int customerId;
	private String customerName;
	private double price;
	private int type;//0 人工注资  1人工扣款  2授信
	private int addTime;
	private String addUser;
	private int auditTime;
	private int auditStatus;//0待审核   1通过 2 不通过 3授信还款
	private String auditUser;
	private String remark;

	
}
