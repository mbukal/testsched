package hr.unizg.fer.hmo.ts.scheduler;

import hr.unizg.fer.hmo.ts.scheduler.encdata.EncodedProblem;
import hr.unizg.fer.hmo.ts.scheduler.encdata.EncodedSolution;
import hr.unizg.fer.hmo.ts.scheduler.encdata.TestDelayList;

public class SolutionEvaluator {
	private final EncodedProblem problem;
	private EncodedSolution solution;

	public SolutionEvaluator(EncodedProblem problem) {
		this.problem = problem;
	}

	public void SetSolution(EncodedSolution solution) {
		this.solution = solution;
	}


	public int evaluateSolution() {
		return getDuration() * (isSolutionFeasible() ? 1 : 2);
	}

	public boolean isSolutionFeasible() {
		return enoughResourcesAvailable();
	}

	private boolean enoughResourcesAvailable() {
		// TODO
		int[] leftResources = problem.resourceMultiplicities.clone();
		return false;
	}

	private int getDuration() {
		int duration = 0;
		int[] testDurations = problem.testDurations;
		for (TestDelayList tdl : solution.machineToTestDelayList) {
			int d = 0;
			for (int i = 0; i < tdl.size(); i++)
				d += tdl.delays[i] + testDurations[tdl.tests[i]];
			if (d > duration)
				duration = d;
		}
		return duration;
	}

	/*
	 * private boolean anyOverlappingTestsUseSameMachine() { for (int i = 0; i <
	 * problem.testCount; i++) { int t1 = solution.tests[i]; for (int j = i + 1; j <
	 * problem.testCount; j++) { int t2 = solution.tests[j]; if
	 * (!orderedTestsOverlap(t1, t2)) break; if (testsUseSameMachine(t1, t2)) return
	 * false; } } return true; }
	 */

	/*
	 * private boolean testsOverlap(int t1, int t2) { int s1 =
	 * solution.testStartTimes[t1], s2 = solution.testStartTimes[t1]; return s1 <=
	 * s2 && s2 < s1 + this.problem.testDurations[t1] || s2 <= s1 && s1 < s2 +
	 * this.problem.testDurations[t2]; }
	 */

	/*
	 * private boolean orderedTestsOverlap(int t1, int t2) { int s1 =
	 * solution.testStartTimes[t1], s2 = solution.testStartTimes[t2]; return s2 < s1
	 * + this.problem.testDurations[t1]; }
	 */

	/*
	 * private boolean testsUseSameMachine(int t1, int t2) { return
	 * solution.testToMachine[t1] == solution.testToMachine[t2]; }
	 */
}
