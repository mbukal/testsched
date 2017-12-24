package hr.unizg.fer.hmo.ts.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class ArrayUtils {
	private static final Random rand = ThreadLocalRandom.current();

	public static void swap(int[] arr, int index1, int index2) {
		int test1 = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = test1;
	}

	// Fisher–Yates shuffle
	public static void shuffle(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--)
			swap(arr, i, rand.nextInt(i + 1));
	}

	// TODO: make symmetric to every element
	public static void shuffleWeakly(int[] arr, double swapProbability) {
		for (int i = arr.length - 1; i > 0; i--)
			if (rand.nextDouble() < swapProbability)
				swap(arr, i, rand.nextInt(i + 1));
	}

	public static void shufflePartially(int[] arr, int numberOfSwappedElements) {
		for (int i = 0; i <= numberOfSwappedElements; i++)
			swap(arr, rand.nextInt(arr.length), rand.nextInt(arr.length));
	}

	public static int randomElement(int[] arr) {
		return arr[ThreadLocalRandom.current().nextInt(arr.length)];
	}
}
