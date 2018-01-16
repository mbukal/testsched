package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import hr.unizg.fer.hmo.ts.optimization.ga.GeneticAlgorithm;
import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.optfinder.OptimumFinder;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.proxies.FitnessUpdateMonitor;
import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.VerboseSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.EvolutionaryScheduler;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Crossovers;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.CachingScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.optfinder.ShortestMakespanFinder;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen.IndependentPopulationGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.AdaptiveBestSelection;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.RandomSelection;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.TopTwoSelection;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop.AdaptiveWorstEliminator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop.DeterministicWorstEliminator;
import hr.unizg.fer.hmo.ts.util.FileUtils;
import hr.unizg.fer.hmo.ts.util.LogUtils;
import hr.unizg.fer.hmo.ts.util.SolutionSaver;

public class _EvolutionarySchedulerDemo {
	
	public static Solution sample(Problem problem, int popSize, int maxIter) throws IOException {
		/* evaluation */
		SolutionDecoder decoder = new SecretSolutionDecoder(problem);
		EvaluationFunction<PartialSolution> evalFunc = new CachingScheduleEvaluator(decoder);
		FitnessUpdateMonitor<PartialSolution> evalFuncMonitored = new FitnessUpdateMonitor<PartialSolution>(
				evalFunc);
		evalFuncMonitored.onUpdate.addListener((best) -> LogUtils.print(best));
		
		/* generation */
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(problem.testCount);
		Comparator<PartialSolution> comparator = (ps1, ps2) -> evalFuncMonitored.evaluate(ps1)
				- evalFuncMonitored.evaluate(ps2);
		PopulationGenerator<PartialSolution> popGen = new IndependentPopulationGenerator(comparator,
				indGen, popSize);

		/* updating */
		
		UpdatePopulationOperator<PartialSolution>updatePopOp = new AdaptiveWorstEliminator(2);

		/* optimum individual detection */
		OptimumFinder<PartialSolution> optFinder = new ShortestMakespanFinder();

		/* selection */
		SelectionOperator<PartialSolution> selectOp = new AdaptiveBestSelection(2);

		/* crossover */
		CrossoverOperator<PartialSolution> crossOp = Crossovers.uniformLike();

		/* mutation */
		//int minSwaps = 1, maxSwaps = 2;
		MutationOperator<PartialSolution> mutOp = //Mutations.multiSwap(minSwaps, maxSwaps);
													Mutations.singleSwap();
													//Mutations.oneOfTwo(Mutations.singleSwap(), Mutations.multiSwap(2, 5), 0.8);


		/* final product -- genetic algorithm */
		GeneticAlgorithm<PartialSolution> scheduler = new EvolutionaryScheduler(popGen, selectOp,
				crossOp, mutOp, updatePopOp, optFinder, maxIter);
		
		LogUtils.print("Starting optimization.");
		PartialSolution parSolution = scheduler.optimize();
		LogUtils.print("Optimization finished.");
		Solution solution = decoder.decode(parSolution);

		System.out.println(solution.getDuration());
		System.out.println(Arrays.stream(problem.testToDuration).sum());

		return solution;
	}

	public static void main(String[] args) throws IOException {
		String problemName = "ts10";
		String path = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/problem-instances") + File.separator + problemName + ".txt";
		String problemDefinitionString;
		try (FileInputStream problemFile = new FileInputStream(path)) {
			problemDefinitionString = new String(problemFile.readAllBytes());
		}
		VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
		Problem problem = new Problem(verboseProblem);
		int popSize = 20, iter = 500000, maxSwaps = 1;
		for (int i = 0; i < 1; i++) {
			Solution solution = sample(problem, popSize, iter);
			VerboseSolution verboseSolution = new VerboseSolution(verboseProblem, solution);
			String algoID = "steady-state" + popSize + "-iter" + iter + "-maxSwaps" + maxSwaps;
			SolutionSaver.save(verboseSolution, problemName, algoID);
			// System.out.println(verboseSolution);

		}
	}
}