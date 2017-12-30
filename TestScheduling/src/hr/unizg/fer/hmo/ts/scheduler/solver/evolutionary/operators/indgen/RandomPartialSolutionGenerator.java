package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen;

import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class RandomPartialSolutionGenerator
				implements IndividualGenerator<PartialSolution> {
	private final int testCount;
	
	public RandomPartialSolutionGenerator(int testCount) {
		this.testCount = testCount;
	}

	@Override
	public PartialSolution generate() {
		int[] priorities = ArrayUtils.range(testCount);
		RandUtils.shuffle(priorities);
		return new PartialSolution(priorities);
	}

}
