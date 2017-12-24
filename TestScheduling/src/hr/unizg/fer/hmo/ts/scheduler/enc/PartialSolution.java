package hr.unizg.fer.hmo.ts.scheduler.enc;

import java.util.Arrays;

import hr.unizg.fer.hmo.ts.util.ArrayUtils;

public class PartialSolution {
	public final int[] priorityToTest;
	public final int[] testToMachine;

	public PartialSolution(int testCount) {
		priorityToTest = new int[testCount];
		testToMachine = new int[testCount];
	}

	public PartialSolution(int[] priorityToTest, int[] testToMachine) {
		this.priorityToTest = priorityToTest;
		this.testToMachine = testToMachine;
	}

	public void swap(int priority1, int priority2) {
		ArrayUtils.swap(priorityToTest, priority1, priority2);
	}

	public void changeMachine(int test, int machine) {
		testToMachine[test] = machine;
	}
	
	@Override
	public boolean equals(Object other) {
		PartialSolution oth = (PartialSolution) other;
		return Arrays.equals(oth.priorityToTest, priorityToTest)
				&& Arrays.equals(oth.testToMachine, testToMachine);
	}

	@Override
	public PartialSolution clone() {
		return new PartialSolution(priorityToTest.clone(), testToMachine.clone());
	}
}
