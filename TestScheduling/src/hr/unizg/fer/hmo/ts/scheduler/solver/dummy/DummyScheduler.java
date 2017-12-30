package hr.unizg.fer.hmo.ts.scheduler.solver.dummy;

import hr.unizg.fer.hmo.ts.optimization.Optimizer;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SecretSolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;

public class DummyScheduler implements Optimizer<Problem, Solution> {
	protected int evaluate(Solution solution) {
		return solution.getDuration();
	}

	@Override
	public Solution optimize(Problem problem) {
		RandomPartialSolutionGenerator generator = new RandomPartialSolutionGenerator(problem.testCount);
		SolutionDecoder decoder = new SecretSolutionDecoder(problem);
		// start optimization
		MutationOperator<PartialSolution> mutator = Mutations.multiSwap(20);		
		PartialSolution psol = generator.generate();
		mutator.mutate(psol);
		// end optimization
		Solution sol = decoder.decode(psol);
		return sol;
	}
}