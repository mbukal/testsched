package hr.unizg.fer.hmo.ts.scheduler.dec;

import java.util.Arrays;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;

class FastPriorityAdheringSolutionDecoder implements SolutionDecoder {
	private final Problem problem;
	public final int[][] resourceToFreeTimes;

	public FastPriorityAdheringSolutionDecoder(Problem problem) {
		this.problem = problem;
		// initialize resourceToTestTimeSeqs
		int resourceCount = problem.resourceCount;
		int[] resourceReferenceCounts = new int[resourceCount];
		for (int[] resources : problem.testToResources)
			for (int r : resources)
				resourceReferenceCounts[r]++;
		resourceToFreeTimes = new int[resourceCount][];
		for (int r = 0; r < resourceCount; r++)
			resourceToFreeTimes[r] = new int[problem.resourceToMultiplicity[r]];
	}

	public Solution decode(PartialSolution psol) {
		// clear resourceToAvailableStartTimes
		for (int[] times : resourceToFreeTimes)
			Arrays.fill(times, 0);

		Solution sol = new Solution(problem);
		for (int i = 0; i < problem.testCount; i++) {
			int test = psol.priorityToTest[i];
			int mach = psol.testToMachine[test];
			TestTimeSeq tts = sol.machineToTestTimeSeq[mach];
			int startTime = constrainStartTimeToResources(tts.getDuration());
			assignResources(test, startTime);
			sol.machineToTestTimeSeq[mach].add(test, startTime);
		}
		return sol;
	}

	private int constrainStartTimeToResources(int minStartTime) {
		for (int r = 0; r < problem.resourceCount; r++) {
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < problem.resourceToMultiplicity[r]; i++) {
				int time = resourceToFreeTimes[r][i];
				if (time <= minStartTime) {
					min = minStartTime;
					break;
				} else if (time < min)
					min = time;
			}
			if (minStartTime < min)
				minStartTime = min;
		}
		return minStartTime;
	}

	private void assignResources(int test, int startTime) {
		int[] resourceIndices = new int[problem.resourceCount];
		for (int r = 0; r < problem.resourceCount; r++) { // minimize gaps in resource usage
			int argmax = -1, max = 0;
			for (int i = 0; i < problem.resourceToMultiplicity[r]; i++) {
				int time = resourceToFreeTimes[r][i];
				if (max < time && time <= startTime) {
					max = time;
					argmax = i;
				}
			}
			resourceIndices[r] = argmax;
		}
		for (int r = 0; r < problem.resourceCount; r++)
			resourceToFreeTimes[r][resourceIndices[r]] = startTime + problem.testToDuration[test];
	}
}
