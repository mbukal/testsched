package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary;

import hr.unizg.fer.hmo.ts.optimization.ga.SteadyStateGeneticAlgorithm;
import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.optfinder.OptimumFinder;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class EvolutionaryScheduler
				extends SteadyStateGeneticAlgorithm<PartialSolution>{

	public EvolutionaryScheduler(
			PopulationGenerator<PartialSolution> initPopGen,
			SelectionOperator<PartialSolution> selectOp,
			CrossoverOperator<PartialSolution> crossOp,
			MutationOperator<PartialSolution> mutOp,
			UpdatePopulationOperator<PartialSolution> updatePopOp,
			OptimumFinder<PartialSolution> optFinder,
			int maxIter) {
		
		super(initPopGen,
				selectOp,
				crossOp,
				mutOp,
				updatePopOp,
				optFinder,
				maxIter);
	}
	
}
