package hr.unizg.fer.hmo.ts.scheduler;

import java.util.stream.Stream;

public class Problem {
	public final int machineCount;
	public final int resourceCount;
	public final int testCount;
	public final int[] resourceToMultiplicity;
	public final int[] testToDuration;
	public final int[][] testToMachines;
	public final int[][] testToResources;

	public Problem(VerboseProblem problem) {
		machineCount = problem.machines.size();
		resourceToMultiplicity = problem.resources.stream().mapToInt(r -> r.multiplicity).toArray();
		resourceCount = resourceToMultiplicity.length;
		testCount = problem.tests.size();
		testToDuration = problem.tests.stream().mapToInt(t -> t.duration).toArray();
		testToMachines = new int[testCount][];
		testToResources = new int[testCount][];
		for (int t = 0; t < testCount; t++) {
			testToMachines[t] = Stream.of(problem.tests.get(t).machines)
					.mapToInt(m -> problem.getMachineIndex(m)).toArray();
			testToResources[t] = Stream.of(problem.tests.get(t).resources)
					.mapToInt(r -> problem.getResourceIndex(r)).toArray();
		}
	}
}
