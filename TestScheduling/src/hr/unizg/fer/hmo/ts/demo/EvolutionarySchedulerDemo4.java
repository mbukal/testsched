package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.TopTwoSelection;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop.DeterministicWorstEliminator;
import hr.unizg.fer.hmo.ts.util.FileUtils;
import hr.unizg.fer.hmo.ts.util.LogUtils;
import hr.unizg.fer.hmo.ts.util.Visualization;

public class EvolutionarySchedulerDemo4 {
	public static void main(String[] args) throws IOException {
		String path = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/problem-instances") + "/ts0.txt";		
		String problemDefinitionString;
		try (FileInputStream problemFile = new FileInputStream(path)) {
			problemDefinitionString = new String(problemFile.readAllBytes());
		}
		VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
		Problem problem = new Problem(verboseProblem);

		/* evaluation */
		SolutionDecoder decoder = new SecretSolutionDecoder(problem);
		EvaluationFunction<PartialSolution> evalFunc = new CachingScheduleEvaluator(decoder);
		FitnessUpdateMonitor<PartialSolution> evalFuncMonitored = new FitnessUpdateMonitor<PartialSolution>(
				evalFunc);
		evalFuncMonitored.onUpdate.addListener((best) -> LogUtils.print(best));
		
		/* generation */
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(problem.testCount);
		int popSize = 30;
		Comparator<PartialSolution> comparator = (ps1, ps2) -> evalFuncMonitored.evaluate(ps1)
				- evalFuncMonitored.evaluate(ps2);
		PopulationGenerator<PartialSolution> popGen = new IndependentPopulationGenerator(comparator,
				indGen, popSize);

		/* updating */
		UpdatePopulationOperator<PartialSolution> updatePopOp = new DeterministicWorstEliminator();

		/* optimum individual detection */
		OptimumFinder<PartialSolution> optFinder = new ShortestMakespanFinder();

		/* selection */
		SelectionOperator<PartialSolution> selectOp = new TopTwoSelection();

		/* stop criterion */
		int maxIter = 100000;

		/* crossover */
		CrossoverOperator<PartialSolution> crossOp = Crossovers.randomParentDummy();

		/* mutation */
		//int minSwaps = 1, maxSwaps = 10;
		MutationOperator<PartialSolution> mutOp = Mutations.singleSwap();

		/* final product -- genetic algorithm */
		GeneticAlgorithm<PartialSolution> scheduler = new EvolutionaryScheduler(popGen, selectOp,
				crossOp, mutOp, updatePopOp, optFinder, maxIter);

		PartialSolution parSolution = scheduler.optimize();
		Solution solution = decoder.decode(parSolution);
		
		Visualization.convertSolutionToHTML(solution, problem);
		String visualizationDirPath = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/visualization");
		System.out.println(visualizationDirPath + "/vizizizi.html");
		
		System.out.println(solution.getDuration());
		System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());

	}
}