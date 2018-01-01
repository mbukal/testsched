package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.diver;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.diver.DiversityFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.inddist.InterIndividualDistance;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class AverageDistance implements DiversityFunction<PartialSolution> {
	private final InterIndividualDistance<PartialSolution> dist;

	public AverageDistance(InterIndividualDistance<PartialSolution> dist) {
		this.dist = dist;
	}

	@Override
	public double diversity(SortedSet<PartialSolution> population) {
		List<PartialSolution> popAsList = new ArrayList<>(population);
		int N = population.size();
		double distSum = 0;
		for (int i = 0; i < N - 1; i++) {
			PartialSolution ps1 = popAsList.get(i);
			for (int j = i + 1; j < N; j++) {
				PartialSolution ps2 = popAsList.get(j);
				distSum += dist.distance(ps1, ps2);
			}
		}
		int pairsCount = (N * (N - 1)) / 2;
		return distSum / pairsCount;
	}
	

}
