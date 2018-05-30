package junitTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord;
import com.Fee.common.db.CommonDao;


public class JunitTestDemo extends CommonTest1 {
	private static final Logger log = LoggerFactory.getLogger(JunitTestDemo.class);
	
	@Autowired
	private CommonDao dao;
	
	@Test
	public void test() {
	
		dao.create(CustomerChargeRecord.class, true);
		
		
	}
}
