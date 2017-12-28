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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(priorityToTest);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartialSolution other = (PartialSolution) obj;
		if (!Arrays.equals(priorityToTest, other.priorityToTest))
			return false;
		return true;
	}
	

	@Override
	public PartialSolution clone() {
		return new PartialSolution(priorityToTest.clone());
	}
}
