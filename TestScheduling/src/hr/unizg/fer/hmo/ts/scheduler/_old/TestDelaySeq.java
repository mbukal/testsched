package hr.unizg.fer.hmo.ts.scheduler._old;

public class TestDelaySeq {
	private int len;
	public final int[] tests;
	public final int[] delays; // delay from the end of the preceding test

	public TestDelaySeq(int capacity) {
		len = 0;
		tests = new int[capacity];
		delays = new int[capacity];
	}

	private TestDelaySeq(int len, int[] tests, int[] delays) {
		this.len = len;
		this.tests = tests.clone();
		this.delays = delays.clone();
	}

	public void add(int test, int delay) {
		// adds test to the end, O(1)
		tests[len] = test;
		delays[len++] = delay;
	}

	public void insert(int index, int test) {
		// inserts element and shifts succeeding elements to the right, O(n)
		assert index < len;
		int n = len++ - index;
		System.arraycopy(tests, index, tests, index + 1, n);
		System.arraycopy(delays, index, delays, index + 1, n);
		tests[index] = test;
	}

	public void swap(int index1, int index2) {
		// swaps tests without swapping delays, O(1)
		assert index1 < len && index2 < len;
		int tempTest = tests[index1];
		tests[index1] = tests[index2];
		tests[index2] = tempTest;
	}

	public void swapBetweenLists(int index, TestDelaySeq other, int indexOther) {
		// swaps tests between lists, O(1)
		assert index < len && indexOther < other.len;
		int tempTest = tests[index];
		tests[index] = other.tests[indexOther];
		other.tests[indexOther] = tempTest;
	}

	public int pop() {
		// removes and returns last test, O(1)
		return tests[len--];
	}

	public int remove(int index) {
		// removes test at index and shifts succeeding tests to the left, O(n)
		assert index < len;
		int n = --len - index;
		int retTest = tests[index];
		System.arraycopy(tests, index + 1, tests, index, n);
		System.arraycopy(delays, index + 1, delays, index, n);
		return retTest;
	}

	public int replace(int index, int replacementTest) {
		// replaces test at index with replacementTest and returns it, O(1)
		assert index < len;
		int retTest = tests[index];
		tests[index] = replacementTest;
		return retTest;
	}

	public int popReplace(int index) {
		// replaces test at index with the last element and returns it, O(1)
		assert index < len;
		len--;
		int retTest = tests[index];
		tests[index] = tests[len];
		delays[index] = delays[len];
		return retTest;
	}

	public void setDelay(int index, int value) {
		delays[index] = value;
	}

	public int size() {
		return len;
	}

	@Override
	public boolean equals(Object other) {
		TestDelaySeq oth = (TestDelaySeq) other;
		if (oth.size() != len)
			return false;
		for (int i = 0; i < len; i++)
			if (oth.tests[i] != tests[i] || oth.delays[i] != delays[i])
				return false;
		return true;
	}

	@Override
	public TestDelaySeq clone() {
		return new TestDelaySeq(len, tests, delays);
	}
}