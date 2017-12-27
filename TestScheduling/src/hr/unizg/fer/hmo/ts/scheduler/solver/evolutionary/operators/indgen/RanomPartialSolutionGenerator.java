package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.indgen;

import hr.unizg.fer.hmo.ts.optimization.ga.indgen.IndividualGenerator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolutionGenerator;

public class RanomPartialSolutionGenerator
			implements IndividualGenerator<PartialSolution> {
	private final PartialSolutionGenerator psg;

	public RanomPartialSolutionGenerator(PartialSolutionGenerator psg) {
		this.psg = psg;
	}
	
	@Override
	public PartialSolution generate() {
		return psg.createRandomlyInitialized();
	}

}
