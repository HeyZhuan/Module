package crateCodeModule.com.fee.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("fee_customer_product")
public class CustomerProduct{
	
	@Id
	@JspFieldName(fildName="id")
	private int id;
	
	@JspFieldName(fildName="customerId")
	private int customerId;
	
	@JspFieldName(fildName="customerName")
	private String customerName;
	
	@JspFieldName(fildName="name")
	private String name;
	
	@JspFieldName(fildName="price",needQuery=0)
	private double price;//标准价
	
	@JspFieldName(fildName="cost",needQuery=0)
	private double cost;//成本
	
	@JspFieldName(fildName="categoryId")
	private int categoryId;//产品大类Id
	
	@JspFieldName(fildName="categoryName")
	private String categoryName;//产品大类名称
	
	@JspFieldName(fildName="province")
	private String province;
	
	@JspFieldName(fildName="operator",isSelect=1)
	private String operator;
	
	@JspFieldName(fildName="area",isSelect=1)
	private int area;
	
	@JspFieldName(fildName="size",isSelect=1)
	private int size;
	
	@JspFieldName(fildName="unit",isSelect=1)
	private int unit;
	
	@JspFieldName(fildName="status",isSelect=1)
	private int status;//0 冻结   1正常
	
	@JspFieldName(fildName="addTime",needQuery=0)
	private int addTime;
	
	@JspFieldName(fildName="remark",needQuery=0)
	private String remark;
	
	@JspFieldName(fildName="apiCode",needQuery=0)
	private String apiCode;//产品对接代码

}
