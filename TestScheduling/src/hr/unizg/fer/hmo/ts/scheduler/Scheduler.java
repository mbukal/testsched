package hr.unizg.fer.hmo.ts.scheduler;

import hr.unizg.fer.hmo.ts.scheduler.enc.EncodedSolution;
import hr.unizg.fer.hmo.ts.scheduler.problem.EncodedProblem;

public abstract class Scheduler {
	private final SolutionEvaluator evaluator;

	public Scheduler(EncodedProblem problem) {
		this.evaluator = new SolutionEvaluator(problem);
	}

	public abstract EncodedSolution solve(EncodedProblem problem);
}