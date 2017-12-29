package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
	public ParentPair<PartialSolution> select(Set<PartialSolution> population) {
		
		List<PartialSolution> popAsList = new ArrayList<>(population);

		int index1 = rnd.nextInt(popAsList.size());
		PartialSolution parent1 = popAsList.get(index1);
		
		int index2 = rnd.nextInt(popAsList.size());
		PartialSolution parent2 = popAsList.get(index2);
		
		return ParentPair.of(parent1, parent2);
	}

}
