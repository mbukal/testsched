package hr.unizg.fer.hmo.ts.ga.crossover;

import hr.unizg.fer.hmo.ts.util.ParentPair;

public interface CrossoverOperator<T> {
	public T reproduce(ParentPair<T> parents);
}
