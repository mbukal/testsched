package hr.unizg.fer.hmo.ts.optimization.ga.optfinder;

import java.util.Set;

public interface OptimumFinder<T> {
	public T getOptimum(Set<T> candidates);
}
