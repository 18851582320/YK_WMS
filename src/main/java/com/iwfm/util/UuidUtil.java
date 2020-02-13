package com.iwfm.util;

import java.util.UUID;

/**
 * ClassName: UuidUtil 
 * @Description: TODO
 * @author yk
 * @date 2017年8月2日
 */
public class UuidUtil {
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
}
