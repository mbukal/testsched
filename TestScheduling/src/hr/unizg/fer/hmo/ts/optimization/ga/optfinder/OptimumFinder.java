package hr.unizg.fer.hmo.ts.optimization.ga.optfinder;

import java.util.List;

public interface OptimumFinder<T> {
	public T getOptimum(List<T> candidates);
}
