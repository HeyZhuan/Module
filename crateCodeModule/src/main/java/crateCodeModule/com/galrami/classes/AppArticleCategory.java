package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("app_article_category")
public class AppArticleCategory {
	
	@Id
	@JspFieldName(fildName="id")
    private Integer id;

	@JspFieldName(fildName="分享可加分数",needQuery=0)
    private Integer score;

	@JspFieldName(fildName="大类名称")
    private String name;

	@JspFieldName(fildName="封面URL",needQuery=0)
    private String pictureUrl;
    
	@JspFieldName(fildName="描述",needQuery=0)
    private String detail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
    
    
    
}
