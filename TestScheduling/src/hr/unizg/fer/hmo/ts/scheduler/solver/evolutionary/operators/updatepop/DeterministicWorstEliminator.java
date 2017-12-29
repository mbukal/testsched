package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.Comparator;
import java.util.Set;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class DeterministicWorstEliminator
				implements UpdatePopulationOperator<PartialSolution> {
	private final Comparator<PartialSolution> comparator;

	public DeterministicWorstEliminator(EvaluationFunction<PartialSolution> evalFunc) {
		comparator = (ps1, ps2) -> evalFunc.evaluate(ps1) - evalFunc.evaluate(ps2); 
	}

	@Override
	public Set<PartialSolution> update(Set<PartialSolution> population, PartialSolution offspring) {
		if (population.contains(offspring)) {
			return population;
		}
		PartialSolution worst = population.stream().max(comparator).get();
		population.remove(worst);
		population.add(offspring);
		return population;
	}

}
