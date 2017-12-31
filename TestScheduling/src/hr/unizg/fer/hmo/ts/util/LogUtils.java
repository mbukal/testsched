package hr.unizg.fer.hmo.ts.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
	private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static Date date = new Date();

	public static void print(Object best) {
		date.setTime(System.currentTimeMillis());
		System.out.println("[" + timeFormat.format(date) + "] " + best);
	}
}
