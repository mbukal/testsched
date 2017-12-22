package hr.unizg.fer.hmo.ts.scheduler;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EncodedSolutionOld2 {
	int[] tests;  // tests ordered by start time
	int[] testStartTimes;  // ordered test start times
	int[] testToMachine;  // <test to machines it can be run on> map

	public EncodedSolutionOld2(EncodedProblem problem) {
		tests = IntStream.range(0, problem.testCount).toArray();
		testStartTimes = new int[problem.testCount];
		testToMachine = Stream.of(problem.testToMachines).mapToInt(m -> m[0]).toArray();
	}
}
