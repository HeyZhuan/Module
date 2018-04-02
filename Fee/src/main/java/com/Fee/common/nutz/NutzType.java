package com.Fee.common.nutz;

public enum NutzType {

	/**
	 * =
	 */
	EQ("="),
	
	/**
	 * in
	 */
	IN("in"),
	/**
	 * <
	 */
	LT("<"),
	/**
	 * <=
	 */
	LE("<="),
	/**
	 * >
	 */
	GT(">"),
	/**
	 * >=
	 */
	GE(">="),
	/**
	 * like
	 */
	LIKE("like"),
	/**
	 * not like
	 */
	NOTLIKE("not like"),
	/**
	 * IS NULL
	 * 
	 */
	ISNULL("is null"),
	/**
	 * ！=
	 */
	NOEQUAL("!="),
	;
	
	public String opt;

	private NutzType(String opt) {
		this.opt = opt;
	}
}
