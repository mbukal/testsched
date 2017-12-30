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
import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.EvolutionaryScheduler;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Crossovers;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.CachingScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen._RandomPartialSolutionGenerator;
//github.com/mbukal/testsched.git
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.optfinder.ShortestMakespanFinder;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen.IndependentPopulationGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.RouletteWheelSelection;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop.DeterministicWorstEliminator;
import hr.unizg.fer.hmo.ts.util.FileUtils;
import hr.unizg.fer.hmo.ts.util.LogUtils;

public class EvolutionarySchedulerDemo2 {
	public static void main(String[] args) throws IOException {
		String path = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/problem-instances") + "/ts500m50r5-5.txt";
		String problemDefinitionString;
		try (FileInputStream problemFile = new FileInputStream(path)) {
			problemDefinitionString = new String(problemFile.readAllBytes());
		}
		VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
		Problem problem = new Problem(verboseProblem);

		/* evaluation */
		SolutionDecoder decoder = new SecretSolutionDecoder(problem);
		EvaluationFunction<PartialSolution> evalFunc = new CachingScheduleEvaluator(decoder);

		/* generation */
		PartialSolutionGenerator psg = new PartialSolutionGenerator(problem);
		IndividualGenerator<PartialSolution> indGen = new _RandomPartialSolutionGenerator(psg);
		int popSize = 70;
		Comparator<PartialSolution> comparator = (ps1, ps2) -> evalFunc.evaluate(ps1)
				- evalFunc.evaluate(ps2);
		PopulationGenerator<PartialSolution> popGen = new IndependentPopulationGenerator(comparator,
				indGen, popSize);

		/* updating */
		UpdatePopulationOperator<PartialSolution> updatePopOp = new DeterministicWorstEliminator();

		/* optimum individual detection */
		OptimumFinder<PartialSolution> optFinder = new ShortestMakespanFinder();

		/* selection */
		SelectionOperator<PartialSolution> selectOp = new RouletteWheelSelection(evalFunc);

		/* stop criterion */
		int maxIter = 50000;

		/* crossover */
		CrossoverOperator<PartialSolution> crossOp = Crossovers.partiallyMapped();

		/* mutation */
		int minSwaps = 1, maxSwaps = 2;
		MutationOperator<PartialSolution> mutOp = Mutations.multiSwap(minSwaps, maxSwaps);

		/* final product -- genetic algorithm */
		GeneticAlgorithm<PartialSolution> scheduler = new EvolutionaryScheduler(popGen, selectOp,
				crossOp, mutOp, updatePopOp, optFinder, maxIter);

		LogUtils.print("Starting optimization.");
		PartialSolution parSolution = scheduler.optimize();
		LogUtils.print("Optimization finished.");
		Solution solution = decoder.decode(parSolution);
		// VerboseSolution verboseSolution = new VerboseSolution(verboseProblem,
		// solution);
		// System.out.println(verboseSolution);
		System.out.println(solution.getDuration());
		System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());

	}
}