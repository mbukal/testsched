package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.mutation;

import java.util.function.Consumer;

import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;

public class AdaptiveMultiswap implements MutationOperator<PartialSolution>,
											Consumer<Integer> {
	private int noImprovemntCount = 0;
	private int lastImprovementInterval = 0;
	private int currBest = Integer.MAX_VALUE;
	private Mutations.ConfigMultiSwap mutator = Mutations.configurableMultiSwap();

	@Override
	public void accept(Integer newBest) {
		if (newBest > currBest) {
			lastImprovementInterval = noImprovemntCount;
			noImprovemntCount = 0;
		} else {
			noImprovemntCount++;
		}
		mutator.setMaxSwapCount(1 + noImprovemntCount/(lastImprovementInterval + 1));
	}

	@Override
	public PartialSolution mutate(PartialSolution individual) {
		return mutator.mutate(individual);
	}

}
