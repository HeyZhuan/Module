package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;



@Table("app_prize_info")
public class AppPrizeInfo {
	@Id
	@JspFieldName(fildName="id")
	private int id;
	 
	@JspFieldName(fildName="分类id",isSelect=1)
	private int  categoryId;
	
	@JspFieldName(fildName="奖品名称")
	private String name;
	@JspFieldName(fildName="奖品价格",needQuery=0)
	private Double price;
	@JspFieldName(fildName="产品名称",needQuery=0)
	private String productName;
	@JspFieldName(fildName="图片URL",needQuery=0)
	private  String picUrl;
	@JspFieldName(fildName="分数",needQuery=0)
	private int score;
	@JspFieldName(fildName="兑换名次",needQuery=0)
	private int rank;
}
