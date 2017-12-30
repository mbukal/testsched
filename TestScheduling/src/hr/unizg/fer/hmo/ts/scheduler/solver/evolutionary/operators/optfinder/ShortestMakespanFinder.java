package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.optfinder;

import java.util.Comparator;
import java.util.Set;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.optfinder.OptimumFinder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class ShortestMakespanFinder implements OptimumFinder<PartialSolution> {
	private final Comparator<PartialSolution> comparator;
	
	public ShortestMakespanFinder(EvaluationFunction<PartialSolution> evalFunc) {
		comparator = (ps1, ps2) -> evalFunc.evaluate(ps1) - evalFunc.evaluate(ps2); 
	}

	@Override
	public PartialSolution getOptimum(Set<PartialSolution> candidates) {
		return candidates.stream().min(comparator).get();
	}

}
