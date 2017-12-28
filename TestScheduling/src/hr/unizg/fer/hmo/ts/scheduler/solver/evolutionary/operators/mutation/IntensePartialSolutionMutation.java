package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.mutation;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionMutator;

public class IntensePartialSolutionMutation implements MutationOperator<PartialSolution> {
	private final PartialSolutionMutator psm;
	private final int maxSwaps;
	private static Random rnd = ThreadLocalRandom.current();

	public IntensePartialSolutionMutation(PartialSolutionMutator psm, int maxSwaps) {
		this.psm = psm;
		this.maxSwaps = maxSwaps;
	}

	@Override
	public PartialSolution mutate(PartialSolution individual) {
		int swaps = 1 + rnd.nextInt(maxSwaps);
		psm.swapRandomly(individual, swaps);
		return individual;
	}

}
