package hr.unizg.fer.hmo.ts.optimization.ga.updatepop;

import java.util.Set;

public interface UpdatePopulationOperator<T> {
	public Set<T> update(Set<T> population, T offspring);
}
