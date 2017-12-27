package hr.unizg.fer.hmo.ts.optimization.ga.updatepop;

import java.util.List;

public interface UpdatePopulationOperator<T> {
	public List<T> update(List<T> population, T offspring);
}
