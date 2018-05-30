package com.Fee.business.productCategory.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 产品信息
 * @author Zhuan
 *
 */
@Table("fee_product_category")
@Data
public class ProductCategory{

	@Id
	private int id;
	private String name;
	private String province;
	private String operator;//运营商
	private int size;
	private int unit;//0M 1G 2元
	private int area;//使用区域  0本省  1全国
	private double price;//标准价
	private int type;//0 流量   1话费   2慢充
	private String remark;

	public static String getUnitName(int unit){
		return unit==0?"M":unit==1?"G":"元";
	}
}
