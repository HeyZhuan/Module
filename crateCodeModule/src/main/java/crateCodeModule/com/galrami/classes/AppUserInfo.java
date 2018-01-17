package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("app_userInfo")
public class AppUserInfo {
	
	@Id
	@JspFieldName(fildName="id")
	private int id;

	@JspFieldName(fildName="openId")
	private String openId;
	
	@JspFieldName(fildName="头像url",needQuery=0)
	private String avatarUrl;

	@JspFieldName(fildName="用户昵称")
	private String nickName;

	@JspFieldName(fildName="性别",needQuery=0)
	private String gender;
	
	@JspFieldName(fildName="是否已成为分享大使",isSelect=1)
	private int ambassador;
	
	@JspFieldName(fildName="总分数",needQuery=0)
	private int  score;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAmbassador() {
		return ambassador;
	}
	public void setAmbassador(int ambassador) {
		this.ambassador = ambassador;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
	
	
}
