package hr.unizg.fer.hmo.ts.scheduler;

import java.util.stream.IntStream;

import hr.unizg.fer.hmo.ts.scheduler.enc.EncodedSolution;
import hr.unizg.fer.hmo.ts.scheduler.enc.TestTimeSeq;
import hr.unizg.fer.hmo.ts.scheduler.problem.EncodedProblem;

public class SolutionEvaluator {
	private final EncodedProblem problem;
	private int maxDuration;

	public SolutionEvaluator(EncodedProblem problem) {
		this.problem = problem;
		maxDuration = IntStream.of(problem.testToDuration).sum();
	}

	public int evaluateSolution(EncodedSolution solution) {
		return getTotalDuration(solution) + (isSolutionFeasible(solution) ? 0 : 2 * maxDuration);
	}

	public boolean isSolutionFeasible(EncodedSolution solution) {
		return enoughResourcesAvailable(solution);
	}

	/*
	 * private int computeTestStartTime(int machine, int testIndex) { TestTimeSeq
	 * tts = solution.machineToTestTimeSeq[machine]; int time =
	 * tts.delays[testIndex]; for (int i = 0; i < testIndex; i++) time +=
	 * tts.delays[i] + problem.testToDuration[i]; return time; }
	 */

	private static int getMachineWithEarliestTestStartTime(EncodedSolution solution, int[] indices) {
		TestTimeSeq[] machineToTestTimeSeq = solution.machineToTestTimeSeq;
		int earliestTime = Integer.MAX_VALUE;
		int earliestMachine = -1;

		for (int m = 0; m < indices.length; m++) {
			int j = indices[m];
			TestTimeSeq tts = machineToTestTimeSeq[m];
			if (indices[m] >= tts.size())
				continue;
			int startTime = tts.getStartTime(j);
			if (startTime < earliestTime) {
				earliestTime = startTime;
				earliestMachine = m;
			}
		}
		return earliestMachine;
	}

	private static int getMachineWithEarliestTestEndTime(EncodedSolution solution, int[] indices) {
		// all indices must be > 0
		TestTimeSeq[] machineToTestTimeSeq = solution.machineToTestTimeSeq;
		int earliestTime = Integer.MAX_VALUE;
		int earliestMachine = -1;

		for (int m = 0; m < indices.length; m++) {
			int j = indices[m];
			if (j == 0)
				return -1;
			TestTimeSeq tts = machineToTestTimeSeq[m];
			int endTime = tts.getEndTime(j - 1);
			if (endTime < earliestTime) {
				earliestTime = endTime;
				earliestMachine = m;
			}
		}
		return earliestMachine;
	}

	private boolean enoughResourcesAvailable(EncodedSolution solution) {
		int[] leftResources = problem.resourceToMultiplicity.clone();

		final boolean START = false, END = true;
		boolean[] eventTypes = new boolean[2 * problem.testCount];
		int[] eventTests = new int[2 * problem.testCount];
		int k = 0;
		int[] currentIndices = new int[problem.machineCount];
		TestTimeSeq[] machineToTestTimeSeq = solution.machineToTestTimeSeq;

		for (int i = 0; i < 2 * problem.testCount; i++) {
			int mE = getMachineWithEarliestTestEndTime(solution, currentIndices);
			int earliestEndTime = mE == -1 ? Integer.MAX_VALUE
					: machineToTestTimeSeq[mE].getEndTime(currentIndices[mE]);
			int mS = getMachineWithEarliestTestStartTime(solution, currentIndices);
			int earliestStartTime = mS == -1 ? Integer.MAX_VALUE
					: machineToTestTimeSeq[mS].getStartTime(currentIndices[mS]);

			boolean event = earliestStartTime < earliestEndTime ? START : END;
			eventTypes[k] = event;
			int m = event == START ? mS : mE;
			int test = machineToTestTimeSeq[m].tests[currentIndices[m]];
			eventTests[k] = test;
			if (event == START) {
				for (int r : problem.testToResources[test])
					if (--leftResources[r] < 0)
						return false;
			} else
				for (int r : problem.testToResources[test])
					leftResources[r]++;
		}
		return true;
	}

	private int getTotalDuration(EncodedSolution solution) {
		int duration = 0;
		int[] testToDuration = problem.testToDuration;
		for (TestTimeSeq tts : solution.machineToTestTimeSeq) {
			int d = testToDuration[tts.tests[tts.size()]];
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
