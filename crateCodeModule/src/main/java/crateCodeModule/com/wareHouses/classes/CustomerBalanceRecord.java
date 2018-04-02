package crateCodeModule.com.wareHouses.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("money_customer_balance_record")
public class CustomerBalanceRecord {
	
	@Id
	@JspFieldName(fildName="id")
    private int id;

	@JspFieldName(fildName="采购商",isSelect=1)
	private int customerId;
	
	@JspFieldName(fildName="出货记录Id")
	private int outStockId;
	
	@JspFieldName(fildName="类型",isSelect=1)
	private int type;
	
	@JspFieldName(fildName="余额",needQuery=0)
    private double balance;
	
	@JspFieldName(fildName="发生金额",needQuery=0)
    private double price;

	@JspFieldName(fildName="添加时间",needQuery=0)
    private int addTime;
    
	@JspFieldName(fildName="备注",needQuery=0)
    private String remark;

    
    
}
