package crateCodeModule.com.fee.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("fee_supplier_audit")
public class CustomerAudit {
	
	@Id
	@JspFieldName(fildName="id")
    private int id;
	
	@JspFieldName(fildName="采购商id")
	private int customerId;
	
	@JspFieldName(fildName="采购商名称")
	private String supplierName;
	
	@JspFieldName(fildName="金额",needQuery=0)
	private double price;
	
	@JspFieldName(fildName="类型",isSelect=1)
	private int type;
	
	@JspFieldName(fildName="添加时间",needQuery=0)
    private int addTime;
	
	@JspFieldName(fildName="申请人",needQuery=0)
    private String addUser;
	
	@JspFieldName(fildName="审核时间",needQuery=0)
    private int auditTime;
	
	@JspFieldName(fildName="审核人",needQuery=0)
    private String auditUser;
    
	@JspFieldName(fildName="备注",needQuery=0)
    private String remark;

    
    
}
