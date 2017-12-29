package hr.unizg.fer.hmo.ts.optimization.ga.selection;

import java.util.Set;

import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;

public interface SelectionOperator<T> {
	public ParentPair<T> select(Set<T> population);
}
