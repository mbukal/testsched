package hr.unizg.fer.hmo.ts.scheduler.solver.tabu;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.BasicTabuSearch;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.neighborhood.Neighborhood;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.tabulist.TabuList;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.tenure.TabuTenure;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class TabuSearchScheduler extends BasicTabuSearch<PartialSolution> {

	public TabuSearchScheduler(int maxIter,
			EvaluationFunction<PartialSolution> evalFunc,
			Neighborhood<PartialSolution> neighborhood,
			TabuList<PartialSolution> tabuList,
			TabuTenure<PartialSolution> tabuTenure) {
		super(maxIter, evalFunc, neighborhood, tabuList, tabuTenure);
	}

}
