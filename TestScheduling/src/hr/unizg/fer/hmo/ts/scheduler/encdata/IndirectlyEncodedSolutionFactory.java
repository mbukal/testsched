package hr.unizg.fer.hmo.ts.scheduler.encdata;

import java.util.Random;

public final class IndirectlyEncodedSolutionFactory {
	private Random rand = new Random();
	int[] maxTestsPerMachine;
	
	public IndirectlyEncodedSolutionFactory(EncodedProblem problem, Random rand) {
		int machineCount = problem.machineCount;
		maxTestsPerMachine = new int[machineCount];
		for (int[] machines : problem.testToMachines)
			for (int machine : machines)
				maxTestsPerMachine[machine]++;
		this.rand = rand;
	}

	public IndirectlyEncodedSolutionFactory(EncodedProblem problem, long randomSeed) {
		this(problem, new Random());
		setRandomSeed(randomSeed);
	}

	public IndirectlyEncodedSolutionFactory(EncodedProblem problem) {
		this(problem, 53);
	}

	private void initialize(EncodedProblem problem, long randomSeed) {
		int machineCount = problem.machineCount;
		maxTestsPerMachine = new int[machineCount];
		for (int[] machines : problem.testToMachines)
			for (int machine : machines)
				maxTestsPerMachine[machine]++;
		setRandomSeed(randomSeed);
	}

	public void setRandomSeed(long seed) {
		rand.setSeed(seed);
	}

	public IndirectlyEncodedSolution createUninitialized(EncodedProblem problem) {
		return new IndirectlyEncodedSolution(maxTestsPerMachine);
	}

	public IndirectlyEncodedSolution createLazilyInitialized(EncodedProblem problem) {
		IndirectlyEncodedSolution s = new IndirectlyEncodedSolution(maxTestsPerMachine);
		for (int t = 0; t < problem.testCount; t++) {
			int m = problem.testToMachines[t][0];
			s.machineToTestSeq[m].add(t);
		}
		return s;
	}

	public IndirectlyEncodedSolution createRandomlyInitialized(EncodedProblem problem) {
		IndirectlyEncodedSolution s = new IndirectlyEncodedSolution(maxTestsPerMachine);
		for (int t = 0; t < problem.testCount; t++) {
			int[] machines = problem.testToMachines[t];
			int m = machines[rand.nextInt(machines.length)];
			s.machineToTestSeq[m].add(t);
		}
		return s;
	}
}
