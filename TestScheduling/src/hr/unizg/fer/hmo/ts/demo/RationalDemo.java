package hr.unizg.fer.hmo.ts.demo;

import java.io.FileInputStream;
import java.io.IOException;

import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RationalPartialSolutionGenerator;

public class RationalDemo {
	public static void main(String[] args) throws IOException {
		String path = "C:/Users/Marko/git/testsched/TestScheduling/data/problem-instances/ts0.txt";
		String problemDefinitionString;
		try (FileInputStream problemFile = new FileInputStream(path)) {
			problemDefinitionString = new String(problemFile.readAllBytes());
		}
		VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
		Problem problem = new Problem(verboseProblem);
		
		MutationOperator<PartialSolution> mutOp = Mutations.singleSwapByDist(1, 1);
		IndividualGenerator<PartialSolution> indGen = new RationalPartialSolutionGenerator(problem, mutOp);
		
		System.out.println(indGen.generate());
		System.out.println(indGen.generate());
		System.out.println(indGen.generate());
		
	}

}
