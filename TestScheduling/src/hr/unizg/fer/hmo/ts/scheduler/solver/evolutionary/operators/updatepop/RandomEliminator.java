package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class RandomEliminator
			implements UpdatePopulationOperator<PartialSolution> {
	
	@Override
	public SortedSet<PartialSolution> update(SortedSet<PartialSolution> population, PartialSolution offspring) {
		if (population.contains(offspring)) {
			return population;
		}
		List<PartialSolution> popAsList = new ArrayList<>(population);
		PartialSolution toBeEliminated = popAsList.get(RandUtils.rand.nextInt(popAsList.size()));
		population.remove(toBeEliminated);
		population.add(offspring);
		return population;
	}

}
