package hr.unizg.fer.hmo.ts.scheduler._old.model.solution;

import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;

public class Solution {
	public static Solution emptyLike(Solution sol) {
		TestTimeSeq[] machineToTestTimeSeq = new TestTimeSeq[sol.machineToTestTimeSeq.length];
		for (int m = 0; m < machineToTestTimeSeq.length; m++)
			machineToTestTimeSeq[m] = TestTimeSeq.emptyLike(sol.machineToTestTimeSeq[m]);
		return new Solution(machineToTestTimeSeq);
	}

	public final TestTimeSeq[] machineToTestTimeSeq;

	public Solution(Problem problem) {
		int machineCount = problem.machineCount;
		int[] machineReferenceCounts = new int[machineCount];
		for (int[] machines : problem.testToMachines)
			for (int m : machines)
				machineReferenceCounts[m]++;
		machineToTestTimeSeq = new TestTimeSeq[machineCount];
		for (int m = 0; m < machineCount; m++)
			machineToTestTimeSeq[m] = new TestTimeSeq(machineReferenceCounts[m],
					problem.testToDuration);
	}

	private Solution(TestTimeSeq[] machineToTestTimeSeq) {
		this.machineToTestTimeSeq = machineToTestTimeSeq;
	}

	public int getDuration() {
		int max = 0;
		for (TestTimeSeq tts : machineToTestTimeSeq)
			max = Math.max(max, tts.getDuration());
		return max;
	}

	public Solution clear() {
		for (int m = 0; m < machineToTestTimeSeq.length; m++)
			this.machineToTestTimeSeq[m].clear();
		return new Solution(machineToTestTimeSeq);
	}

	@Override
	public boolean equals(Object other) {
		Solution oth = (Solution) other;
		for (int i = 0; i < machineToTestTimeSeq.length; i++) {
			if (!machineToTestTimeSeq[i].equals(oth.machineToTestTimeSeq[i]))
				return false;
		}
		return true;
	}

	@Override
	public Solution clone() {
		TestTimeSeq[] machineToTestTimeSeq = new TestTimeSeq[this.machineToTestTimeSeq.length];
		for (int m = 0; m < machineToTestTimeSeq.length; m++)
			machineToTestTimeSeq[m] = this.machineToTestTimeSeq[m].clone();
		return new Solution(machineToTestTimeSeq);
	}
}
