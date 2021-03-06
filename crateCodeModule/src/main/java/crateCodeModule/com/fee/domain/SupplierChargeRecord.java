package crateCodeModule.com.fee.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("fee_supplier_charge_record")
public class SupplierChargeRecord {
	
	@Id
	@JspFieldName(fildName="id")
    private int id;
	
	@JspFieldName(fildName="供货商id")
	private int supplierId;
	
	@JspFieldName(fildName="供货商名称")
	private String supplierName;
	
	@JspFieldName(fildName="订单Id")
	private int orderId;
	
	@JspFieldName(fildName="类型",isSelect=1)
	private int type;
	
	@JspFieldName(fildName="添加时间",needQuery=0)
    private int addTime;
	
	@JspFieldName(fildName="发生金额",needQuery=0)
    private double price;
    
	@JspFieldName(fildName="备注",needQuery=0)
    private String remark;

    
    
}
