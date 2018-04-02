package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;

/**
 * 代理商 注资审核
 * @author Zhuan
 *
 */
@Table("glm_proxy_info_audit")
public class ProxyInfoAudit {

	@Id
	@JspFieldName(fildName="id")
	private int id;
	@JspFieldName(fildName="代理id")
	private int proxyId;//代理id
	@JspFieldName(fildName="注资金额")
	private double price;//注资金额，正数为注资，负数为扣款
	@JspFieldName(fildName="注资类型",isSelect=1)
	private int type;//0 扣款   1 注资
	@JspFieldName(fildName="添加时间",needQuery=0)
	private int addTime;//添加时间
	@JspFieldName(fildName="审核时间",needQuery=0)
	private int auditTime;//审核时间
	@JspFieldName(fildName="提交人",needQuery=0)
	private String addUser;//提交人
	@JspFieldName(fildName="审核人",needQuery=0)
	private String auditUser;//审核人
	@JspFieldName(fildName="审核状态",isSelect=1)
	private int status;//审核状态   0未审核   1审核成功   2审核失败
	@JspFieldName(fildName="备注",needQuery=0)
	private String remark;//备注
	
	
	public int getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(int auditTime) {
		this.auditTime = auditTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProxyId() {
		return proxyId;
	}
	public void setProxyId(int proxyId) {
		this.proxyId = proxyId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAddTime() {
		return addTime;
	}
	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
