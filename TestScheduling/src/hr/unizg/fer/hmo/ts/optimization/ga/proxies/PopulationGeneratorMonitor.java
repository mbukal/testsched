package hr.unizg.fer.hmo.ts.optimization.ga.proxies;

import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.diver.DiversityFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class PopulationGeneratorMonitor
					implements PopulationGenerator<PartialSolution> {
	private final PopulationGenerator<PartialSolution> popGen;
	private final DiversityFunction<PartialSolution> diverFunc;
	
	public PopulationGeneratorMonitor(PopulationGenerator<PartialSolution> popGen,
			DiversityFunction<PartialSolution> diverFunc) {
		this.popGen = popGen;
		this.diverFunc = diverFunc;
	}

	@Override
	public SortedSet<PartialSolution> generate() {
		SortedSet<PartialSolution> population = popGen.generate();
		System.out.println("init pop-diver: " + diverFunc.diversity(population));
		return population;
	}

}
