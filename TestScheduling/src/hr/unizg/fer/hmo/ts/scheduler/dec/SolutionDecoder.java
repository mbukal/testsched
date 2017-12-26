package hr.unizg.fer.hmo.ts.scheduler.dec;

import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;

interface SolutionDecoder {
	Solution decode(PartialSolution ps);
}
