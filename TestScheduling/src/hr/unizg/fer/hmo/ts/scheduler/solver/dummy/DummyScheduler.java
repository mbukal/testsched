package hr.unizg.fer.hmo.ts.scheduler.solver.dummy;

import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.FastPriorityAdheringSolutionDecoder;
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
		SolutionDecoder decoder = new FastPriorityAdheringSolutionDecoder(problem);
		// start optimization
		PartialSolutionMutator mutator = new PartialSolutionMutator(problem);
		PartialSolution psol = generator.createRandomlyInitialized();
		mutator.swapRandomly(psol, 8);
		for (int i = 0; i < 8; i++)
			mutator.changeMachineRandomly(psol);
		// end optimization
		Solution sol = decoder.decode(psol);
		return sol;
	}
}