package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.mutation;

import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionMutator;

public class SingleSwapMutation implements MutationOperator<PartialSolution> {
	private final PartialSolutionMutator psm;
	
	public SingleSwapMutation(PartialSolutionMutator psm) {
		this.psm = psm;
	}

	@Override
	public PartialSolution mutate(PartialSolution ps) {
		psm.swapRandomly(ps);
		return ps;
	}
}
