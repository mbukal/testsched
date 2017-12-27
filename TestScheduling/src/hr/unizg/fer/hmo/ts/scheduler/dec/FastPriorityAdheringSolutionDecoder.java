package hr.unizg.fer.hmo.ts.scheduler.dec;

import java.util.Arrays;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;

public class FastPriorityAdheringSolutionDecoder implements SolutionDecoder {
	private final Problem problem;
	public final int[][] resourceToFreeTimes;
	Solution solutionTemplate;

	public FastPriorityAdheringSolutionDecoder(Problem problem) {
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
			TestTimeSeq tts = sol.machineToTestTimeSeq[mach];
			int startTime = constrainStartTimeToResources(test, tts.getDuration());
			assignResources(test, startTime);
			tts.add(test, startTime);
		}
		return sol;
	}

	private int constrainStartTimeToResources(int test, int minStartTime) {
		for (int r: problem.testToResources[test]) {
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
		// minimize gaps in resource usage
		int[] resourceIndices = new int[problem.resourceCount];
		for (int r: problem.testToResources[test]) {
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
	}
}
