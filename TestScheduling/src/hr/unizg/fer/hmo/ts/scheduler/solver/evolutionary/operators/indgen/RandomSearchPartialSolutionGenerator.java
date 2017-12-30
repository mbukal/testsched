package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen;

import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.random.RandomSamplingScheduler;

public class RandomSearchPartialSolutionGenerator implements IndividualGenerator<PartialSolution> {
	Problem problem;
	RandomSamplingScheduler gen;

	public RandomSearchPartialSolutionGenerator(Problem problem, int sampleCount) {
		this.problem = problem;
		gen = new RandomSamplingScheduler(sampleCount);
	}

	@Override
	public PartialSolution generate() {
		gen.optimize(problem);
		return gen._bestPsol;
	}
}