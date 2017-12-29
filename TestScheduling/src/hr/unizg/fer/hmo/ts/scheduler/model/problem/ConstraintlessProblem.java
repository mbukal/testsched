package hr.unizg.fer.hmo.ts.scheduler.model.problem;

import java.util.stream.IntStream;

public class ConstraintlessProblem extends Problem {
	public final int machineCount;
	public final int resourceCount;
	public final int testCount;
	public final int[] resourceToMultiplicity;
	public final int[] testToDuration;
	public final int[][] testToMachines;
	public final int[][] testToResources;

	public ConstraintlessProblem(VerboseProblem problem) {
		super(problem);
		machineCount = problem.machines.size();
		resourceToMultiplicity = problem.resources.stream().mapToInt(r -> r.multiplicity).toArray();
		resourceCount = resourceToMultiplicity.length;
		testCount = problem.tests.size();
		testToDuration = problem.tests.stream().mapToInt(t -> t.duration).toArray();
		testToMachines = new int[testCount][];
		testToResources = new int[testCount][];
		for (int t = 0; t < testCount; t++) {
			testToMachines[t] = IntStream.range(0,problem.machines.size()).toArray();
			testToResources[t] = new int[0];
		}
	}
}