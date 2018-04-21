package crateCodeModule.com.fee.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;



/**
 * 采购商信息记录
 * 
 * @author lgy
 * 
 */

@Table("fee_customer")
public class Customer{

	@Id
	@JspFieldName(fildName="id")
	private int id;
	@JspFieldName(fildName="name")
	private String name;
	@JspFieldName(fildName="phone",needQuery=0)
	private String phone;
	@JspFieldName(fildName="contacts",needQuery=0)
	private String contacts;//采购商联系人
	@JspFieldName(fildName="balance",needQuery=0)
	private BigDecimal balance;
	@JspFieldName(fildName="addTime",needQuery=0)
	private int addTime;
	@JspFieldName(fildName="address",needQuery=0)
	private String address;
	@JspFieldName(fildName="apiKey",needQuery=0)
	private String apiKey;//开发者密钥
	@JspFieldName(fildName="status",isSelect=1)
	private int status;//状态(0冻结 1正常)
	@JspFieldName(fildName="allowIp",needQuery=0)
	private String allowIp;
	@JspFieldName(fildName="loginName",needQuery=0)
	private String loginName;
	@JspFieldName(fildName="email",needQuery=0)
	private String email;
	@JspFieldName(fildName="password",needQuery=0)
	private String password;
	@JspFieldName(fildName="remark",needQuery=0)
	private String remark;
	
	
}
