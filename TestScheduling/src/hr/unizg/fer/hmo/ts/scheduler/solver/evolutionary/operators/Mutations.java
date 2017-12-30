package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators;

import java.util.List;
import java.util.Random;

import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public final class Mutations {
	private static Random rand = RandUtils.rand;

	public static MutationOperator<PartialSolution> sequence(
			List<MutationOperator<PartialSolution>> mutations) {
		return (individual) -> {
			for (MutationOperator<PartialSolution> mut : mutations)
				individual = mut.mutate(individual);
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> singleSwap() {
		return (individual) -> {
			swapRandomly(individual);
			return individual;
		};
	}
	
	public static MutationOperator<PartialSolution> multiSwap(int swapCount) {
		return (individual) -> {
			swapRandomly(individual, swapCount);
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> multiSwap(int minSwaps,
			int maxSwaps) {
		return (individual) -> {
			swapRandomly(individual, minSwaps + rand.nextInt(maxSwaps));
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> forwardGeometricallyDistributedDistanceSwap(
			double elementSwapProbability) {
		return (individual) -> {
			int[] arr = individual.priorityToTest;
			int i = 0;
			while (i < arr.length) {
				for (; i < arr.length; i++)
					if (rand.nextDouble() < elementSwapProbability)
						break;
				int j = i + 1;
				for (; j < arr.length; j++)
					if (rand.nextDouble() < elementSwapProbability)
						ArrayUtils.swap(arr, i, j);
				i = j + 1;
			}
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> backwardGeometricallyDistributedDistanceSwap(
			double elementSwapProbability) {
		return (individual) -> {
			int[] arr = individual.priorityToTest;
			int j = arr.length - 1;
			while (j >= 0) {
				for (; j >= 0; j--)
					if (rand.nextDouble() < elementSwapProbability)
						break;
				int i = j - 1;
				for (; i >= 0; i--)
					if (rand.nextDouble() < elementSwapProbability)
						ArrayUtils.swap(arr, i, j);
				j = i - 1;
			}
			return individual;
		};
	}

	// private static helper methods

	private static void swapRandomly(PartialSolution ps) {
		int testCount = ps.priorityToTest.length;
		ArrayUtils.swap(ps.priorityToTest, rand.nextInt(testCount), rand.nextInt(testCount));
	}

	private static void swapRandomly(PartialSolution ps, int swapCount) {
		int testCount = ps.priorityToTest.length;
		for (int i = 0; i < swapCount; i++)
			ArrayUtils.swap(ps.priorityToTest, rand.nextInt(testCount), rand.nextInt(testCount));
	}
}
