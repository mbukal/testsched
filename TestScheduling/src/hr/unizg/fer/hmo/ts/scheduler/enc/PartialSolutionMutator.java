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

	public void swapClosePairsRandomlyForward(PartialSolution ps, int swapProb) {
		int[] arr = ps.priorityToTest;
		int i = 0;
		while (i < arr.length) {
			for (; i < arr.length; i++)
				if (rand.nextDouble() < swapProb)
					break;
			int j = i + 1;
			for (; j < arr.length; j++)
				if (rand.nextDouble() < swapProb)
					ArrayUtils.swap(arr, i, j);
			i = j + 1;
		}
	}

	public void swapClosePairsRandomlyBackward(PartialSolution ps, int swapProb) {
		int[] arr = ps.priorityToTest;
		int j = arr.length - 1;
		while (j >= 0) {
			for (; j >= 0; j--)
				if (rand.nextDouble() < swapProb)
					break;
			int i = j - 1;
			for (; i >= 0; i--)
				if (rand.nextDouble() < swapProb)
					ArrayUtils.swap(arr, i, j);
			j = i - 1;
		}
	}

	public void changeMachineRandomly(PartialSolution ps, int test) {
		ps.testToMachine[test] = RandUtils.randomElement(testToMachines[test]);
	}

	public void changeMachineRandomly(PartialSolution ps) {
		changeMachineRandomly(ps, rand.nextInt(testCount));
	}
}
