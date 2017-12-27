package hr.unizg.fer.hmo.ts.optimization.ga.popgen;

import java.util.List;

public interface PopulationGenerator<T> {
	public List<T> generate();
}
