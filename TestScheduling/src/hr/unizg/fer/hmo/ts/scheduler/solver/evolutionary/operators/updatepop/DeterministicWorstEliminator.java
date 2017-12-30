package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class DeterministicWorstEliminator
				implements UpdatePopulationOperator<PartialSolution> {

	@Override
	public SortedSet<PartialSolution> update(SortedSet<PartialSolution> population, PartialSolution offspring) {
		if (population.contains(offspring)) {
			return population;
		}
		PartialSolution worst = population.last();
		population.remove(worst);
		population.add(offspring);
		return population;
	}

}
