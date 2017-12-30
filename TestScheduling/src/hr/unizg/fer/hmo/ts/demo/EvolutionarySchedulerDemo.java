package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection.RouletteWheelSelection;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop.SimplifiedRouletteWheelEliminator;
import hr.unizg.fer.hmo.ts.util.FileUtils;

public class EvolutionarySchedulerDemo {
	public static void main(String[] args) throws IOException {
		String problemInstanceDirPath = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"data/problem-instances");
		DirectoryStream<Path> dirStream = Files
				.newDirectoryStream(Paths.get(problemInstanceDirPath));
		for (Path problemFilePath : dirStream) {
			String problemDefinitionString;
			System.out.println(problemFilePath);
			try (FileInputStream problemFile = new FileInputStream(problemFilePath.toFile())) {
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
			int popSize = 10;	
			PopulationGenerator<PartialSolution> popGen = new IndependentPopulationGenerator(indGen, popSize);
				
			/* updating */
			UpdatePopulationOperator<PartialSolution> updatePopOp = new SimplifiedRouletteWheelEliminator(evalFunc);
			
			/* optimum individual detection */
			OptimumFinder<PartialSolution> optFinder = new ShortestMakespanFinder(evalFunc);
			
			/* selection */
			SelectionOperator<PartialSolution> selectOp = new RouletteWheelSelection(evalFunc);
			
			/* stop criterion */
			int maxIter = 10000;	
			
			/* crossover */
			CrossoverOperator<PartialSolution> crossOp = new DummyCrossover();
			
			/* mutation */
			PartialSolutionMutator psm = new PartialSolutionMutator();
			int minSwaps = 1;
			int maxSwaps = 100;
			MutationOperator<PartialSolution> mutOp = new MultipleSwapMutation(psm, minSwaps, maxSwaps);
			
			/* final product -- genetic algorithm */
			GeneticAlgorithm<PartialSolution> scheduler = new EvolutionaryScheduler(popGen, selectOp, crossOp , mutOp , updatePopOp, optFinder, maxIter );
			
			PartialSolution parSolution = scheduler.optimize();
			Solution solution = decoder.decode(parSolution);
			// VerboseSolution verboseSolution = new VerboseSolution(verboseProblem, solution);
			// System.out.println(verboseSolution);
			System.out.println(solution.getDuration());
			System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());
		}
		
	}
}
