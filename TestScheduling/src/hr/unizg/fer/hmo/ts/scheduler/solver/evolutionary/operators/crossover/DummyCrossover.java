package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.crossover;

import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class DummyCrossover implements CrossoverOperator<PartialSolution> {

	@Override
	public PartialSolution reproduce(ParentPair<PartialSolution> parents) {
		if (RandUtils.flipCoin() == RandUtils.HEADS) {
			return parents.getParent1().clone();
		} else {
			return parents.getParent2().clone();
		}
	}

}
