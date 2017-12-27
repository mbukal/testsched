package hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import hr.unizg.fer.hmo.ts.util.ArrayUtils;

public class PartialSolutionMutator {
	private final Random rand = ThreadLocalRandom.current();

	public PartialSolutionMutator() {
	}

	public void swapRandomly(PartialSolution ps) {
		int testCount = ps.priorityToTest.length;
		ArrayUtils.swap(ps.priorityToTest, rand.nextInt(testCount), rand.nextInt(testCount));
	}

	public void swapRandomly(PartialSolution ps, int swapCount) {
		int testCount = ps.priorityToTest.length;
		for (int i = 0; i < swapCount; i++)
			ArrayUtils.swap(ps.priorityToTest, rand.nextInt(testCount), rand.nextInt(testCount));
	}

	public void swapClosePairsRandomlyForward(PartialSolution ps, double swapProb) {
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

	public void swapClosePairsRandomlyBackward(PartialSolution ps, double swapProb) {
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
}
