package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;

/**
 * 代理商 注资审核
 * @author Zhuan
 *
 */
@Table("glm_proxy_info_balance_record")
public class ProxyInfoBalanceReord {

	@Id
	@JspFieldName(fildName="id")
	private int id;
	@JspFieldName(fildName="代理id")
	private int proxyId;//代理id
	@JspFieldName(fildName="金额",needQuery=0)
	private double price;//消费金额
	@JspFieldName(fildName="类型",isSelect=1)
	private int type;//0 扣款   1 注资  2订单消耗  3订单退款
	@JspFieldName(fildName="添加时间",needQuery=0)
	private int addTime;//添加时间
	@JspFieldName(fildName="订单Id")
	private int orderId;//订单Id
	@JspFieldName(fildName="备注",needQuery=0)
	private String remark;//备注
	
	
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
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	
	
}
