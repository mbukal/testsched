package hr.unizg.fer.hmo.ts.scheduler.enc;

import hr.unizg.fer.hmo.ts.scheduler.Problem;

public class Solution {
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

	public void swapTestsBetweenMachines(int m1, int index1, int m2, int index2) {
		machineToTestTimeSeq[m1].swap(index1, machineToTestTimeSeq[m2], index2);
	}

	public void moveLastTestBetweenMachines(int mSrc, int mDest) {
		machineToTestTimeSeq[mDest].add(machineToTestTimeSeq[mSrc].pop(), 0);
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
		for (int i = 0; i < machineToTestTimeSeq.length; i++)
			machineToTestTimeSeq[i] = this.machineToTestTimeSeq[i].clone();
		return new Solution(machineToTestTimeSeq);
	}
}
