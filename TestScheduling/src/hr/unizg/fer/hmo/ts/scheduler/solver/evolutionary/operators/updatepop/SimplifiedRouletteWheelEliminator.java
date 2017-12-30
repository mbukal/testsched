package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class SimplifiedRouletteWheelEliminator
				implements UpdatePopulationOperator<PartialSolution> {
	private final EvaluationFunction<PartialSolution> evalFunc;

	public SimplifiedRouletteWheelEliminator(EvaluationFunction<PartialSolution> evalFunc) {
		this.evalFunc = evalFunc;
	}

	@Override
	public SortedSet<PartialSolution> update(SortedSet<PartialSolution> population, PartialSolution offspring) {
		if (population.contains(offspring)) {
			return population;
		}
		
		Map<PartialSolution, Double> psToNormEval = new HashMap<>();
	
		int evalSum = population.stream().mapToInt(ps -> evalFunc.evaluate(ps)).sum();
		
		population.forEach(ps -> psToNormEval.put(ps, (double)evalFunc.evaluate(ps) / evalSum));
		
		PartialSolution toBeEliminated = RandUtils.spinAWheel(psToNormEval);
		
		population.remove(toBeEliminated);
		population.add(offspring);
				
		return population;
	}

}
