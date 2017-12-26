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
		testToMachines = (int[][]) problem.tests.stream()
				.map(t -> Stream.of(t.machines).mapToInt(m -> problem.getMachineIndex(m)).toArray())
				.toArray();
		testToResources = (int[][]) problem.tests.stream().map(
				t -> Stream.of(t.resources).mapToInt(r -> problem.getResourceIndex(r)).toArray())
				.toArray();
	}
}
