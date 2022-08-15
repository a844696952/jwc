package com.yice.edu.cn.jy.source21.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	public static void debug(String text) {
		System.out.println("DEBUG["+Thread.currentThread().getName() + "][" + getTime() + "]" + ":" + text);
	}

	public static void info(String text) {
		System.out.println("INFO["+Thread.currentThread().getName() + "][" + getTime() + "]" + ":" + text);
	}

	public static void error(String text) {
		System.err.println("ERROR["+Thread.currentThread().getName() + "][" + getTime() + "]" + ":" + text);
	}

	public static void warn(String text) {
		System.out.println("WARN["+Thread.currentThread().getName() + "][" + getTime() + "]" + ":" + text);
	}
}
