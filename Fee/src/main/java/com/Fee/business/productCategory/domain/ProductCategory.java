package com.Fee.business.productInfo.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 产品信息
 * @author Zhuan
 *
 */
@Table("fee_product_info")
@Data
public class ProductInfo {

	@Id
	private int id;
	private String name;
	private String operator;//运营商
	private int size;
	private int unit;
	private int area;//使用区域  0本省  1全国
	private double price;//单价
	private int status;//0冻结  1开通
	private String remark;

	
}
