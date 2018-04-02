package com.Fee.business.logInfo.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 操作日志信息
 * @author Zhuan
 *
 */
@Table("wr_log_info")
@Data
public class LogInfo {

	@Id
	private int id;
	private String name;
	private int workType; //workTypeEnum
	private int contentType; //contentTypeEnum
	private int addTime;
	private String remark;
	private double price;//当 contentTypeEnum 为6、7、 8、9 即出入库，从资金池添加、提取资金时，该字段填写对应金额

	
}
