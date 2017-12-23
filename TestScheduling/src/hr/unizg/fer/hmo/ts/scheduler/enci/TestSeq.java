package hr.unizg.fer.hmo.ts.scheduler.enci;

public class TestSeq {
	private int len;
	public final int[] tests;

	public TestSeq(int capacity) {
		len = 0;
		tests = new int[capacity];
	}

	private TestSeq(int len, int[] tests) {
		this.len = len;
		this.tests = tests.clone();
	}

	public void add(int test) {
		// adds test to the end, O(1)
		tests[len++] = test;
	}

	public void insert(int index, int test) {
		// inserts element and shifts succeeding elements to the right, expensive
		assert index < len;
		int n = len++ - index;
		System.arraycopy(tests, index, tests, index + 1, n);
		tests[index] = test;
	}

	public void swap(int index1, int index2) {
		// swaps tests without swapping delays
		assert index1 < len && index2 < len;
		int tempTest = tests[index1];
		tests[index1] = tests[index2];
		tests[index2] = tempTest;
	}

	public void swap(int index, TestSeq other, int indexOther) {
		// swaps tests between TestTimeSeqs
		assert index < len && indexOther < other.len;
		int tempTest = tests[index];
		tests[index] = other.tests[indexOther];
		other.tests[indexOther] = tempTest;
	}

	public int pop() {
		// removes and returns last test, cheap
		return tests[len--];
	}

	public int remove(int index, boolean leaveTimeGap) {
		// removes test at index and shifts succeeding tests to the left, expensive
		assert index < len;
		int n = --len - index;
		int retTest = tests[index];
		System.arraycopy(tests, index + 1, tests, index, n);
		return retTest;
	}

	public int replace(int index, int replacementTest) {
		// replaces test at index with replacementTest and returns it
		assert index < len;
		int retTest = tests[index];
		tests[index] = replacementTest;
		return retTest;
	}

	public int popReplace(int index) {
		// replaces test at index with the last element and returns it
		assert index < len;
		len--;
		int retTest = tests[index];
		tests[index] = tests[len];
		return retTest;
	}

	public int size() {
		return len;
	}

	@Override
	public boolean equals(Object other) {
		TestSeq oth = (TestSeq) other;
		if (oth.size() != len)
			return false;
		for (int i = 0; i < len; i++)
			if (oth.tests[i] != tests[i])
				return false;
		return true;
	}

	@Override
	public TestSeq clone() {
		return new TestSeq(len, tests);
	}
}