package hr.unizg.fer.hmo.ts.scheduler.dec;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;

class CarelessSolutionDecoder implements SolutionDecoder {
	private final Problem problem;

	public CarelessSolutionDecoder(Problem problem) {
		this.problem = problem;
	}

	public Solution decode(PartialSolution psol) {
		Solution sol = new Solution(problem);
		for (int i = 0; i < problem.testCount; i++) {
			int test = psol.priorityToTest[i];
			int mach = psol.testToMachine[test];
			sol.machineToTestTimeSeq[mach].add(test);
		}		
		return sol;
	}
}
