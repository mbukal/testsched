package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.updatepop;

import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.Event;

public class UpdaterProxy implements UpdatePopulationOperator<PartialSolution> {
	public final Event<Integer> afterUpdate = new Event<>();
	private final UpdatePopulationOperator<PartialSolution> popUpdater;
	private final EvaluationFunction<PartialSolution> evalFunc;
	
	
	public UpdaterProxy(UpdatePopulationOperator<PartialSolution> popUpdater,
			EvaluationFunction<PartialSolution> evalFunc) {
		this.popUpdater = popUpdater;
		this.evalFunc = evalFunc;
	}


	@Override
	public SortedSet<PartialSolution> update(SortedSet<PartialSolution> population, PartialSolution offspring) {
		SortedSet<PartialSolution> newPopulation = popUpdater.update(population, offspring);
		afterUpdate.broadcast(evalFunc.evaluate(newPopulation.first()));
		return newPopulation;
	}

}
