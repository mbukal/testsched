package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class RouletteWheelEliminator
				implements UpdatePopulationOperator<PartialSolution> {
	private final EvaluationFunction<PartialSolution> evalFunc;

	public RouletteWheelEliminator(EvaluationFunction<PartialSolution> evalFunc) {
		this.evalFunc = evalFunc;
	}
	
	@Override
	public SortedSet<PartialSolution> update(SortedSet<PartialSolution> population, PartialSolution offspring) {
		if (population.contains(offspring)) {
			return population;
		}
		
		Map<PartialSolution, Double> psToNormEval = new HashMap<>();
	
		int minEval = population.stream().mapToInt(ps -> evalFunc.evaluate(ps)).min().getAsInt();
		int evalSum = population.stream().mapToInt(ps -> (evalFunc.evaluate(ps) - minEval)).sum();
		
		population.forEach(ps -> psToNormEval.put(ps, (double)(evalFunc.evaluate(ps) - minEval) / evalSum));
		
		PartialSolution toBeEliminated = null;
		try {
			toBeEliminated = RandUtils.spinAWheel(psToNormEval);
		} catch (IllegalArgumentException ex) {
			int distinctEvals = population.stream().mapToInt(ps -> evalFunc.evaluate(ps)).boxed().collect(Collectors.toSet()).size();
			System.out.println("distinct evals: " + distinctEvals);
			System.out.println("evalSum: " + evalSum);
			throw ex;
		}
		
		population.remove(toBeEliminated);
		population.add(offspring);
				
		return population;
	}

}
