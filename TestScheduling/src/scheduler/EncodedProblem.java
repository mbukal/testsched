package scheduler;

import java.util.stream.Stream;

class EncodedProblem {
	public final int machineCount;
	public final int[] resourceMultiplicities;
	public final int testCount;
	public final int[] testDurations;
	public final int[][] testMachines;
	public final int[][] testResources;

	public EncodedProblem(Problem problem) {
		this.machineCount = problem.machines.size();
		this.resourceMultiplicities = problem.resources.stream().mapToInt(r -> r.multiplicity)
				.toArray();
		this.testCount = problem.tests.size();
		this.testDurations = problem.tests.stream().mapToInt(t -> t.duration).toArray();
		this.testMachines = (int[][]) problem.tests.stream()
				.map(t -> Stream.of(t.machines).mapToInt(m -> problem.getMachineIndex(m)).toArray())
				.toArray();
		this.testResources = (int[][]) problem.tests.stream().map(
				t -> Stream.of(t.resources).mapToInt(r -> problem.getResourceIndex(r)).toArray())
				.toArray();
	}
}
