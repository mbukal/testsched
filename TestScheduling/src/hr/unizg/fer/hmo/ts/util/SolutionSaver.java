package hr.unizg.fer.hmo.ts.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.unizg.fer.hmo.ts.scheduler.model.solution.VerboseSolution;

public final class SolutionSaver {
	public static final String problemInstanceDirPath = FileUtils.findInAncestor(
			new File(".").getAbsolutePath(), "data/results") + File.separator + "solutions";

	public static void save(VerboseSolution solution, String problemID, String algorithmID)
			throws IOException {
		Path path = Paths.get(problemInstanceDirPath + File.separator + problemID + File.separator
				+ algorithmID + File.separator + solution.duration + ".txt").toRealPath();
		Files.createDirectories(path);
		System.out.println(path);
		Files.write(path, solution.toString().getBytes());
	}
}
