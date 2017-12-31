package hr.unizg.fer.hmo.ts.demo;

import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Crossovers;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;

public class CrossoverDemo {
	public static void main(String[] args) {
		int testCount = 10;
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(testCount);
		
		PartialSolution ps1 = indGen.generate();
		PartialSolution ps2 = indGen.generate();
		
		System.out.println(ps1);
		System.out.println(ps2);
		
		CrossoverOperator<PartialSolution> crossOp = Crossovers.uniformLike();
		
		PartialSolution ps = crossOp.reproduce(ParentPair.of(ps1, ps2));
		
		System.out.println(ps);
	}
}
