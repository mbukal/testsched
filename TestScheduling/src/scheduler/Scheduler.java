package scheduler;

import java.util.function.*;

public abstract class Scheduler {
	final EncodedProblem problem;

	public Scheduler(EncodedProblem problem) {
		this.problem = problem;
	}

	public abstract EncodedSolution solve(EncodedProblem problem);

	protected boolean isFeasible(EncodedSolution solution) {
		BiFunction<Integer, Integer, Boolean> testsOverlap = (t1, t2) -> {
			int s1 = solution.testStartTimes[t1], s2 = solution.testStartTimes[t1];
			int e1 = s1 + problem.testDurations[t1], e2 = s2 + problem.testDurations[t2];
			return false;
		};

		for (int i = 0; i < problem.testCount; i++) {
			for (int j = i + 1; j < problem.testCount; j++) {

			}
		}
		return false;
	}

	protected int evaluateSolution(EncodedSolution solution) {
		return 0;
	}
}