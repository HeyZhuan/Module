package crateCodeModule.com.galrami.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JspFieldName {

	/**
	 * 字段对应的名称
	 * @return
	 */
	public String fildName();
	
	/**
	 * 是否需要在页面的查询中展示
	 * @return
	 */
	public int needQuery() default 1;
	
	/**
	 * 是否是select
	 * @return
	 */
	public int isSelect() default 0;
	
}
