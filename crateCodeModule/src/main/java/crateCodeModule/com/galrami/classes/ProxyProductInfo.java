package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;

/**
 * 代理得产品信息
 * @author Zhuan
 *
 */
@Table("glm_proxy_info")
public class ProxyProductInfo {

	@Id
	@JspFieldName(fildName="id")
	private int id;
	
	@JspFieldName(fildName="代理id")
	private int proxyId;
	
	@JspFieldName(fildName="产品id")
	private int productId;
	
	@JspFieldName(fildName="产品名称")
	private String productName;
	
	@JspFieldName(fildName="产品售价")
	private double price;//该代理该产品得售价
	private int status;//1 开通  0冻结
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
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
