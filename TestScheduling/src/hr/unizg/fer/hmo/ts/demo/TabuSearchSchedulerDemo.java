package hr.unizg.fer.hmo.ts.demo;

import java.io.FileInputStream;
import java.io.IOException;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.TabuSearch;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.neighborhood.Neighborhood;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.tabulist.TabuList;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.tabulist.impl.SimpleTabuList;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.tenure.TabuTenure;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.CachingScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.solver.tabu.TabuSearchScheduler;
import hr.unizg.fer.hmo.ts.scheduler.solver.tabu.neighborhood.InterchangeNeighborhood;
import hr.unizg.fer.hmo.ts.scheduler.solver.tabu.tenure.ConstantTenure;

public class TabuSearchSchedulerDemo {
	public static void main(String[] args) throws IOException {
		String path = "C:/Users/Marko/git/testsched/TestScheduling/data/problem-instances/ts0.txt";
		String problemDefinitionString;
		try (FileInputStream problemFile = new FileInputStream(path)) {
			problemDefinitionString = new String(problemFile.readAllBytes());
		}
		VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
		Problem problem = new Problem(verboseProblem);

		/* evaluation */
		SolutionDecoder decoder = new SecretSolutionDecoder(problem);
		EvaluationFunction<PartialSolution> evalFunc = new CachingScheduleEvaluator(decoder);

		/* initial solution */
		PartialSolutionGenerator psg = new PartialSolutionGenerator(problem);
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(psg);
		PartialSolution initSol = indGen.generate();
		
		/* stop criterion */
		int maxIter = 1000;
		
		/* neighborhood */
		Neighborhood<PartialSolution> neighborhood = new InterchangeNeighborhood();
		//System.out.println("initial solution: " + initSol);
		//System.out.println("neighbors: ");
		//neighborhood.neighbors(initSol).forEach(ps -> System.out.println(ps));

		
		/* tabu list */
		TabuList<PartialSolution> tabuList = new SimpleTabuList<>();
		
		/* tabu tenure */
		int constTenure = 20;
		TabuTenure<PartialSolution> tabuTenure = new ConstantTenure(constTenure);

		TabuSearch<PartialSolution> scheduler = new TabuSearchScheduler(maxIter, evalFunc, neighborhood, tabuList, tabuTenure);

		PartialSolution parSolution = scheduler.optimize(initSol);
		Solution solution = decoder.decode(parSolution);
		// VerboseSolution verboseSolution = new VerboseSolution(verboseProblem,
		// solution);
		// System.out.println(verboseSolution);
		System.out.println(solution.getDuration());
		System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());

	}
}
