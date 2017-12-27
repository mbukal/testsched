package hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding;

import java.util.Arrays;

public class PartialSolution {
	public final int[] priorityToTest;

	public PartialSolution(int testCount) {
		priorityToTest = new int[testCount];
	}

	public PartialSolution(int[] priorityToTest) {
		this.priorityToTest = priorityToTest;
	}


	@Override
	public boolean equals(Object other) {
		PartialSolution oth = (PartialSolution) other;
		return Arrays.equals(oth.priorityToTest, priorityToTest);
	}

	@Override
	public PartialSolution clone() {
		return new PartialSolution(priorityToTest.clone());
	}
}
