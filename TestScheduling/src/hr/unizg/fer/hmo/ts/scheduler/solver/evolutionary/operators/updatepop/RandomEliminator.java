package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.List;
import java.util.Random;

import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class RandomEliminator
			implements UpdatePopulationOperator<PartialSolution> {
	private final Random rnd;
	
	public RandomEliminator() {
		rnd = new Random();
	}
	
	@Override
	public List<PartialSolution> update(List<PartialSolution> population, PartialSolution offspring) {
		population.set(rnd.nextInt(population.size()), offspring);
		return population;
	}

}
