package hr.unizg.fer.hmo.ts.scheduler.solver.random;

import hr.unizg.fer.hmo.ts.optimization.Optimizer;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionGenerator;

public class RandomSamplingScheduler implements Optimizer<Problem, Solution> {
	private int sampleCount;
	public PartialSolution _bestPsol = null;


	public RandomSamplingScheduler(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	protected int evaluate(Solution solution) {
		return solution.getDuration();
	}

	@Override
	public Solution optimize(Problem problem) {
		PartialSolutionGenerator generator = new PartialSolutionGenerator(problem);
		SolutionDecoder decoder = new SecretSolutionDecoder(problem);

		Solution bestSol = null;
		int bestResult = Integer.MAX_VALUE;
		for (int i = 0; i < sampleCount; i++) {
			PartialSolution ps = generator.createRandomlyInitialized();
			Solution s = decoder.decode(ps);
			int res = evaluate(s);
			if (res < bestResult) {
				bestSol = s;
				bestResult = res;
				_bestPsol = ps;
			}
		}
		return bestSol;
	}
}