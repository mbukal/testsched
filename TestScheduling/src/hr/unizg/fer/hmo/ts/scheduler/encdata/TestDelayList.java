package hr.unizg.fer.hmo.ts.scheduler.encdata;

public class TestDelayList {
	private int len;
	public final int[] tests;
	public final int[] delays; // delay from the end of the preceding test

	public TestDelayList(int capacity) {
		len = 0;
		tests = new int[capacity];
		delays = new int[capacity];
	}

	private TestDelayList(int len, int[] tests, int[] delays) {
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
		int n = len++ - index;
		System.arraycopy(tests, index, tests, index + 1, n);
		System.arraycopy(delays, index, delays, index + 1, n);
		tests[index] = test;
	}
	
	public void swap(int index1, int index2) {
		// swaps tests without swapping delays, O(1)
		int tempTest = tests[index1];
		tests[index1] = tests[index2];
		tests[index2] = tempTest;
	}

	public int pop() {
		// removes and returns last test, O(1)
		return tests[len--];
	}
	
	public int remove(int index) {
		// removes test at index and shifts succeeding tests to the left, O(n)
		int n = --len - index;
		int retTest = tests[index];
		System.arraycopy(tests, index + 1, tests, index, n);
		System.arraycopy(delays, index + 1, delays, index, n);
		return retTest;
	}
	
	public int removeInsert(int index, int test) {
		// replaces test at index with argument test and returns it, O(1)
		int retTest = tests[index];
		tests[index] = test;
		return retTest;
	}
	
	public int popRemoveInsert(int index) {
		// replaces test at index with the last element and returns, O(1)
		len--;
		int retTest = tests[index];
		tests[index] = tests[len];
		delays[index] = delays[len];
		return retTest;
	}
	
	public int size() {
		return len;
	}

	@Override
	public TestDelayList clone() {
		return new TestDelayList(len, tests, delays);
	}
}