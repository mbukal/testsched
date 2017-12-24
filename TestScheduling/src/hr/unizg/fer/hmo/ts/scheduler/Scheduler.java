package hr.unizg.fer.hmo.ts.scheduler;

import hr.unizg.fer.hmo.ts.scheduler.enc.Solution;

public abstract class Scheduler {
	private final SolutionEvaluator evaluator;

	public Scheduler(Problem problem) {
		this.evaluator = new SolutionEvaluator(problem);
	}

	public abstract Solution solve(Problem problem);
}