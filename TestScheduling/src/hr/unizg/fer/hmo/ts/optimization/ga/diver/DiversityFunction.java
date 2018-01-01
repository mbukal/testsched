package hr.unizg.fer.hmo.ts.optimization.ga.diver;

import java.util.SortedSet;

public interface DiversityFunction<T> {
	public double diversity(SortedSet<T> population);
}
