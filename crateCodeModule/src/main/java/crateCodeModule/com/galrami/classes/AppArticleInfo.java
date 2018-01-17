package crateCodeModule.com.galrami.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;

@Table("app_article_info")
public class AppArticleInfo {
	
	@Id
	@JspFieldName(fildName="id")
    private Integer id;

	@JspFieldName(fildName="大类id")
    private Integer categoryId;

	@JspFieldName(fildName="标题")
    private String title;

	@JspFieldName(fildName="分享文章链接",needQuery=0)
    private String url;
    
	@JspFieldName(fildName="分值",needQuery=0)
    private int score;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
    
    
    
}
