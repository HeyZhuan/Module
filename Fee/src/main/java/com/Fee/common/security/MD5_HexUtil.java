package com.Fee.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * MD5加密
 * 
 * @author AX
 * 
 */
public class MD5_HexUtil {
	
	public static final Logger logger = Logger.getLogger(MD5_HexUtil.class);
	private static java.security.MessageDigest digest = null;

	public synchronized static final String md5Hex(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				nsae.printStackTrace();
			}
		}
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}

	private static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}
}
