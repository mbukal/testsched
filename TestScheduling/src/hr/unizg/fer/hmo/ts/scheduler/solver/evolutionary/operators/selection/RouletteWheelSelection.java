package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class RouletteWheelSelection implements SelectionOperator<PartialSolution> {
	private final EvaluationFunction<PartialSolution> evalFunc;

	public RouletteWheelSelection(EvaluationFunction<PartialSolution> evalFunc) {
		this.evalFunc = evalFunc;
	}
	
	@Override
	public ParentPair<PartialSolution> select(SortedSet<PartialSolution> population) {
		Map<PartialSolution, Double> psToInverseEval = new HashMap<>();
		
		int evalSum = population.stream().mapToInt(ps -> evalFunc.evaluate(ps)).sum();
		
		population.forEach(ps -> psToInverseEval.put(ps, (double)evalFunc.evaluate(ps) / evalSum));
		
		PartialSolution parent1 = RandUtils.spinAWheel(psToInverseEval);
		PartialSolution parent2 = RandUtils.spinAWheel(psToInverseEval);
		
		return ParentPair.of(parent1, parent2);
	}

}
