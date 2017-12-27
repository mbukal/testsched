package hr.unizg.fer.hmo.ts.scheduler._old;

import java.util.stream.Stream;

import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;

public class EncodedSolutionOld {
	int[] testStartTimes;  // test to test start time map
	int[] testToMachine;  // test to machine map

	public EncodedSolutionOld(Problem problem) {
		testStartTimes = new int[problem.testCount];
		testToMachine = Stream.of(problem.testToMachines).mapToInt(m -> m[0]).toArray();
	}
}