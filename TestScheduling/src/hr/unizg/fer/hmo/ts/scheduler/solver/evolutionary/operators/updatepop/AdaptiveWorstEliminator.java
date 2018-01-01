package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class AdaptiveWorstEliminator implements UpdatePopulationOperator<PartialSolution> {
	private final int shrinkCount;

	public AdaptiveWorstEliminator(int shrinkCount) {
		this.shrinkCount = shrinkCount;
	}

	@Override
	public SortedSet<PartialSolution> update(SortedSet<PartialSolution> population, PartialSolution offspring) {
		if (population.contains(offspring)) {
			return population;
		}
		
		List<PartialSolution> popAsList = new ArrayList<>(population);
		
		int shrink = 0;
		int lb = 0, ub = popAsList.size() - 1;
		while (shrink < shrinkCount) {
			lb = RandUtils.randBetweenInclusive(lb, ub - 1);
			shrink++;	
		}
		
		int randIndex = RandUtils.randBetweenInclusive(lb, ub);
		
		PartialSolution outcast = popAsList.get(randIndex);
		population.remove(outcast);
		population.add(offspring);
		return population;
	}

}
