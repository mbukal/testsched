package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;

import hr.unizg.fer.hmo.ts.optimization.ga.GeneticAlgorithm;
import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.diver.DiversityFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.inddist.InterIndividualDistance;
import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.optfinder.OptimumFinder;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.proxies.OptimumFinderMonitor;
import hr.unizg.fer.hmo.ts.optimization.ga.proxies.PopulationGeneratorMonitor;
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
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.diver.AverageDistance;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.CachingScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.inddist.KendallTauDistance;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.optfinder.ShortestMakespanFinder;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen.IndependentPopulationGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.AdaptiveBestSelection;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop.AdaptiveWorstEliminator;
import hr.unizg.fer.hmo.ts.util.FileUtils;

public class DiversityDemo {
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
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(problem.testCount);
		int popSize = 20;
		Comparator<PartialSolution> comparator = (ps1, ps2) -> evalFunc.evaluate(ps1)
				- evalFunc.evaluate(ps2);
		PopulationGenerator<PartialSolution> popGen = new IndependentPopulationGenerator(comparator,
				indGen, popSize);

		InterIndividualDistance<PartialSolution> dist = new KendallTauDistance();
		DiversityFunction<PartialSolution> diverFunc = new AverageDistance(dist);
		
		PopulationGenerator<PartialSolution> popGenMonitor = new PopulationGeneratorMonitor(popGen, diverFunc);
		
		/* updating */
		UpdatePopulationOperator<PartialSolution> updatePopOp = new AdaptiveWorstEliminator(4);
		

		/* optimum individual detection */
		OptimumFinder<PartialSolution> optFinder = new ShortestMakespanFinder();
		OptimumFinder<PartialSolution> optFinderMonitor = new OptimumFinderMonitor(optFinder, diverFunc);

		/* selection */
		SelectionOperator<PartialSolution> selectOp = new AdaptiveBestSelection(2);

		/* stop criterion */
		int maxIter = 500000;

		/* crossover */
		CrossoverOperator<PartialSolution> crossOp = Crossovers.randomParentDummy();

		/* mutation */
		MutationOperator<PartialSolution> mutOp = Mutations.singleSwap();

		/* final product -- genetic algorithm */
		GeneticAlgorithm<PartialSolution> scheduler = new EvolutionaryScheduler(popGenMonitor, selectOp,
				crossOp, mutOp, updatePopOp, optFinderMonitor, maxIter);

		PartialSolution parSolution = scheduler.optimize();
		Solution solution = decoder.decode(parSolution);
		System.out.println(solution.getDuration());
	}
	
}
