package hr.unizg.fer.hmo.ts.scheduler;

import java.io.FileInputStream;
import java.io.IOException;

import hr.unizg.fer.hmo.ts.scheduler.VerboseSolution;
import hr.unizg.fer.hmo.ts.scheduler.dec.FastPriorityAdheringSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.dec.Solution;
import hr.unizg.fer.hmo.ts.scheduler.dec.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolutionMutator;

public class Scheduler {
	public Solution solve(Problem problem) {
		PartialSolutionGenerator generator = new PartialSolutionGenerator(problem);
		SolutionDecoder decoder = new FastPriorityAdheringSolutionDecoder(problem);
		// start optimization
		PartialSolutionMutator mutator = new PartialSolutionMutator(problem);
		PartialSolution psol = generator.createRandomlyInitialized();
		mutator.swapRandomly(psol, 8);
		for (int i = 0; i < 8; i++)
			mutator.changeMachineRandomly(psol);
		// end optimization
		Solution sol = decoder.decode(psol);
		return sol;
	}

	protected int evaluate(Solution solution) {
		return solution.getDuration();
	}

	public static void main(String[] args) throws IOException {
		String problemDefinitionString;
		try (FileInputStream problemFile = new FileInputStream(args[1])) {
			problemDefinitionString = new String(problemFile.readAllBytes());
		}
		VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
		System.out.println(verboseProblem);
		Problem problem = new Problem(verboseProblem);
		Scheduler scheduler = new Scheduler();
		Solution solution = scheduler.solve(problem);
		VerboseSolution verboseSolution = new VerboseSolution(verboseProblem, solution);
		System.out.println(verboseSolution);
	}
}