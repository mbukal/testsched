package hr.unizg.fer.hmo.ts.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.inddist.InterIndividualDistance;
import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Crossovers;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc.ScheduleEvaluator;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.inddist.KendallTauDistance;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.util.FileUtils;

public class DistanceDemo {
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
		EvaluationFunction<PartialSolution> evalFunc = new ScheduleEvaluator(decoder);
		
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(problem.testCount);
		
		PartialSolution ps1 = indGen.generate();
		PartialSolution ps2 = indGen.generate();
		
		InterIndividualDistance<PartialSolution> dist = new KendallTauDistance();
		
		
		System.out.println("dist: " + dist.distance(ps1, ps2));
		
		System.out.println("eval: " + evalFunc.evaluate(ps1));
		System.out.println("eval: " + evalFunc.evaluate(ps2));
		
		
		MutationOperator<PartialSolution> mutOp = Mutations.singleSwapByLocation(399, 400);
		
		
		/*
		CrossoverOperator<PartialSolution> crossOp = Crossovers.uniformLike();
		
		_ps1 = crossOp.reproduce(ParentPair.of(ps1, ps2));
		
		System.out.println(dist.distance(ps1, _ps1));
		System.out.println(dist.distance(ps2, _ps1));
		*/
	}
	
}
