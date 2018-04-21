package crateCodeModule.com.fee.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("fee_wx_user")
public class WxUser {
	
	@Id
	@JspFieldName(fildName="id")
    private int id;

	@JspFieldName(fildName="openId")
	private int openId;
	
	@JspFieldName(fildName="昵称")
	private String nickName;
	
	@JspFieldName(fildName="充值次数",needQuery=0)
	private int chategeTimes;
	
	@JspFieldName(fildName="性别",needQuery=0)
	private int sex;
	
	@JspFieldName(fildName="头像url",needQuery=0)
	private int portRait;
	
	@JspFieldName(fildName="添加时间",needQuery=0)
    private int addTime;
    
	@JspFieldName(fildName="备注",needQuery=0)
    private String remark;

    
    
}
