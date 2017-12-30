package hr.unizg.fer.hmo.ts.util;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandUtils {
	public static final Random rand = ThreadLocalRandom.current();
	public final static int HEADS = 0;
	public final static int TAILS = 1;
	private final static double PRECISION = 1e-4;

	public static int randomElement(int[] arr) {
		return arr[rand.nextInt(arr.length)];
	}

	// Fisher–Yates shuffle
	public static void shuffle(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--)
			ArrayUtils.swap(arr, i, rand.nextInt(i + 1));
	}
	
	public static void shufflePart(int[] arr, int startIndex, int exclusiveEndIndex) {
		for (int i = exclusiveEndIndex - 1; i > startIndex; i--)
			ArrayUtils.swap(arr, i, rand.nextInt(i + 1));
	}

	public static int flipCoin() {
		if (rand.nextDouble() < 0.5) {
			return HEADS;
		} else {
			return TAILS;
		}
	}

	public static <T> T spinAWheel(Map<T, Double> elementToValue) {
		double spinnedValue = Math.random();
		double accum = 0;
		for (Map.Entry<T, Double> entry : elementToValue.entrySet()) {
			accum += entry.getValue();
			if (accum > spinnedValue || Math.abs(accum - spinnedValue) < PRECISION) {
				return entry.getKey();
			}
		}
		elementToValue.forEach((el, val) -> System.out.println(el + "--> " + val));
		throw new IllegalArgumentException(
				"Provided map not suitable to this method. Make sure values are normed!");
	}
}
