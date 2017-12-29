package hr.unizg.fer.hmo.ts.scheduler.solver.tabu.tenure;

import hr.unizg.fer.hmo.ts.optimization.tabusearch.tenure.TabuTenure;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class ConstantTenure implements TabuTenure<PartialSolution> {
	private final int constTenure;

	public ConstantTenure(int constTenure) {
		this.constTenure = constTenure;
	}

	@Override
	public int tenureOf(PartialSolution element) {
		return constTenure;
	}

}
