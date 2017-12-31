package hr.unizg.fer.hmo.ts.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.inddist.InterIndividualDistance;
import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.CachingScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.inddist.KendallTauDistance;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;

public class DistanceDemo {
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
		
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(problem.testCount);
		
		int size = 500;
		List<PartialSolution> population = new ArrayList<>();
		while (population.size() < size) {
			population.add(indGen.generate());
		}
		
		InterIndividualDistance<PartialSolution> dist = new KendallTauDistance();
		
		double[] evals = new double[(size * (size - 1)) / 2];
		double[] dists = new double[(size * (size - 1)) / 2];
		
		int k = 0;
		for (int i = 0; i < size - 1; i++) {
			PartialSolution ps1 = population.get(i);
			for (int j = i + 1; j < size; j++) {
				PartialSolution ps2 = population.get(j);
				evals[k] = Math.abs(evalFunc.evaluate(ps1) - evalFunc.evaluate(ps2));
				dists[k++] = dist.distance(ps1, ps2);
			}
		}

	    double corr = new PearsonsCorrelation().correlation(evals, dists);

	    System.out.println(corr);

		
	}
	
}
