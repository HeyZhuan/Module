package crateCodeModule.com.fee.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("fee_order")
public class Order {
	
	@Id
	@JspFieldName(fildName="id")
    private int id;

	@JspFieldName(fildName="产品id")
	private int productId;
	
	@JspFieldName(fildName="产品名称")
	private int productName;
	
	@JspFieldName(fildName="供货商id")
	private int supplierId;
	
	@JspFieldName(fildName="供货商名称")
	private int supplierName;
	
	@JspFieldName(fildName="规格",isSelect=1)
	private int size;
	
	@JspFieldName(fildName="状态",isSelect=1)
	private int status;
	
	@JspFieldName(fildName="添加时间",needQuery=0)
    private int addTime;
	
	@JspFieldName(fildName="售价",needQuery=0)
    private double price;
	
	@JspFieldName(fildName="成本",needQuery=0)
    private double cost;

	@JspFieldName(fildName="完成时间",needQuery=0)
    private int finishTime;
    
	@JspFieldName(fildName="备注",needQuery=0)
    private String remark;

    
    
}
