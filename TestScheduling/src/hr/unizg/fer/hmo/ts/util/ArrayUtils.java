package hr.unizg.fer.hmo.ts.util;

public final class ArrayUtils {
	public static void swap(int[] arr, int index1, int index2) {
		int test1 = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = test1;
	}

	public static int[] range(int endExclusive) {
		int[] arr = new int[endExclusive];
		for (int i = 0; i < endExclusive; i++)
			arr[i] = i;
		return arr;
	}

	public static int[] range(int start, int endExclusive) {
		int[] arr = new int[endExclusive - start];
		for (int i = start; i < endExclusive; i++)
			arr[i] = i;
		return arr;
	}
}
