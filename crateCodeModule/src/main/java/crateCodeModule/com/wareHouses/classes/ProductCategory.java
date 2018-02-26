package crateCodeModule.com.wareHouses.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("wr_product_category")
public class ProductCategory {
	
	@Id
	@JspFieldName(fildName="id")
    private int id;

	@JspFieldName(fildName="产品名称")
	private String name;
	
	@JspFieldName(fildName="类型",needQuery=1,isSelect=1)
    private int type;


	@JspFieldName(fildName="添加时间",needQuery=0)
    private int addTime;
    
	@JspFieldName(fildName="备注",needQuery=0)
    private String remark;

    
    
}
