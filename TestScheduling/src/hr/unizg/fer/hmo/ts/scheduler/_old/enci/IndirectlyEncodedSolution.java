package hr.unizg.fer.hmo.ts.scheduler._old.enci;

public class IndirectlyEncodedSolution {
	public final TestSeq[] machineToTestSeq;

	IndirectlyEncodedSolution(int[] maxTestsPerMachine) {
		int machineCount = maxTestsPerMachine.length;
		machineToTestSeq = new TestSeq[machineCount];
		for (int i = 0; i < machineCount; i++)
			machineToTestSeq[i] = new TestSeq(maxTestsPerMachine[i]);
	}

	private IndirectlyEncodedSolution(TestSeq[] machineToTestSeq) {
		this.machineToTestSeq = machineToTestSeq;
	}

	public void swapTestsBetweenMachines(int m1, int index1, int m2, int index2) {
		machineToTestSeq[m1].swap(index1, machineToTestSeq[m2], index2);
	}

	public void moveLastTestBetweenMachines(int mSrc, int mDest) {
		machineToTestSeq[mDest].add(machineToTestSeq[mSrc].pop());
	}

	@Override
	public boolean equals(Object other) {
		IndirectlyEncodedSolution oth = (IndirectlyEncodedSolution) other;
		for (int i = 0; i < machineToTestSeq.length; i++) {
			if (!machineToTestSeq[i].equals(oth.machineToTestSeq[i]))
				return false;
		}
		return true;
	}

	@Override
	public IndirectlyEncodedSolution clone() {
		TestSeq[] machineToTestSeq = new TestSeq[this.machineToTestSeq.length];
		for (int i = 0; i < machineToTestSeq.length; i++)
			machineToTestSeq[i] = this.machineToTestSeq[i].clone();
		return new IndirectlyEncodedSolution(machineToTestSeq);
	}
}
