package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class ProportionalPopulationUpdater
				implements UpdatePopulationOperator<PartialSolution> {
	private final EvaluationFunction<PartialSolution> evalFunc;

	public ProportionalPopulationUpdater(EvaluationFunction<PartialSolution> evalFunc) {
		this.evalFunc = evalFunc;
	}
	
	@Override
	public List<PartialSolution> update(List<PartialSolution> population, PartialSolution offspring) {
		Map<PartialSolution, Double> psToNormEval = new HashMap<>();
	
		// suspicious norming
		int evalSum = population.stream().mapToInt(ps -> evalFunc.evaluate(ps)).sum();
		population.forEach(ps -> psToNormEval.put(ps, (double)evalFunc.evaluate(ps) / evalSum));

		/*
		// gibberish
		int maxEval = population.stream().mapToInt(ps -> evalFunc.evaluate(ps)).max().getAsInt();
		int minEval = population.stream().mapToInt(ps -> evalFunc.evaluate(ps)).min().getAsInt();
		
		population.forEach(ps -> psToNormEval.put(ps, (double)(evalFunc.evaluate(ps) - minEval) / (maxEval - minEval)));
		*/
		PartialSolution toBeEliminated = RandUtils.spinAWheel(psToNormEval);
		
		population.set(population.indexOf(toBeEliminated), offspring);
				
		return population;
	}

}
