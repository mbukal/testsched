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

	public static int max(int[] arr) {
		int max = arr[0];
		for (int i = 1; i < arr.length; i++)
			if (arr[i] > max)
				max = arr[i];
		return max;
	}

	public static int min(int[] arr) {
		int min = arr[0];
		for (int i = 1; i < arr.length; i++)
			if (arr[i] < min)
				min = arr[i];
		return min;
	}

	public static int argmin(int[] arr) {
		int argmin = 0, min = arr[0];
		for (int i = 1; i < arr.length; i++)
			if (arr[i] < min) 
				min = arr[argmin = i];
		return argmin;
	}
	
	public static int[] invertedIndex(int[] arr) {
		int[] inv = new int[arr.length];
		for (int i = 1; i < arr.length; i++)
			inv[arr[i]]=i;
		return inv;
	}
}
