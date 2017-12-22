package hr.unizg.fer.hmo.ts.scheduler;

import hr.unizg.fer.hmo.ts.scheduler.encdata.EncodedProblem;
import hr.unizg.fer.hmo.ts.scheduler.encdata.EncodedSolution;

public abstract class Scheduler {
	private final EncodedProblem problem;
	private final SolutionEvaluator evaluator;

	public Scheduler(EncodedProblem problem) {
		this.problem = problem;
		this.evaluator = new SolutionEvaluator(problem);
	}

	public abstract EncodedSolution solve(EncodedProblem problem);
}