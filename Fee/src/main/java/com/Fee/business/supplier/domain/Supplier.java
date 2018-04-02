package com.Fee.business.supplier.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 供应商信息
 * @author Zhuan
 *
 */
@Table("fee_supplier")
@Data
public class Supplier {

	@Id
	private int id;
	private String name;
	private int status;
	private double balance;
	private String remark;

	
}
