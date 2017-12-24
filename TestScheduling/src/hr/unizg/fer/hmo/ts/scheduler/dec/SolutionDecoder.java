package hr.unizg.fer.hmo.ts.scheduler.dec;

import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.enc.Solution;

interface SolutionDecoder {
	Solution decode(PartialSolution ps);
}
