package hr.unizg.fer.hmo.ts.ga.updatepop;

import java.util.List;

public interface UpdatePopulationOperator<T> {
	public List<T> update(List<T> population, T offspring);
}
