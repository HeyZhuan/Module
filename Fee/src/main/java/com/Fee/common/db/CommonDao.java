package com.Fee.common.db;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.util.Pojos;

public class CommonDao extends NutDao {
	/**
	 * 如"WHERE 1=1 AND status='5' AND receiveTime >= '1476633600' ORDER BY id DESC"
	 * @param cls
	 * @param cnd
	 * @return
	 */
	public String getWhereSql(Class cls, Cnd cnd) {
		Entity<?> entity = getEntity(cls);
		return Pojos.formatCondition(entity, cnd);
	}

}
