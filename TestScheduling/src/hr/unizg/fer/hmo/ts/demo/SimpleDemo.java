package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.scheduler.Scheduler;
import hr.unizg.fer.hmo.ts.scheduler.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.VerboseSolution;
import hr.unizg.fer.hmo.ts.scheduler.dec.Solution;
import hr.unizg.fer.hmo.ts.util.FileUtils;

public class SimpleDemo {
	public static void main(String[] args) throws IOException {
		String problemInstanceDirPath = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/problem-instances");

		DirectoryStream<Path> dirStream = Files
				.newDirectoryStream(Paths.get(problemInstanceDirPath));
		{
			for (Path problemFilePath : dirStream) {
				String problemDefinitionString;
				System.out.println(problemFilePath.toString());
				try (FileInputStream problemFile = new FileInputStream(problemFilePath.toFile())) {
					problemDefinitionString = new String(problemFile.readAllBytes());
				}
				VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
				// System.out.println(verboseProblem);
				Problem problem = new Problem(verboseProblem);
				Scheduler scheduler = new Scheduler();
				Solution solution = scheduler.solve(problem);
				VerboseSolution verboseSolution = new VerboseSolution(verboseProblem, solution);
				// System.out.println(verboseSolution);
				System.out.println(solution.getDuration());
				System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());
				/*for (int m = 0; m < problem.machineCount; m++) {
					System.out.print(m);
					System.out.println(solution.machineToTestTimeSeq[m]);
				}*/
			}
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		}
	}
}
