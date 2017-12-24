package hr.unizg.fer.hmo.ts.scheduler.enc;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;

public class PartialSolutionGenerator {
	private final int testCount;
	private final int[][] testToMachines;

	private final int[] priorityToTestTemplate;

	public PartialSolutionGenerator(Problem problem) {
		testCount = problem.testCount;
		testToMachines = problem.testToMachines;
		priorityToTestTemplate = new int[problem.testCount];
		for (int i = 0; i < problem.testCount; i++)
			priorityToTestTemplate[i] = i;
	}

	public PartialSolution createUninitialized() {
		return new PartialSolution(testCount);
	}

	public PartialSolution createLazilyInitialized() {
		int[] testToMachine = new int[testCount];
		for (int t = 0; t < testCount; t++)
			testToMachine[t] = testToMachines[t][0];
		return new PartialSolution(priorityToTestTemplate.clone(), testToMachine);
	}

	public PartialSolution createRandomlyInitialized() {
		int[] testToMachine = new int[testCount];
		for (int t = 0; t < testCount; t++)
			testToMachine[t] = ArrayUtils.randomElement(testToMachines[t]);
		ArrayUtils.shuffle(priorityToTestTemplate);
		return new PartialSolution(priorityToTestTemplate.clone(), testToMachine);
	}
}
