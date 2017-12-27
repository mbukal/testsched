package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class ScheduleEvaluator implements EvaluationFunction<PartialSolution> {
	private final SolutionDecoder decoder;
		
	public ScheduleEvaluator(SolutionDecoder decoder) {
		this.decoder = decoder;
	}

	@Override
	public int evaluate(PartialSolution partialSolution) {
		Solution completeLegalSolution = decoder.decode(partialSolution);
		return completeLegalSolution.getDuration();
	}

}
