package hr.unizg.fer.hmo.ts.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
	private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public static void print(String text) {
		System.out.println("[" + timeFormat.format(new Date()) + "] " + text);
	}
}
