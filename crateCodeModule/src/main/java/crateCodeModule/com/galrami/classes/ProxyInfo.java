package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;

/**
 * 代理信息
 * @author Zhuan
 *
 */
@Table("glm_proxy_info")
public class ProxyInfo {

	@Id
	@JspFieldName(fildName="id")
	private int id;
	
	@JspFieldName(fildName="代理名称",isSelect=1)
	private String name;
	
	@JspFieldName(fildName="代理联系人",needQuery=0)
	private String linkMan;
	
	@JspFieldName(fildName="代理号码",needQuery=0)
	private String phone;
	
	@JspFieldName(fildName="代理地址",needQuery=0)
	private String address;
	
	@JspFieldName(fildName="代理状态",isSelect=1)
	private int status;//1 开通  0冻结
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
