package hr.unizg.fer.hmo.ts.scheduler.solver.tabu.neighborhood;

import java.util.Iterator;

import hr.unizg.fer.hmo.ts.optimization.tabusearch.neighborhood.Neighborhood;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.ArrayUtils;

public class InterchangeNeighborhood implements Neighborhood<PartialSolution> {
	@Override
	public Iterable<PartialSolution> neighbors(PartialSolution ps) {
		return new IterableNeighborhood(ps);
	}
	
	private class NeighborhoodItarator implements Iterator<PartialSolution> {
		private int currI;
		private int currJ;
		private final PartialSolution ps;
		
		public NeighborhoodItarator(PartialSolution ps) {
			currI = 0;
			currJ = 1;
			this.ps = ps;
		}
		
		@Override
		public boolean hasNext() {
			if (currI < ps.priorityToTest.length - 1) {
				return true;
			} else {
				return false;
			}		
		}

		@Override
		public PartialSolution next() {
			PartialSolution neighbor = ps.clone();
			ArrayUtils.swap(neighbor.priorityToTest, currI, currJ);
			if (currJ + 1 > ps.priorityToTest.length - 1) {
				currI++;
				currJ = currI + 1;
			} else {
				currJ++;
			}
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
