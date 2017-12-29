package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class RandomEliminator
			implements UpdatePopulationOperator<PartialSolution> {
	private final Random rnd;
	
	public RandomEliminator() {
		rnd = new Random();
	}
	
	@Override
	public Set<PartialSolution> update(Set<PartialSolution> population, PartialSolution offspring) {
		if (population.contains(offspring)) {
			return population;
		}
		List<PartialSolution> popAsList = new ArrayList<>(population);
		PartialSolution toBeEliminated = popAsList.get(rnd.nextInt(popAsList.size()));
		population.remove(toBeEliminated);
		population.add(offspring);
		return population;
	}

}
