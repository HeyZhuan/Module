package com.Fee.business.supplierAudit.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 供货商注资审核信息
 * @author Zhuan
 *
 */
@Table("fee_supplier_audit")
@Data
public class SupplierAudit {

	@Id
	private int id;
	private int supplierId;
	private double price;
	private int type; //0人工注资  1人工扣款 ,扣款时需要负号
	private int addTime;
	private String addUser;
	private int auditTime;
	private int auditStatus;//0不通过   1通过
	private String auditUser;
	private String remark;

	
	
}
