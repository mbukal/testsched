package hr.unizg.fer.hmo.ts.scheduler.solver.tabu.tenure;

import java.util.Random;

import hr.unizg.fer.hmo.ts.optimization.tabusearch.tenure.TabuTenure;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class RandomTenure implements TabuTenure<PartialSolution> {
	private final int minTenure;
	private final int maxTenure;
	private Random rnd;

	public RandomTenure(int minTenure, int maxTenure) {
		this.minTenure = minTenure;
		this.maxTenure = maxTenure;
		rnd = null;
	}

	@Override
	public int tenureOf(PartialSolution element) {
		if (rnd == null) {
			rnd = new Random();
		}
		return minTenure + rnd.nextInt(maxTenure);
	}

}
