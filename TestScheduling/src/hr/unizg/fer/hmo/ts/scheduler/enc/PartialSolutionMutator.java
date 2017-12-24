package hr.unizg.fer.hmo.ts.scheduler.enc;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class PartialSolutionMutator {
	private final int testCount;
	private final int[][] testToMachines;
	private final Random rand = ThreadLocalRandom.current();

	public PartialSolutionMutator(Problem problem) {
		testCount = problem.testCount;
		testToMachines = problem.testToMachines;
	}

	public void swapRandomly(PartialSolution ps) {
		ArrayUtils.swap(ps.priorityToTest, rand.nextInt(testCount), rand.nextInt(testCount));
	}

	public void swapRandomly(PartialSolution ps, int swapCount) {
		RandUtils.swapRandomly(ps.priorityToTest, swapCount);
	}

	public void changeMachineRandomly(PartialSolution ps, int test) {
		ps.testToMachine[test] = RandUtils.randomElement(testToMachines[test]);
	}

	public void changeMachineRandomly(PartialSolution ps) {
		int test = rand.nextInt(testCount);
		ps.testToMachine[test] = RandUtils.randomElement(testToMachines[test]);
	}
}
