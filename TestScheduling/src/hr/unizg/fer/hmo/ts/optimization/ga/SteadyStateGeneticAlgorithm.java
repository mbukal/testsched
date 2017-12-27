package hr.unizg.fer.hmo.ts.optimization.ga;

import java.util.List;

import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.initpopgen.InitialPopulationGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.optfinder.OptimumFinder;
import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.stopcrit.StopCriterion;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;

public class SteadyStateGeneticAlgorithm<T> implements GeneticAlgorithm<T> {
	private InitialPopulationGenerator<T> initPopGen;
	
	private EvaluationFunction<T> evalFunc;
	
	private SelectionOperator<T> selectOp;
	
	private CrossoverOperator<T> crossOp;
	
	private MutationOperator<T> mutOp;
	
	private UpdatePopulationOperator<T> updatePopOp;
	
	private OptimumFinder<T> optFinder;
	
	private StopCriterion stopCrit;
	
	public SteadyStateGeneticAlgorithm(
			InitialPopulationGenerator<T> initPopGen,
			EvaluationFunction<T> evalFunc,
			SelectionOperator<T> selectOp,
			CrossoverOperator<T> crossOp,
			MutationOperator<T> mutOp,
			UpdatePopulationOperator<T> updatePopOp,
			OptimumFinder<T> optFinder,
			StopCriterion stopCrit) {

		this.initPopGen = initPopGen;
		this.evalFunc = evalFunc;
		this.selectOp = selectOp;
		this.crossOp = crossOp;
		this.mutOp = mutOp;
		this.updatePopOp = updatePopOp;
		this.optFinder = optFinder;
		this.stopCrit = stopCrit;
	}



	@Override
	public T optimize() {
		List<T> population = initPopGen.generate();
		
		while(! stopCrit.isMet()) {
			ParentPair<T> parents = selectOp.select(population);
			
			T offspring = crossOp.reproduce(parents);
			
			offspring = mutOp.mutate(offspring);
			
			population = updatePopOp.update(population, offspring);
		}
		
		return optFinder.getOptimum(population);
	}

}
