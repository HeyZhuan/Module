package com.Fee.business.supplierProduct.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 供应商产品信息
 * @author Zhuan
 *
 */
@Table("fee_supplier_product")
@Data
public class SupplierProduct {

	@Id
	private int id;
	private int supplierId;
	private String name;
	private double price;//标准价
	private double cost;
	private int categoryId;
	private int status;
	private int addTime;
	private int priority;//产品优先级,越大越优先
	private String remark;
	private String apiCode;

	
}
