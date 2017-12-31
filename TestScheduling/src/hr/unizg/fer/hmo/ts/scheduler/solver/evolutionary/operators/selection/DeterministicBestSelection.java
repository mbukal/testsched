package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection;

import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class DeterministicBestSelection implements SelectionOperator<PartialSolution> {

	@Override
	public ParentPair<PartialSolution> select(SortedSet<PartialSolution> population) {
		return ParentPair.of(population.first(), population.first());
	}

}
