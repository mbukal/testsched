package hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class PartialSolutionGenerator {
	private final int[] priorityToTestTemplate;

	public PartialSolutionGenerator(Problem problem) {
		priorityToTestTemplate = ArrayUtils.range(problem.testCount);
	}

	public PartialSolution createRandomlyInitialized() {
		RandUtils.shuffle(priorityToTestTemplate);
		return new PartialSolution(priorityToTestTemplate.clone());
	}
}
