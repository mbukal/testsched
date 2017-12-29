package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen;

import java.util.HashSet;
import java.util.Set;

import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class IndependentPopulationGenerator
			implements PopulationGenerator<PartialSolution> {
	private final IndividualGenerator<PartialSolution> individualGenerator;
	private int populationSize;
	
	public IndependentPopulationGenerator(
			IndividualGenerator<PartialSolution> individualGenerator,
			int populationSize) {
		this.individualGenerator = individualGenerator;
		this.populationSize = populationSize;
	}

	@Override
	public Set<PartialSolution> generate() {
		Set<PartialSolution> population = new HashSet<>();
		
		while (population.size() < populationSize) {
			population.add(individualGenerator.generate());
		}
		
		return population;
	}

}
