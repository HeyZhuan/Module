package crateCodeModule.com.wareHouses.classes;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import crateCodeModule.com.galrami.util.JspFieldName;


@Table("wr_log_info")
public class LogInfo {
	
	@Id
	@JspFieldName(fildName="id")
    private int id;

	@JspFieldName(fildName="操作人名称")
	private String name;
	
	@JspFieldName(fildName="操作类型",needQuery=1,isSelect=1)
    private int workType;
	
	@JspFieldName(fildName="内容类型",needQuery=1,isSelect=1)
    private int contentType;


	@JspFieldName(fildName="添加时间",needQuery=0)
    private int addTime;
    
	@JspFieldName(fildName="备注",needQuery=0)
    private String remark;

    
    
}
