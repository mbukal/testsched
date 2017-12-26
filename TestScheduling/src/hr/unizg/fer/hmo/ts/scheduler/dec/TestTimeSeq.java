package hr.unizg.fer.hmo.ts.scheduler.dec;

public class TestTimeSeq {
	private int len;
	public final int[] tests;
	private final int[] startTimes;

	private final int[] testToDuration; // problem.testToDuration. not to be modified

	public TestTimeSeq(int capacity, int[] testToDuration) {
		len = 0;
		tests = new int[capacity];
		startTimes = new int[capacity];
		this.testToDuration = testToDuration;
	}

	private TestTimeSeq(int len, int[] tests, int[] startTimes, int[] testToDuration) {
		this.len = len;
		this.tests = tests.clone();
		this.startTimes = startTimes.clone();
		this.testToDuration = testToDuration;
	}

	public void clear() {
		len = 0;
	}

	public int getStartTime(int index) {
		return startTimes[index];
	}

	public void setStartTime(int index, int value) {
		int delta = value - startTimes[index];
		assert getStartDelay(index) + delta > 0;
		startTimes[index] = value;
		fixContiguousOverlaps(index);
	}

	public int getEndTime(int index) {
		return startTimes[index] + testToDuration[tests[index]];
	}

	public int getStartDelay(int index) {
		return startTimes[index] - (index == 0 ? 0 : getEndTime(index));
	}

	public int getDuration() {
		return getEndTime(len - 1);
	}

	public void add(int test) {
		// adds test to the end, O(1)
		tests[len] = test;
		startTimes[len] = len == 0 ? 0 : getEndTime(len - 1);
		len++;
	}

	public void add(int test, int startTime) {
		// adds test to the end, O(1)
		tests[len] = test;
		startTimes[len] = startTime;
		len++;
	}

	public void insert(int index, int test) {
		// inserts element and shifts succeeding elements to the right, expensive
		assert index < len;
		int n = len++ - index;
		System.arraycopy(tests, index, tests, index + 1, n);
		System.arraycopy(startTimes, index, startTimes, index + 1, n);
		tests[index] = test;

		fixContiguousOverlaps(index);
	}

	public void swap(int index1, int index2) {
		// swaps tests without swapping delays
		assert index1 < len && index2 < len;
		int tempTest = tests[index1];
		tests[index1] = tests[index2];
		tests[index2] = tempTest;

		boolean test1Longer = testToDuration[tests[index1]] > testToDuration[tests[index2]];
		fixContiguousOverlaps(test1Longer ? index1 : index2);
	}

	public void swap(int index, TestTimeSeq other, int indexOther) {
		// swaps tests between TestTimeSeqs
		assert index < len && indexOther < other.len;
		int tempTest = tests[index];
		tests[index] = other.tests[indexOther];
		other.tests[indexOther] = tempTest;

		if (tests[index] > other.tests[indexOther])
			fixContiguousOverlaps(index);
		else
			other.fixContiguousOverlaps(indexOther);
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
		if (leaveTimeGap)
			System.arraycopy(startTimes, index + 1, startTimes, index, n);
		else
			fixOverlaps(index); // TODO
		return retTest;
	}

	public int size() {
		return len;
	}

	@Override
	public boolean equals(Object other) {
		TestTimeSeq oth = (TestTimeSeq) other;
		if (oth.size() != len)
			return false;
		for (int i = 0; i < len; i++)
			if (oth.tests[i] != tests[i] || oth.startTimes[i] != startTimes[i])
				return false;
		return true;
	}

	@Override
	public TestTimeSeq clone() {
		return new TestTimeSeq(len, tests, startTimes, testToDuration);
	}

	private void fixContiguousOverlaps(int fromIndex) {
		// fixes overlaps until first non-overlapping test
		for (int i = fromIndex; i < len - 1; i++) {
			int delay = startTimes[i + 1] - getEndTime(i);
			if (delay >= 0)
				break;
			startTimes[i + 1] -= delay;
		}
	}

	private void fixOverlaps(int fromIndex) {
		for (int i = fromIndex; i < len - 1; i++) {
			int delay = startTimes[i + 1] - getEndTime(i);
			if (delay < 0)
				startTimes[i + 1] -= delay;
		}
	}
}