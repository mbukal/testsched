package hr.unizg.fer.hmo.ts.optimization.ga.popgen;

import java.util.Set;

public interface PopulationGenerator<T> {
	public Set<T> generate();
}
