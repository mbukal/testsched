package hr.unizg.fer.hmo.ts.scheduler;

import hr.unizg.fer.hmo.ts.scheduler.dec.FastPriorityAdheringSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.dec.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.enc.PartialSolutionMutator;
import hr.unizg.fer.hmo.ts.optimization.Optimizer;

public class RandomSamplingScheduler implements Optimizer<Problem, Solution> {
	private int sampleCount;

	public RandomSamplingScheduler(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	protected int evaluate(Solution solution) {
		return solution.getDuration();
	}

	@Override
	public Solution optimize(Problem problem) {
		PartialSolutionGenerator generator = new PartialSolutionGenerator(problem);
		SolutionDecoder decoder = new FastPriorityAdheringSolutionDecoder(problem);

		Solution bestSol = null;
		int bestResult = Integer.MAX_VALUE;
		for (int i = 0; i < sampleCount; i++) {
			PartialSolution ps = generator.createRandomlyInitialized();
			Solution s = decoder.decode(ps);
			int res = evaluate(s);
			if (res < bestResult) {
				bestSol = s;
				bestResult = res;
			}
		}
		return bestSol;
	}
}