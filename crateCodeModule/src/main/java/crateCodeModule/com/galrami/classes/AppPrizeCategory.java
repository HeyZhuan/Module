package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;



@Table("app_prize_category")
public class AppPrizeCategory {
	
	@Id
	@JspFieldName(fildName="id")
	private int id;
	 
	@JspFieldName(fildName="备注")
	private String  detail;

	@JspFieldName(fildName="分类名称")
	private String name;

}
