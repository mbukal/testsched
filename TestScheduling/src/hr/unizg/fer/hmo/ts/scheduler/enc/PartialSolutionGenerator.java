package hr.unizg.fer.hmo.ts.scheduler.enc;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class PartialSolutionGenerator {
	private final int testCount;
	private final int[][] testToMachines;

	private final int[] priorityToTestTemplate;

	public PartialSolutionGenerator(Problem problem) {
		testCount = problem.testCount;
		testToMachines = problem.testToMachines;
		priorityToTestTemplate = ArrayUtils.range(problem.testCount);
	}

	public PartialSolution createUninitialized() {
		return new PartialSolution(testCount);
	}

	public PartialSolution createRandomlyInitialized() {
		int[] testToMachine = new int[testCount];
		for (int t = 0; t < testCount; t++)
			testToMachine[t] = RandUtils.randomElement(testToMachines[t]);
		RandUtils.shuffle(priorityToTestTemplate);
		return new PartialSolution(priorityToTestTemplate.clone(), testToMachine);
	}
}
