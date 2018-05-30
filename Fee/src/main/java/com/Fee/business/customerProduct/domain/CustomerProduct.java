package com.Fee.business.customerProduct.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 采购商产品信息
 * @author Zhuan
 *
 */
@Table("fee_customer_product")
@Data
public class CustomerProduct {

	@Id
	private int id;
	private int customerId;
	private String name;
	private double price;//标准价
	private double sells;//售价
	private int categoryId;
	private int status;
	private int addTime;
	private String remark;
	
	
	
}
