package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection;

import java.util.List;
import java.util.Random;

import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class RandomPartialSolutionSelection 
			implements SelectionOperator<PartialSolution> {
	
	private final Random rnd;
	
	public RandomPartialSolutionSelection() {
		rnd = new Random();
	}

	@Override
	public ParentPair<PartialSolution> select(List<PartialSolution> population) {

		int index1 = rnd.nextInt(population.size());
		PartialSolution parent1 = population.get(index1);
		
		int index2 = index1;
		
		while(index2 == index1) {
			index2 = rnd.nextInt(population.size());
		}
		
		PartialSolution parent2 = population.get(index2);
		
		return ParentPair.of(parent1, parent2);
	}

}
