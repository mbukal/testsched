package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.inddist;

import hr.unizg.fer.hmo.ts.optimization.ga.inddist.InterIndividualDistance;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.KendallTau;

public class KendallTauDistance implements InterIndividualDistance<PartialSolution> {

	@Override
	public int distance(PartialSolution ps1, PartialSolution ps2) {
		return (int) KendallTau.distance(ps1.priorityToTest, ps2.priorityToTest);
	}

}
