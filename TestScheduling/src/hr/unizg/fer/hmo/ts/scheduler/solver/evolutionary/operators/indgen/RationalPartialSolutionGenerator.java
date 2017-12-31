package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.primitives.Ints;

import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class RationalPartialSolutionGenerator
					implements IndividualGenerator<PartialSolution> {
	private final int[] greedyPriorities;
	private final MutationOperator<PartialSolution> mutOp;
	
	public RationalPartialSolutionGenerator(Problem problem, MutationOperator<PartialSolution> mutOp) {
		this.mutOp = mutOp;
		
		List<Integer> tests = new ArrayList<Integer>();
		IntStream.range(0, problem.testCount).forEach(x -> tests.add(x));
		Collections.sort(tests, (t1, t2) -> problem.testToMachines[t1].length -  problem.testToMachines[t2].length);
		greedyPriorities = Ints.toArray(tests);
	}

	@Override
	public PartialSolution generate() {
		return mutOp.mutate(new PartialSolution(greedyPriorities));	
	}

}
