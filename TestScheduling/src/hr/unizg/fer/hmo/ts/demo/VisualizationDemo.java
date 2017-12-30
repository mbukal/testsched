package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;
import hr.unizg.fer.hmo.ts.scheduler.solver.random.RandomSamplingScheduler;
import hr.unizg.fer.hmo.ts.util.FileUtils;
import hr.unizg.fer.hmo.ts.util.Visualization;

public class VisualizationDemo {
	public static void main(String[] args) throws IOException {
		String problemInstanceDirPath = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/problem-instances");
		String visualizationDirPath = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/visualization");
		DirectoryStream<Path> dirStream = Files
				.newDirectoryStream(Paths.get(problemInstanceDirPath));
		for (Path problemFilePath : dirStream) {
			System.out.println(problemFilePath);
			String problemDefinitionString = new String(Files.readAllBytes(problemFilePath));
			VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
			// System.out.println(verboseProblem);
			Problem problem = new Problem(verboseProblem);
			RandomSamplingScheduler scheduler = new RandomSamplingScheduler(1);
			Solution solution = scheduler.optimize(problem);
			// VerboseSolution verboseSolution = new VerboseSolution(verboseProblem,
			// solution);
			Visualization.pyDisplayHistogram(IntStream.range(0, 4000)
					.map(i -> scheduler.optimize(problem).getDuration()).toArray());
			String htmlVis = Visualization.convertSolutionToHTML(solution, problem);
			String visualizationFilePath = visualizationDirPath + "/viz1.html";
			System.out.println(visualizationFilePath);
			Files.write(Paths.get(visualizationFilePath), htmlVis.getBytes());

			SecretSolutionDecoder ssd = new SecretSolutionDecoder(problem);
			MutationOperator<PartialSolution> mut = Mutations.multiSwap(1);
			mut.mutate(scheduler._bestPsol);
			htmlVis = Visualization.convertSolutionToHTML(ssd.decode(scheduler._bestPsol), problem);
			visualizationFilePath = visualizationDirPath + "/viz2.html";
			System.out.println(visualizationFilePath);
			Files.write(Paths.get(visualizationFilePath), htmlVis.getBytes());

			// System.out.println(verboseSolution);
			System.out.println(solution.getDuration());
			System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());
		}
	}
}
