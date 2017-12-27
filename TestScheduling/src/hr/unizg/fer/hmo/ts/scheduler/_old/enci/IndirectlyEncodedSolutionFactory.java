package hr.unizg.fer.hmo.ts.scheduler._old.enci;

import java.util.Random;

import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;

public final class IndirectlyEncodedSolutionFactory {
	private Random rand = new Random();
	private int[] maxTestsPerMachine;
	
	public IndirectlyEncodedSolutionFactory(Problem problem, Random rand) {
		int machineCount = problem.machineCount;
		maxTestsPerMachine = new int[machineCount];
		for (int[] machines : problem.testToMachines)
			for (int machine : machines)
				maxTestsPerMachine[machine]++;
		this.rand = rand;
	}

	public IndirectlyEncodedSolutionFactory(Problem problem, long randomSeed) {
		this(problem, new Random());
		setRandomSeed(randomSeed);
	}

	public IndirectlyEncodedSolutionFactory(Problem problem) {
		this(problem, 53);
	}

	public void setRandomSeed(long seed) {
		rand.setSeed(seed);
	}

	public IndirectlyEncodedSolution createUninitialized(Problem problem) {
		return new IndirectlyEncodedSolution(maxTestsPerMachine);
	}

	public IndirectlyEncodedSolution createLazilyInitialized(Problem problem) {
		IndirectlyEncodedSolution s = new IndirectlyEncodedSolution(maxTestsPerMachine);
		for (int t = 0; t < problem.testCount; t++) {
			int m = problem.testToMachines[t][0];
			s.machineToTestSeq[m].add(t);
		}
		return s;
	}

	public IndirectlyEncodedSolution createRandomlyInitialized(Problem problem) {
		IndirectlyEncodedSolution s = new IndirectlyEncodedSolution(maxTestsPerMachine);
		for (int t = 0; t < problem.testCount; t++) {
			int[] machines = problem.testToMachines[t];
			int m = machines[rand.nextInt(machines.length)];
			s.machineToTestSeq[m].add(t);
		}
		return s;
	}
}
