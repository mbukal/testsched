package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.popgen;

import java.util.ArrayList;
import java.util.List;

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
	public List<PartialSolution> generate() {
		List<PartialSolution> population = new ArrayList<>();
		
		while (population.size() < populationSize) {
			population.add(individualGenerator.generate());
		}
		
		return population;
	}

}
