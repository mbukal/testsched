package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

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
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionMutator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.EvolutionaryScheduler;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.crossover.DummyCrossover;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.crossover.PartiallyMappedCrossover;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.CachingScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.mutation.IntensePartialSolutionMutation;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.optfinder.ShortestMakespanDetector;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen.IndependentPopulationGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.RandomPartialSolutionSelection;
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
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(psg);
		int popSize = 70;
		PopulationGenerator<PartialSolution> popGen = new IndependentPopulationGenerator(indGen,
				popSize);

		/* updating */
		UpdatePopulationOperator<PartialSolution> updatePopOp = new DeterministicWorstEliminator(
				evalFunc);

		/* optimum individual detection */
		OptimumFinder<PartialSolution> optFinder = new ShortestMakespanDetector(evalFunc);

		/* selection */
		SelectionOperator<PartialSolution> selectOp = new RandomPartialSolutionSelection();

		/* stop criterion */
		int maxIter = 300000;

		/* crossover */
		CrossoverOperator<PartialSolution> crossOp = new PartiallyMappedCrossover();

		/* mutation */
		PartialSolutionMutator psm = new PartialSolutionMutator();
		int maxSwaps = 2;
		MutationOperator<PartialSolution> mutOp = new IntensePartialSolutionMutation(psm, maxSwaps);

		/* final product -- genetic algorithm */
		GeneticAlgorithm<PartialSolution> scheduler = new EvolutionaryScheduler(popGen, selectOp,
				crossOp, mutOp, updatePopOp, optFinder, maxIter);
		
		LogUtils.print("Starting optimization");
		PartialSolution parSolution = scheduler.optimize();
		LogUtils.print("Finished optimization");
		Solution solution = decoder.decode(parSolution);
		// VerboseSolution verboseSolution = new VerboseSolution(verboseProblem,
		// solution);
		// System.out.println(verboseSolution);
		System.out.println(solution.getDuration());
		System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());

	}
}