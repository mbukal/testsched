package hr.unizg.fer.hmo.ts.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandUtils {
	private static final Random rand = ThreadLocalRandom.current();
	public final static int HEADS = 0;
	public final static int TAILS = 1;

	public static int randomElement(int[] arr) {
		return arr[rand.nextInt(arr.length)];
	}

	// Fisher–Yates shuffle
	public static void shuffle(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--)
			ArrayUtils.swap(arr, i, rand.nextInt(i + 1));
	}
	
	public static int flipCoin() {
		if (rand.nextDouble() < 0.5) {
			return HEADS;
		} else {
			return TAILS;
		}
	}
}
