package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.optfinder;

import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.optfinder.OptimumFinder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class ShortestMakespanFinder implements OptimumFinder<PartialSolution> {

	@Override
	public PartialSolution getOptimum(SortedSet<PartialSolution> candidates) {
		return candidates.first();
	}

}
