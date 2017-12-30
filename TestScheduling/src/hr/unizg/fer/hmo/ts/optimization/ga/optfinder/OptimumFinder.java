package hr.unizg.fer.hmo.ts.optimization.ga.optfinder;

import java.util.SortedSet;

public interface OptimumFinder<T> {
	public T getOptimum(SortedSet<T> candidates);
}
