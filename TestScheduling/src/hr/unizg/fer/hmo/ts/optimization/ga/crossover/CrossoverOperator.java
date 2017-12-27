package hr.unizg.fer.hmo.ts.optimization.ga.crossover;

import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;

public interface CrossoverOperator<T> {
	public T reproduce(ParentPair<T> parents);
}
