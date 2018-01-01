package hr.unizg.fer.hmo.ts.optimization.ga.proxies;

import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.diver.DiversityFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.optfinder.OptimumFinder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class OptimumFinderMonitor implements OptimumFinder<PartialSolution> {
	private final OptimumFinder<PartialSolution> optFinder;
	private final DiversityFunction<PartialSolution> diverFunc;
	
	public OptimumFinderMonitor(OptimumFinder<PartialSolution> optFinder,
								DiversityFunction<PartialSolution> diverFunc) {
		this.optFinder = optFinder;
		this.diverFunc = diverFunc;
	}
	
	@Override
	public PartialSolution getOptimum(SortedSet<PartialSolution> candidates) {
		System.out.println("final pop-diver: " + diverFunc.diversity(candidates));
		return optFinder.getOptimum(candidates);
	}

}
