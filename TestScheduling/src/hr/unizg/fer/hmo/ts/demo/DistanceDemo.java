package hr.unizg.fer.hmo.ts.demo;

import java.io.IOException;

import hr.unizg.fer.hmo.ts.optimization.ga.inddist.InterIndividualDistance;
import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.Mutations;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.inddist.KendallTauDistance;
import hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen.RandomPartialSolutionGenerator;

public class DistanceDemo {
	public static void main(String[] args) throws IOException {
		IndividualGenerator<PartialSolution> indGen = new RandomPartialSolutionGenerator(500);
		
		PartialSolution ps1 = indGen.generate();
		PartialSolution ps2 = indGen.generate();
		
		PartialSolution _ps1 = ps1.clone();
		
		InterIndividualDistance<PartialSolution> dist = new KendallTauDistance();
		
		System.out.println(dist.distance(ps1, ps2));
		
		System.out.println(dist.distance(ps1, _ps1));
		
		MutationOperator<PartialSolution> mutOp = Mutations.scramble();
		
		_ps1 = mutOp.mutate(_ps1);
		
		System.out.println(dist.distance(ps1, _ps1));
		
	}
	
}
