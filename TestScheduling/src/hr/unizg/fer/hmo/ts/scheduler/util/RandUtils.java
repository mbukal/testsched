package hr.unizg.fer.hmo.ts.scheduler.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandUtils {
	private static final Random rand = ThreadLocalRandom.current();

	public static int randomElement(int[] arr) {
		return arr[rand.nextInt(arr.length)];
	}

	// Fisher–Yates shuffle
	public static void shuffle(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--)
			ArrayUtils.swap(arr, i, rand.nextInt(i + 1));
	}
}
