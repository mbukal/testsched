package hr.unizg.fer.hmo.ts.optimization.ga;

import java.util.SortedSet;

import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.optfinder.OptimumFinder;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.selection.SelectionOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.updatepop.UpdatePopulationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;

public class SteadyStateGeneticAlgorithm<T> implements GeneticAlgorithm<T> {
	protected PopulationGenerator<T> initPopGen;
	
	protected SelectionOperator<T> selectOp;
	
	protected CrossoverOperator<T> crossOp;
	
	protected MutationOperator<T> mutOp;
	
	protected UpdatePopulationOperator<T> updatePopOp;
	
	protected OptimumFinder<T> optFinder;
	
	protected int maxIter;
	
	public SteadyStateGeneticAlgorithm(
			PopulationGenerator<T> initPopGen
			,SelectionOperator<T> selectOp
			,CrossoverOperator<T> crossOp
			,MutationOperator<T> mutOp
			,UpdatePopulationOperator<T> updatePopOp
			,OptimumFinder<T> optFinder
			,int maxIter) {

		this.initPopGen = initPopGen;
		this.selectOp = selectOp;
		this.crossOp = crossOp;
		this.mutOp = mutOp;
		this.updatePopOp = updatePopOp;
		this.optFinder = optFinder;
		this.maxIter = maxIter;
	}



	@Override
	public T optimize() {
		SortedSet<T> population = initPopGen.generate();
		int iter = 0;
		
		while(iter < maxIter) {
			ParentPair<T> parents = selectOp.select(population);
			
			T offspring = crossOp.reproduce(parents);
			
			offspring = mutOp.mutate(offspring);
			
			population = updatePopOp.update(population, offspring);
			
			iter++;
		}
		
		return optFinder.getOptimum(population);
	}

}
