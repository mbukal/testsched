package hr.unizg.fer.hmo.ts.scheduler.solver.tabu.neighborhood;

import java.util.Iterator;

import hr.unizg.fer.hmo.ts.optimization.tabusearch.neighborhood.Neighborhood;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;

public class TransposeNeighborhood implements Neighborhood<PartialSolution> {

	@Override
	public Iterable<PartialSolution> neighbors(PartialSolution ps) {
		return new IterableNeighborhood(ps);
	}
	
	private class NeighborhoodItarator implements Iterator<PartialSolution> {
		private int currIndex;
		private final PartialSolution ps;
		
		public NeighborhoodItarator(PartialSolution ps) {
			currIndex = 0;
			this.ps = ps;
		}
		
		@Override
		public boolean hasNext() {
			if (currIndex < ps.priorityToTest.length - 1) {
				return true;
			} else {
				return false;
			}		
		}

		@Override
		public PartialSolution next() {
			PartialSolution neighbor = ps.clone();
			ArrayUtils.swap(neighbor.priorityToTest, currIndex, currIndex + 1);
			currIndex++;
			return neighbor;
		}
		
	}
	
	private class IterableNeighborhood implements Iterable<PartialSolution> {
		private final PartialSolution ps;
		
		public IterableNeighborhood(PartialSolution ps) {
			this.ps = ps;
		}

		@Override
		public Iterator<PartialSolution> iterator() {
			return new NeighborhoodItarator(ps);
		}
		
	}

}
