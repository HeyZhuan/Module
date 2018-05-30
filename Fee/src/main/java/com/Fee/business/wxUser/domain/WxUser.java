package com.Fee.business.wxUser.domain;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import lombok.Data;
/**
 * 微信用户信息
 * @author Zhuan
 *
 */
@Table("fee_wx_user")
@Data
public class WxUser {

	@Id
	private int id;
	private String openId;
	private String nickName;
	private int chategeTimes;
	private int sex;
	private int portRait;
	private int addTime;
	private String remark;

	
}
