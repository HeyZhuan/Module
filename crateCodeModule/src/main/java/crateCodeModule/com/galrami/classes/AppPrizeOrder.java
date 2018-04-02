package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("app_prize_order")
public class AppPrizeOrder {
	
	@Id
	@JspFieldName(fildName="id")
	private int id;
	 
	@JspFieldName(fildName="openId")
	private String  openId;

	@JspFieldName(fildName="奖品id",isSelect=1)
	private int prizeId;
	
	@JspFieldName(fildName="订单状态",isSelect=1)
	private int state;
	
	@JspFieldName(fildName="订单时间")
	private int addTime;
	
	@JspFieldName(fildName="分类Id",isSelect=1)
	private int categoryId;

	@JspFieldName(fildName="手机号码")
	private String  phoneNumber;
	@JspFieldName(fildName="联系地址",needQuery=0)
	private String  address;
	@JspFieldName(fildName="用户名称",needQuery=0)
	private String  realName;
	

}
