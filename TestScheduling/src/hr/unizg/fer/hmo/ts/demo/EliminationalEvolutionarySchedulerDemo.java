package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import hr.unizg.fer.hmo.ts.optimization.ga.EliminationalTournamentGeneticAlgorithm;
import hr.unizg.fer.hmo.ts.optimization.ga.GeneticAlgorithm;
import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.proxies.FitnessUpdateMonitor;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.VerboseSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Crossovers;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.CachingScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomSearchPartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen.IndependentPopulationGenerator;
import hr.unizg.fer.hmo.ts.util.FileUtils;
import hr.unizg.fer.hmo.ts.util.LogUtils;
import hr.unizg.fer.hmo.ts.util.SolutionSaver;

public class EliminationalEvolutionarySchedulerDemo {
	public static Solution sample(Problem problem, int popSize, int maxIter) throws IOException {
		/* evaluation */
		SolutionDecoder decoder = new SecretSolutionDecoder(problem);
		EvaluationFunction<PartialSolution> evalFunc = new CachingScheduleEvaluator(decoder);

		FitnessUpdateMonitor<PartialSolution> evalFuncMonitored = new FitnessUpdateMonitor<PartialSolution>(
				evalFunc);
		evalFuncMonitored.onUpdate.addListener((best) -> LogUtils.print(best));

		/* generation */
		IndividualGenerator<PartialSolution> indGen = new RandomSearchPartialSolutionGenerator(
				problem, 1);

		Comparator<PartialSolution> comparator = (ps1, ps2) -> evalFuncMonitored.evaluate(ps1)
				- evalFuncMonitored.evaluate(ps2);
		PopulationGenerator<PartialSolution> popGen = new IndependentPopulationGenerator(comparator,
				indGen, popSize);

		/* crossover */
		CrossoverOperator<PartialSolution> crossOp = Crossovers.partiallyMapped();

		/* mutation */
		MutationOperator<PartialSolution> mutOp = Mutations.multiSwap(1, 1);

		/* final product -- genetic algorithm */
		GeneticAlgorithm<PartialSolution> scheduler = new EliminationalTournamentGeneticAlgorithm<>(
				popGen, crossOp, mutOp, evalFuncMonitored, maxIter);

		LogUtils.print("Starting optimization.");
		PartialSolution parSolution = scheduler.optimize();
		LogUtils.print("Optimization finished.");
		Solution solution = decoder.decode(parSolution);

		System.out.println(solution.getDuration());
		System.out.println(Arrays.stream(problem.testToDuration).sum());

		return solution;
	}

	public static void main(String[] args) throws IOException {
		String problemName = "ts2";
		String path = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/problem-instances") + File.separator + problemName + ".txt";
		String problemDefinitionString;
		try (FileInputStream problemFile = new FileInputStream(path)) {
			problemDefinitionString = new String(problemFile.readAllBytes());
		}
		VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
		Problem problem = new Problem(verboseProblem);
		int popSize = 20, iter = 1000;
		for (int i = 0; i < 1; i++) {
			Solution solution = sample(problem, popSize, iter);
			VerboseSolution verboseSolution = new VerboseSolution(verboseProblem, solution);
			String algoID = "elim-pop" + popSize + "-iter" + iter;
			SolutionSaver.save(verboseSolution, problemName, algoID);
			// System.out.println(verboseSolution);

		}
	}
}