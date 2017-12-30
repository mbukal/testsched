package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class IndependentPopulationGenerator
			implements PopulationGenerator<PartialSolution> {
	private final Comparator<PartialSolution> comparator;
	private final IndividualGenerator<PartialSolution> individualGenerator;
	private int populationSize;
	
	public IndependentPopulationGenerator(
			Comparator<PartialSolution> comparator,
			IndividualGenerator<PartialSolution> individualGenerator,
			int populationSize) {
		this.comparator = comparator;
		this.individualGenerator = individualGenerator;
		this.populationSize = populationSize;
	}

	@Override
	public SortedSet<PartialSolution> generate() {
		SortedSet<PartialSolution> population = new TreeSet<>(comparator);
		
		while (population.size() < populationSize) {
			population.add(individualGenerator.generate());
		}
		
		return population;
	}

}
