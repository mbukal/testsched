package hr.unizg.fer.hmo.ts.demo;

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
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionMutator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.EvolutionaryScheduler;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.crossover.DummyCrossover;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.CachingScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.mutation.MultipleSwapMutation;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.optfinder.ShortestMakespanFinder;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen.IndependentPopulationGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.RandomSelection;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop.DeterministicWorstEliminator;

public class _EvolutionarySchedulerDemo {
	public static void main(String[] args) throws IOException {
		String path = "C:/Users/Marko/git/testsched/TestScheduling/data/problem-instances/ts500m50r5-5.txt";
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
		int popSize = 30;
		Comparator<PartialSolution> comparator = (ps1, ps2) -> evalFunc.evaluate(ps1) - evalFunc.evaluate(ps2);
		PopulationGenerator<PartialSolution> popGen = new IndependentPopulationGenerator(comparator, indGen, popSize);

		/* updating */
		UpdatePopulationOperator<PartialSolution> updatePopOp = new DeterministicWorstEliminator();

		/* optimum individual detection */
		OptimumFinder<PartialSolution> optFinder = new ShortestMakespanFinder();

		/* selection */
		SelectionOperator<PartialSolution> selectOp = new RandomSelection();

		/* stop criterion */
		int maxIter = 100000;

		/* crossover */
		CrossoverOperator<PartialSolution> crossOp = new DummyCrossover();

		/* mutation */
		PartialSolutionMutator psm = new PartialSolutionMutator();
		int minSwaps = 1;
		int maxSwaps = 2;
		MutationOperator<PartialSolution> mutOp = new MultipleSwapMutation(psm, minSwaps, maxSwaps);

		/* final product -- genetic algorithm */
		GeneticAlgorithm<PartialSolution> scheduler = new EvolutionaryScheduler(popGen, selectOp, crossOp, mutOp,
				updatePopOp, optFinder, maxIter);

		PartialSolution parSolution = scheduler.optimize();
		Solution solution = decoder.decode(parSolution);
		// VerboseSolution verboseSolution = new VerboseSolution(verboseProblem,
		// solution);
		// System.out.println(verboseSolution);
		System.out.println(solution.getDuration());
		System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());

	}
}