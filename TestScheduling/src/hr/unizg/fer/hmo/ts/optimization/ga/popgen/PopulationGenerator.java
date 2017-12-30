package hr.unizg.fer.hmo.ts.optimization.ga.popgen;

import java.util.SortedSet;

public interface PopulationGenerator<T> {
	public SortedSet<T> generate();
}
