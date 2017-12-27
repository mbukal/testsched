package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.unizg.fer.hmo.ts.optimization.Optimizer;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.VerboseSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.dummy.DummyScheduler;
import hr.unizg.fer.hmo.ts.scheduler.util.FileUtils;

public class OperationTest {
	public static void main(String[] args) throws IOException {
		String problemInstanceDirPath = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/problem-instances");
		DirectoryStream<Path> dirStream = Files
				.newDirectoryStream(Paths.get(problemInstanceDirPath));
		for (Path problemFilePath : dirStream) {
			String problemDefinitionString;
			System.out.println(problemFilePath.toString());
			try (FileInputStream problemFile = new FileInputStream(problemFilePath.toFile())) {
				problemDefinitionString = new String(problemFile.readAllBytes());
			}
			VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
			// System.out.println(verboseProblem);
			Problem problem = new Problem(verboseProblem);
			Optimizer<Problem, Solution> scheduler = new DummyScheduler();
			Solution solution = scheduler.optimize(problem);
			VerboseSolution verboseSolution = new VerboseSolution(verboseProblem, solution);
			// System.out.println(verboseSolution);
			System.out.println(solution.getDuration());
			System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());
			/*
			 * for (int m = 0; m < problem.machineCount; m++) { System.out.print(m);
			 * System.out.println(solution.machineToTestTimeSeq[m]); }
			 */
		}
	}
}
