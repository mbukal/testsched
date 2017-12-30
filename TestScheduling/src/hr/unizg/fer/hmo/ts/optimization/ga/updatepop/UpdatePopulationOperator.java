package hr.unizg.fer.hmo.ts.optimization.ga.updatepop;

import java.util.SortedSet;

public interface UpdatePopulationOperator<T> {
	public SortedSet<T> update(SortedSet<T> population, T offspring);
}
