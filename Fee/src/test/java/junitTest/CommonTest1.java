package junitTest;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/config/spring/common_Context_junit.xml",
		/*"file:src/main/webapp/WEB-INF/config/spring/shiro_Context.xml",*/
	 })
public abstract class CommonTest1 {

	protected static final Logger logger = Logger.getLogger(CommonTest1.class);

	static {
		try {
			Log4jConfigurer.initLogging("file:src/main/webapp/WEB-INF/config/log4j/log4j_t.properties");
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot Initialize log4j");
		}
	}

}