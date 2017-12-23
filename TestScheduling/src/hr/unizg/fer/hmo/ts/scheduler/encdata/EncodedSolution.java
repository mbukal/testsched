package hr.unizg.fer.hmo.ts.scheduler.encdata;

import java.util.Random;

public class EncodedSolution {
	private static Random rand = new Random();

	public static void setRandomSeed(long seed) {
		rand.setSeed(seed);
	}

	public static EncodedSolution createUninitialized(EncodedProblem problem) {
		return new EncodedSolution(problem);
	}

	public static EncodedSolution createLazilyInitialized(EncodedProblem problem) {
		EncodedSolution es = new EncodedSolution(problem);
		for (int t = 0; t < problem.testCount; t++) {
			int m = problem.testToMachines[t][0];
			es.machineToTestTimeSeq[m].add(t, 0);
		}
		return es;
	}

	public static EncodedSolution createRandomlyInitialized(EncodedProblem problem) {
		EncodedSolution es = new EncodedSolution(problem);
		for (int t = 0; t < problem.testCount; t++) {
			int[] machines = problem.testToMachines[t];
			int m = machines[rand.nextInt(machines.length)];
			es.machineToTestTimeSeq[m].add(t, 0);
		}
		return es;
	}

	// instance

	public final TestTimeSeq[] machineToTestTimeSeq;

	private EncodedSolution(EncodedProblem problem) {
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

	private EncodedSolution(TestTimeSeq[] machineToTestTimeSeq) {
		this.machineToTestTimeSeq = machineToTestTimeSeq;
	}

	public void swapTestsBetweenMachines(int machine1, int index1, int machine2, int index2) {
		machineToTestTimeSeq[machine1].swap(index1, machineToTestTimeSeq[machine2],
				index2);
	}

	public void moveLastTestBetweenMachines(int machineSrc, int machineDest) {
		machineToTestTimeSeq[machineDest].add(machineToTestTimeSeq[machineSrc].pop(), 0);
	}

	@Override
	public boolean equals(Object other) {
		EncodedSolution oth = (EncodedSolution) other;
		for (int i = 0; i < machineToTestTimeSeq.length; i++) {
			if (!machineToTestTimeSeq[i].equals(oth.machineToTestTimeSeq[i]))
				return false;
		}
		return true;
	}

	@Override
	public EncodedSolution clone() {
		TestTimeSeq[] machineToTestTimeSeq = new TestTimeSeq[this.machineToTestTimeSeq.length];
		for (int i = 0; i < machineToTestTimeSeq.length; i++)
			machineToTestTimeSeq[i] = this.machineToTestTimeSeq[i].clone();
		return new EncodedSolution(machineToTestTimeSeq);
	}
}
