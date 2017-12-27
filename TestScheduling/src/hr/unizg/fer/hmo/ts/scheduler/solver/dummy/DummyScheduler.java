package hr.unizg.fer.hmo.ts.scheduler.solver.dummy;

import hr.unizg.fer.hmo.ts.scheduler.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionGenerator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionMutator;
import hr.unizg.fer.hmo.ts.optimization.Optimizer;

public class DummyScheduler implements Optimizer<Problem, Solution> {
	protected int evaluate(Solution solution) {
		return solution.getDuration();
	}

	@Override
	public Solution optimize(Problem problem) {
		PartialSolutionGenerator generator = new PartialSolutionGenerator(problem);
		SolutionDecoder decoder = new SecretSolutionDecoder(problem);
		// start optimization
		PartialSolutionMutator mutator = new PartialSolutionMutator();
		PartialSolution psol = generator.createRandomlyInitialized();
		mutator.swapRandomly(psol, 8);
		// end optimization
		Solution sol = decoder.decode(psol);
		return sol;
	}
}