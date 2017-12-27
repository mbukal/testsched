package hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding;

import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public interface SolutionDecoder {
	Solution decode(PartialSolution ps);
}
