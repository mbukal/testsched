package hr.unizg.fer.hmo.ts.scheduler.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public final class FileUtils {
	public static String findInAncestor(String path, String ancestorSiblingName) {
		List<String> components = Arrays.asList(path.split("\\|/"));
		while (components.size() > 0) {
			path = String.join(File.separator, components) + File.separator + ancestorSiblingName;
			if (new File(path).exists())
				return path;
			components.remove(components.size() - 1);
		}
		return null;
	}
}
