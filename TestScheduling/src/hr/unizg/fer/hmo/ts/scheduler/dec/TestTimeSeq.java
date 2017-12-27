package hr.unizg.fer.hmo.ts.scheduler.dec;

public class TestTimeSeq {
	public static TestTimeSeq emptyLike(TestTimeSeq tts) {
		return new TestTimeSeq(tts.len, new int[tts.capacity], new int[tts.capacity],
				tts.testToDuration);
	}

	public final int capacity;
	private int len;
	public final int[] tests;
	private final int[] startTimes;

	private final int[] testToDuration; // problem.testToDuration. not to be modified

	public TestTimeSeq(int capacity, int[] testToDuration) {
		this.capacity = capacity;
		len = 0;
		tests = new int[capacity];
		startTimes = new int[capacity];
		this.testToDuration = testToDuration;
	}

	private TestTimeSeq(int len, int[] tests, int[] startTimes, int[] testToDuration) {
		this.capacity = tests.length;
		this.len = len;
		this.tests = tests;
		this.startTimes = startTimes;
		this.testToDuration = testToDuration;
	}

	@Override
	public TestTimeSeq clone() {
		return new TestTimeSeq(len, tests.clone(), startTimes.clone(), testToDuration);
	}

	public void clear() {
		len = 0;
	}

	public int getStartTime(int index) {
		return startTimes[index];
	}

	public void setStartTime(int index, int value) {
		startTimes[index] = value;
	}

	public int getEndTime(int index) {
		return startTimes[index] + testToDuration[tests[index]];
	}

	public int getStartDelay(int index) {
		return startTimes[index] - (index == 0 ? 0 : getEndTime(index));
	}

	public int getDuration() {
		if (len == 0)
			return 0;
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

	public int pop() {
		// removes and returns last test, cheap
		return tests[len--];
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
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < len; i++)
			sb.append("(" + tests[i] + ", " + startTimes[i] + "), ");
		if (len > 0)
			sb.setLength(sb.length() - 2);
		return sb.append("]").toString();
	}
}