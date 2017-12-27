package hr.unizg.fer.hmo.ts.optimization.ga.initpopgen;

import java.util.List;

public interface InitialPopulationGenerator<T> {
	public List<T> generate();
}
