package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class AdaptiveBestSelection implements SelectionOperator<PartialSolution> {
	public final int shrinkCount;
	
	public AdaptiveBestSelection(int shrinkCount) {
		this.shrinkCount = shrinkCount;
	}

	@Override
	public ParentPair<PartialSolution> select(SortedSet<PartialSolution> population) {
		List<PartialSolution> popAsList = new ArrayList<>(population);
		
		int shrink = 0;
		int lb = 0, ub = popAsList.size() - 1;
		while (shrink < shrinkCount) {
			ub = RandUtils.randBetweenInclusive(lb + 1, ub);
			shrink++;	
		}
		
		int rand1 = RandUtils.randBetweenInclusive(lb, ub);
		int rand2 = RandUtils.randBetweenInclusive(lb, ub);
		
		PartialSolution parent1 = popAsList.get(rand1);
		PartialSolution parent2 = popAsList.get(rand2);
		
		return ParentPair.of(parent1, parent2);
	}

}
