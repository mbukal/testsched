package hr.unizg.fer.hmo.ts.scheduler.dec;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.scheduler.Solution;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;

public class CarelessSolutionDecoder implements SolutionDecoder {
	private final Problem problem;
	Solution solutionTemplate;

	public CarelessSolutionDecoder(Problem problem) {
		this.problem = problem;
		solutionTemplate = new Solution(problem);
	}

	public Solution decode(PartialSolution psol) {
		Solution sol = Solution.emptyLike(solutionTemplate);
		for (int i = 0; i < problem.testCount; i++) {
			int test = psol.priorityToTest[i];
			int mach = psol.testToMachine[test];
			sol.machineToTestTimeSeq[mach].add(test);
		}		
		return sol;
	}
}
