package hr.unizg.fer.hmo.ts.optimization.ga.selection;

import java.util.List;

import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;

public interface SelectionOperator<T> {
	public ParentPair<T> select(List<T> population);
}
