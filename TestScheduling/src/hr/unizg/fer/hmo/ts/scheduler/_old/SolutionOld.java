package hr.unizg.fer.hmo.ts.scheduler._old;

import java.util.Random;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.scheduler.enc.TestTimeSeq;

public class SolutionOld {
	private static Random rand = new Random();

	public static void setRandomSeed(long seed) {
		rand.setSeed(seed);
	}

	public static SolutionOld createUninitialized(Problem problem) {
		return new SolutionOld(problem);
	}

	public static SolutionOld createLazilyInitialized(Problem problem) {
		SolutionOld es = new SolutionOld(problem);
		for (int t = 0; t < problem.testCount; t++) {
			int m = problem.testToMachines[t][0];
			es.machineToTestTimeSeq[m].add(t, 0);
		}
		return es;
	}

	public static SolutionOld createRandomlyInitialized(Problem problem) {
		SolutionOld es = new SolutionOld(problem);
		for (int t = 0; t < problem.testCount; t++) {
			int[] machines = problem.testToMachines[t];
			int m = machines[rand.nextInt(machines.length)];
			es.machineToTestTimeSeq[m].add(t, 0);
		}
		return es;
	}

	// instance

	public final TestTimeSeq[] machineToTestTimeSeq;

	private SolutionOld(Problem problem) {
		int machineCount = problem.machineCount;
		int[] machineReferenceCounts = new int[machineCount];
		for (int[] machines : problem.testToMachines)
			for (int machine : machines)
				machineReferenceCounts[machine]++;
		machineToTestTimeSeq = new TestTimeSeq[machineCount];
		for (int i = 0; i < machineCount; i++)
			machineToTestTimeSeq[i] = new TestTimeSeq(machineReferenceCounts[i],
					problem.testToDuration);
	}

	private SolutionOld(TestTimeSeq[] machineToTestTimeSeq) {
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
		SolutionOld oth = (SolutionOld) other;
		for (int i = 0; i < machineToTestTimeSeq.length; i++) {
			if (!machineToTestTimeSeq[i].equals(oth.machineToTestTimeSeq[i]))
				return false;
		}
		return true;
	}

	@Override
	public SolutionOld clone() {
		TestTimeSeq[] machineToTestTimeSeq = new TestTimeSeq[this.machineToTestTimeSeq.length];
		for (int i = 0; i < machineToTestTimeSeq.length; i++)
			machineToTestTimeSeq[i] = this.machineToTestTimeSeq[i].clone();
		return new SolutionOld(machineToTestTimeSeq);
	}
}
