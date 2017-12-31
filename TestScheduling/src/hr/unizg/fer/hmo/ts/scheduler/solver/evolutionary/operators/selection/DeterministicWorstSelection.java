package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection;

import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class DeterministicWorstSelection
					implements SelectionOperator<PartialSolution> {

	@Override
	public ParentPair<PartialSolution> select(SortedSet<PartialSolution> population) {
		PartialSolution parent1 = population.last().clone();
		PartialSolution parent2 = population.last().clone();
		return ParentPair.of(parent1, parent2);
	}

}
