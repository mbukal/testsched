package hr.unizg.fer.hmo.ts.scheduler.dec;

import java.util.Arrays;
import java.util.stream.IntStream;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.scheduler.Solution;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;

public class SecretSolutionDecoder implements SolutionDecoder {
	private final Problem problem;
	public final int[][] resourceToFreeTimes;
	Solution solutionTemplate;

	public SecretSolutionDecoder(Problem problem) {
		this.problem = problem;
		resourceToFreeTimes = new int[problem.resourceCount][];
		for (int r = 0; r < problem.resourceCount; r++)
			resourceToFreeTimes[r] = new int[problem.resourceToMultiplicity[r]];
		solutionTemplate = new Solution(problem);
	}

	public Solution decode(PartialSolution psol) {
		// clear resourceToAvailableStartTimes
		for (int[] times : resourceToFreeTimes)
			Arrays.fill(times, 0);

		Solution sol = Solution.emptyLike(solutionTemplate);
		for (int i = 0; i < problem.testCount; i++) {
			int test = psol.priorityToTest[i];
			int mach = psol.testToMachine[test];
			int mStartTime = IntStream.of(problem.testToMachines[test])
					.map(m -> sol.machineToTestTimeSeq[m].getDuration()).min().getAsInt();
			int startTime = constrainStartTimeToResources(test, mStartTime);
			assignResources(test, startTime, sol);
		}
		return sol;
	}

	private int constrainStartTimeToResources(int test, int minStartTime) {
		for (int r : problem.testToResources[test]) {
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

	private void assignResources(int test, int startTime, Solution sol) {
		// minimize gaps in resource usage
		int[] resourceIndices = new int[problem.resourceCount];
		for (int r : problem.testToResources[test]) {
			int argmax = -1, max = -1;
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
		int argmax = -1, max = -1;
		for (int m : problem.testToMachines[test]) {
			int time = sol.machineToTestTimeSeq[m].getDuration();
			if (max < time && time <= startTime) {
				max = time;
				argmax = m;
			}
		}
		sol.machineToTestTimeSeq[argmax].add(test);
	}
}
