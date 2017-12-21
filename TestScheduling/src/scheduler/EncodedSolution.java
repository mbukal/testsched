package scheduler;

import java.util.stream.Stream;

public class EncodedSolution {
	int[] testStartTimes;
	int[] machines;

	public EncodedSolution(EncodedProblem problem) {
		testStartTimes = new int[problem.testCount];
		machines = Stream.of(problem.testMachines).mapToInt(m -> m[0]).toArray();
	}
}
