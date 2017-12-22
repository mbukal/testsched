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
		for (int t = 0; t < problem.testToMachines.length; t++) {
			int m = problem.testToMachines[t][0];
			es.machineToTestDelayList[m].add(t, 0);
		}
		return es;
	}

	public static EncodedSolution createRandomlyInitialized(EncodedProblem problem) {
		EncodedSolution es = new EncodedSolution(problem);
		for (int t = 0; t < problem.testToMachines.length; t++) {
			int[] machines = problem.testToMachines[t];
			int m = machines[rand.nextInt(machines.length)];
			es.machineToTestDelayList[m].add(t, 0);
		}
		return es;
	}

	// instance

	public final TestDelayList[] machineToTestDelayList;

	private EncodedSolution(EncodedProblem problem) {
		int machineCount = problem.machineCount;
		int[] machineReferenceCounts = new int[machineCount];
		for (int[] machines : problem.testToMachines)
			for (int machine : machines)
				machineReferenceCounts[machine]++;
		machineToTestDelayList = new TestDelayList[machineCount];
		for (int i = 0; i < machineCount; i++)
			machineToTestDelayList[i] = new TestDelayList(machineReferenceCounts[i]);
	}

	private EncodedSolution(TestDelayList[] machineToTestDelayList) {
		this.machineToTestDelayList = machineToTestDelayList;
	}

	@Override
	public EncodedSolution clone() {
		TestDelayList[] machineToTestDelayList = new TestDelayList[this.machineToTestDelayList.length];
		for (int i = 0; i < machineToTestDelayList.length; i++)
			machineToTestDelayList[i] = this.machineToTestDelayList[i].clone();
		return new EncodedSolution(machineToTestDelayList);
	}
}
