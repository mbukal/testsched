package hr.unizg.fer.hmo.ts.ga.selection;

import java.util.List;

import hr.unizg.fer.hmo.ts.util.ParentPair;

public interface SelectionOperator<T> {
	public ParentPair<T> select(List<T> population);
}
