package hr.unizg.fer.hmo.ts.optimization.tabusearch;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.neighborhood.Neighborhood;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.tabulist.TabuList;
import hr.unizg.fer.hmo.ts.optimization.tabusearch.tenure.TabuTenure;

public class BasicTabuSearch<T> implements TabuSearch<T> {
	private final int maxIter;
	private final EvaluationFunction<T> evalFunc;
	private final Neighborhood<T> neighborhood;
	private final TabuList<T> tabuList;
	private final TabuTenure<T> tabuTenure;

	public BasicTabuSearch(int maxIter, EvaluationFunction<T> evalFunc,
			Neighborhood<T> neighborhood, TabuList<T> tabuList, TabuTenure<T> tabuTenure) {
		this.maxIter = maxIter;
		this.evalFunc = evalFunc;
		this.neighborhood = neighborhood;
		this.tabuList = tabuList;
		this.tabuTenure = tabuTenure;
	}

	@Override
	public T optimize(T initSolution) {
		int iter = 0;
		tabuList.clear();
		T localBest = initSolution;
		T globalBest = initSolution;
		while (iter < maxIter) {
			// System.out.format("iter %d: %d\n", iter, evalFunc.evaluate(globalBest));
			T bestNeighbor = null;
			for (T neighbor : neighborhood.neighbors(localBest)) {
				if (! tabuList.contains(neighbor)) {
					if (neighbor == null ||
							((neighbor != null) && evalFunc.evaluate(neighbor) < evalFunc.evaluate(localBest))) {
						bestNeighbor = neighbor;
					}
				}
			}
			if (bestNeighbor != null) {
				// System.out.println(evalFunc.evaluate(bestNeighbor));
				localBest = bestNeighbor;
				tabuList.add(localBest, tabuTenure.tenureOf(localBest));
				if (evalFunc.evaluate(localBest) <= evalFunc.evaluate(globalBest)) {
					globalBest = localBest;
				}
			}
			tabuList.update();
			iter++;
		}
			
		return globalBest;
	}

}
