package hr.unizg.fer.hmo.ts.scheduler.dec;

import hr.unizg.fer.hmo.ts.scheduler.Solution;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;

public interface SolutionDecoder {
	Solution decode(PartialSolution ps);
}
