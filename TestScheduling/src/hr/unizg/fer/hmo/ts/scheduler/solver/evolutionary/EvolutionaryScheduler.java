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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[MAX-ITER]:");
		sb.append("\n");
		sb.append(maxIter);
		sb.append("\n");
		
		sb.append("[POPULATION GENERATOR]:");
		sb.append("\n");
		sb.append(initPopGen);
		sb.append("\n");
		
		sb.append("[SELECTION OPERATOR]:");
		sb.append("\n");
		sb.append(selectOp);
		sb.append("\n");
		
		sb.append("[CROSSOVER OPERATOR]:");
		sb.append("\n");
		sb.append(crossOp);
		sb.append("\n");
		
		sb.append("[MUTATION OPERATOR]:");
		sb.append("\n");
		sb.append(mutOp);
		sb.append("\n");
		
		sb.append("[POPULATION UPDATER]:");
		sb.append("\n");
		sb.append(updatePopOp);
		sb.append("\n:");
		
		sb.append("[OPTIMUM FINDER]:");
		sb.append("\n");
		sb.append(optFinder);
		sb.append("\n");
		
		return sb.toString();
	}
	
}
