package hr.unizg.fer.hmo.ts.scheduler.encdata;

import java.util.Random;

public class IndirectlyEncodedSolution {
	private static Random rand = new Random();

	public static void setRandomSeed(long seed) {
		rand.setSeed(seed);
	}

	public static IndirectlyEncodedSolution createUninitialized(EncodedProblem problem) {
		return new IndirectlyEncodedSolution(problem);
	}

	public static IndirectlyEncodedSolution createLazilyInitialized(EncodedProblem problem) {
		IndirectlyEncodedSolution es = new IndirectlyEncodedSolution(problem);
		for (int t = 0; t < problem.testCount; t++) {
			int m = problem.testToMachines[t][0];
			es.machineToTestSeq[m].add(t, 0);
		}
		return es;
	}

	public static IndirectlyEncodedSolution createRandomlyInitialized(EncodedProblem problem) {
		IndirectlyEncodedSolution es = new IndirectlyEncodedSolution(problem);
		for (int t = 0; t < problem.testCount; t++) {
			int[] machines = problem.testToMachines[t];
			int m = machines[rand.nextInt(machines.length)];
			es.machineToTestSeq[m].add(t, 0);
		}
		return es;
	}

	// instance

	public final TestSeq[] machineToTestSeq;

	private IndirectlyEncodedSolution(EncodedProblem problem) {
		int machineCount = problem.machineCount;
		int[] machineReferenceCounts = new int[machineCount];
		for (int[] machines : problem.testToMachines)
			for (int machine : machines)
				machineReferenceCounts[machine]++;
		machineToTestSeq = new TestSeq[machineCount];
		for (int i = 0; i < machineCount; i++)
			machineToTestSeq[i] = new TestSeq(machineReferenceCounts[i]);
	}

	private IndirectlyEncodedSolution(TestSeq[] machineToTestSeq) {
		this.machineToTestSeq = machineToTestSeq;
	}

	public void swapTestsBetweenMachines(int m1, int index1, int m2, int index2) {
		machineToTestSeq[m1].swap(index1, machineToTestSeq[m2], index2);
	}

	public void moveLastTestBetweenMachines(int mSrc, int mDest) {
		machineToTestSeq[mDest].add(machineToTestSeq[mSrc].pop(), 0);
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
