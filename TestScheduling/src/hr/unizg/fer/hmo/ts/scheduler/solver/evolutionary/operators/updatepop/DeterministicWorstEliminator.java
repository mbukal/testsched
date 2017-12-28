package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.Comparator;
import java.util.List;

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
	public List<PartialSolution> update(List<PartialSolution> population, PartialSolution offspring) {
		PartialSolution worst = population.stream().max(comparator).get();
		population.set(population.indexOf(worst), offspring);
		return population;
	}

}
