package hr.unizg.fer.hmo.ts.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandUtils {
	private static final Random rand = ThreadLocalRandom.current();

	// Fisher�Yates shuffle
	public static void shuffle(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--)
			ArrayUtils.swap(arr, i, rand.nextInt(i + 1));
	}

	public static void swapRandomly(int[] arr, int swapCount) {
		for (int i = 0; i < swapCount; i++)
			ArrayUtils.swap(arr, rand.nextInt(arr.length), rand.nextInt(arr.length));
	}

	public static int randomElement(int[] arr) {
		return arr[rand.nextInt(arr.length)];
	}
}
