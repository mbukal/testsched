package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators;

import java.util.Random;

import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public final class Mutations {
	private static Random rand = RandUtils.rand;
	
	public static MutationOperator<PartialSolution> identity() {
		return (individual) -> { return individual; };
	}

	@SafeVarargs
	public static MutationOperator<PartialSolution> sequence(
			MutationOperator<PartialSolution> ... mutations) {
		return (individual) -> {
			for (MutationOperator<PartialSolution> mut : mutations)
				individual = mut.mutate(individual);
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> singleSwap() {
		return (individual) -> {
			swapRandomly(individual.priorityToTest);
			return individual;
		};
	}
	
	public static MutationOperator<PartialSolution> singleSwapByDist(int minDist, int maxDist) {
		return (individual) -> {
			int dist = minDist + RandUtils.rand.nextInt(maxDist - minDist + 1);
			int maxFirst = (individual.priorityToTest.length - 1) - dist;
			int first = RandUtils.rand.nextInt(maxFirst + 1);
			int second = first + dist;
			ArrayUtils.swap(individual.priorityToTest, first, second);
			return individual;
		};
	}
	
	public static MutationOperator<PartialSolution> singleSwapByLocation(int minIndex, int maxIndex) {
		return (individual) -> {
			int first = minIndex + RandUtils.rand.nextInt(maxIndex - minIndex + 1);
			int second = minIndex + RandUtils.rand.nextInt(maxIndex - minIndex + 1);
			ArrayUtils.swap(individual.priorityToTest, first, second);
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> multiSwap(int swapCount) {
		return (individual) -> {
			swapRandomly(individual.priorityToTest, swapCount);
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> multiSwap(int minSwaps, int maxSwaps) {
		return (individual) -> {
			swapRandomly(individual.priorityToTest, minSwaps + rand.nextInt(maxSwaps));
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> insert() {
		return (individual) -> {
			insertRandomly(individual.priorityToTest);
			return individual;
		};
	}

	public static MutationOperator<PartialSolution> scramble() {
		return (individual) -> {
			int start = rand.nextInt(individual.priorityToTest.length);
			int end = rand.nextInt(individual.priorityToTest.length);
			if (end < start) {
				int temp = start;
				start = end;
				end = temp;
			}
			RandUtils.shufflePart(individual.priorityToTest, start, end + 1 - start);
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
	
	public static ConfigMultiSwap configurableMultiSwap() {
		return new ConfigMultiSwap();
	}

	// private static helper methods

	private static void swapRandomly(int[] arr) {
		ArrayUtils.swap(arr, rand.nextInt(arr.length), rand.nextInt(arr.length));
	}

	private static void swapRandomly(int[] arr, int swapCount) {
		for (int i = 0; i < swapCount; i++)
			ArrayUtils.swap(arr, rand.nextInt(arr.length), rand.nextInt(arr.length));
	}

	private static void insertRandomly(int[] arr) {
		int take = rand.nextInt(arr.length), put = rand.nextInt(arr.length);
		int taken = arr[take];
		if (take < put)
			System.arraycopy(arr, take + 1, arr, take, put - take);
		else // put < take
			System.arraycopy(arr, put, arr, put + 1, take - put);
		arr[put] = taken;
	}
	
	public static class ConfigMultiSwap implements MutationOperator<PartialSolution> {
		private int maxSwapCount;
		
		public ConfigMultiSwap() {
			this.maxSwapCount = 1;
		}

		public ConfigMultiSwap(int swapCount) {
			this.maxSwapCount = swapCount;
		}
		
		public void setMaxSwapCount(int swapCount) {
			this.maxSwapCount = swapCount;
		}

		@Override
		public PartialSolution mutate(PartialSolution individual) {
			swapRandomly(individual.priorityToTest, RandUtils.randBetweenInclusive(1, maxSwapCount));
			return individual;
		}

	}
}
