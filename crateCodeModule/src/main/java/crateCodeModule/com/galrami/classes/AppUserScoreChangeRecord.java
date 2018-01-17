package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("app_user_score_change_record")
public class AppUserScoreChangeRecord {

	@Id
	@JspFieldName(fildName="id")
	private int id;
	
	@JspFieldName(fildName="浏览的openID",needQuery=0)
	private String targetOpenId;
	
	@JspFieldName(fildName="分享的openID",needQuery=0)
	private String originalOpenId;
	
	@JspFieldName(fildName="昵称")
	private String nickName;
	
	@JspFieldName(fildName="头像地址",needQuery=0)
	private String avatarUrl;//头像地址
	
	@JspFieldName(fildName="添加时间",needQuery=0)
	private int addTime;//添加时间
	
	@JspFieldName(fildName="分数",needQuery=0)
	private int score;
	
	@JspFieldName(fildName="分享的文章ID",needQuery=0)
	private int articleId;
	@Readonly
	private double sumNum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTargetOpenId() {
		return targetOpenId;
	}
	public void setTargetOpenId(String targetOpenId) {
		this.targetOpenId = targetOpenId;
	}
	public String getOriginalOpenId() {
		return originalOpenId;
	}
	public void setOriginalOpenId(String originalOpenId) {
		this.originalOpenId = originalOpenId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public int getAddTime() {
		return addTime;
	}
	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public double getSumNum() {
		return sumNum;
	}
	public void setSumNum(double sumNum) {
		this.sumNum = sumNum;
	}
	
	
	
}
