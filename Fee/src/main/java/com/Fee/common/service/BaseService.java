package com.Fee.common.service;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.OrderBy;
import org.nutz.dao.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;

@Service
public class BaseService {
	private static Logger log = LoggerFactory.getLogger(BaseService.class);
	@Autowired
	private CommonDao commonDao;

	/**
	 * 获取pager 对象
	 * 
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = true)
	public Pager creatPager(int pageNo, int pageSize) {
		return commonDao.createPager(pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public <T> QueryResult get(Class<T> clazz, Condition cnd, Pager pager) {
		List<T> list = commonDao.query(clazz, cnd, pager);
		pager.setRecordCount(commonDao.count(clazz, cnd));
		return new QueryResult(list, pager);
	}

	/**
	 * 导出的时候先判断是否大于数量大于某个值 maxLength默认为10000
	 * 
	 * @param clazz
	 * @param cnd
	 * @param pager
	 * @param maxLength
	 * @return
	 */
	@Transactional(readOnly = true)
	public <T> QueryResult exportList(Class<T> clazz, Cnd cnd, Pager pager, Integer maxLength) {
		if (pager == null) {
			pager = new Pager();
			pager.setPageNumber(1);
		}
		if (maxLength == null || maxLength == 0) {
			maxLength = 100000;
		}
		List<T> list = null;
		int count = commonDao.count(clazz, cnd);
		pager.setRecordCount(count);
		if (count >= maxLength) {
			list = new ArrayList<>();
		} else {
			list = commonDao.query(clazz, cnd, null);
		}
		return new QueryResult(list, pager);
	}

	@Transactional(readOnly = true)
	public <T> List<T> get(Class<T> clazz, Cnd cnd) {
		List<T> list = commonDao.query(clazz, cnd);
		return list;
	}

	@Transactional(readOnly = true)
	public <T> List<T> get(Class<T> clazz, OrderBy orderBy) {
		List<T> list = commonDao.query(clazz, orderBy);
		return list;
	}

	@Transactional(readOnly = true)
	public <T> T get(Class<T> clazz, Integer id) {
		return commonDao.fetch(clazz, id);
	}

	@Transactional(readOnly = true)
	public <T> List<T> getAll(Class<T> clazz) {
		return commonDao.query(clazz, null);
	}

	/**
	 * 根据传入的sql 与 参数数组进行查询,若要传参数需要写成 ?，不传则传null
	 * 
	 * @param sql
	 * @param params
	 *            参数需按顺序传进来
	 * @return
	 */
	public <T> List<T> query(Class<T> clazz, String sql, String[] params) {
		Sql sqll = Sqls.create(sql);
		
		sqll.setCallback(Sqls.callback.entities());
		sqll.setEntity(commonDao.getEntity(clazz));
		commonDao.execute(sqll);
		return sqll.getList(clazz);
	}

	@Transactional(readOnly = false)
	public <T> T save(T t) {
		return commonDao.insert(t);
	}
	
	@Transactional(readOnly = false)
	public <T> List<T> fastSave(List<T> t) {
		return commonDao.fastInsert(t);
	}

	public <T> void save(String sql, String[] params) {
		if (params != null) {
			for (String param : params) {
				sql=sql.replaceFirst("?", param);
				
			}
		}
		Sql sqll = Sqls.create(sql);
		commonDao.execute(sqll);
	}

	@Transactional(readOnly = false)
	public <T> void update(final T t) {
		commonDao.update(t);
	}
	
	@Transactional(readOnly = false)
	public <T> int update(Class<T> clazz,Chain chain,Cnd cnd) {
		return commonDao.update(clazz,chain,cnd);
	}

	/**
	 * 单个条件批量更新数据
	 */
	@Transactional(readOnly = false)
	public <T> int update(Class<T> clazz, String[] ids, String name, String value) {
		return commonDao.update(clazz, Chain.make(name, value), Cnd.where("id", NutzType.IN.opt, ids));

	}
	
	/**
	 * 单个条件批量更新数据
	 */
	@Transactional(readOnly = false)
	public <T> int update(Class<T> clazz, List<Integer> ids, String name, String value) {
		return commonDao.update(clazz, Chain.make(name, value), Cnd.where("id", NutzType.IN.opt, ids));

	}

	@Transactional(readOnly = false)
	public <T> void delete(final T t) {
		commonDao.delete(t);
	}

	@Transactional(readOnly = false)
	public <T> void delete(Class<T> clazz, long id) {
		commonDao.delete(clazz, id);
	}

	@Transactional(readOnly = false)
	public <T> void delete(Class<T> clazz, final Integer id) {
		commonDao.delete(clazz, id);
	}

	@Transactional(readOnly = false)
	public <T> void delete(Class<T> clazz, Cnd cnd) {
		commonDao.clear(clazz, cnd);
	}

	/**
	 * 执行传入的sql ，不支持查询
	 * 
	 * @param sql
	 * @param params
	 *            参数需按顺序传进来
	 * @return
	 */
	public void doSql(String sql) {
		Sql sqll = Sqls.create(sql);
		commonDao.execute(sqll);
	}
	
	/**
	 * 执行传入的sql ，查询
	 * 
	 * @param sql
	 * @param params
	 *            参数需按顺序传进来
	 * @return
	 */
	public <T> List<T> querySql(String sql,Class<T> clazz) {
		Sql sqll = Sqls.create(sql);
		sqll.setCallback(Sqls.callback.entities());
		sqll.setEntity(commonDao.getEntity(clazz));
		Sql execute = commonDao.execute(sqll);
		return execute.getList(clazz);
	}
	
	@Transactional(readOnly = true)
	public <T>T fetch(Class<T> clazz, Cnd cnd) {
		return commonDao.fetch(clazz, cnd);
	}

}
