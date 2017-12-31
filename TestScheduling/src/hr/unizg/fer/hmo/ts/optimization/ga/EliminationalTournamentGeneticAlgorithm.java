package hr.unizg.fer.hmo.ts.optimization.ga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.optimization.ga.mutation.MutationOperator;
import hr.unizg.fer.hmo.ts.optimization.ga.popgen.PopulationGenerator;
import hr.unizg.fer.hmo.ts.optimization.ga.util.ParentPair;
import hr.unizg.fer.hmo.ts.util.LogUtils;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public class EliminationalTournamentGeneticAlgorithm<T> implements GeneticAlgorithm<T> {
	private PopulationGenerator<T> initPopGen;
	private CrossoverOperator<T> crossOp;
	private MutationOperator<T> mutOp;
	private EvaluationFunction<T> evalFunc;

	private int maxIter;

	public EliminationalTournamentGeneticAlgorithm(PopulationGenerator<T> initPopGen,
			CrossoverOperator<T> crossOp, MutationOperator<T> mutOp, EvaluationFunction<T> evalFunc,
			int maxIter) {

		this.initPopGen = initPopGen;
		this.crossOp = crossOp;
		this.mutOp = mutOp;
		this.evalFunc = evalFunc;
		this.maxIter = maxIter;
	}

	@Override
	public T optimize() {
		SortedSet<T> population = initPopGen.generate();
		int iter = 0;

		while (iter < maxIter) {
			Iterator<T> tournament = selectTournament(population);
			T offspring = crossOp
					.reproduce(new ParentPair<T>(tournament.next(), tournament.next()));
			offspring = mutOp.mutate(offspring);
			if (!population.contains(offspring)) {
				population.remove(tournament.next());
				population.add(offspring);
			}
			iter++;
		}

		return population.first();
	}

	private Iterator<T> selectTournament(SortedSet<T> population) {
		List<T> popAsList = new ArrayList<>(population);
		TreeSet<T> tournament = new TreeSet<T>(
				(a, b) -> evalFunc.evaluate(a) - evalFunc.evaluate(b));
		while (tournament.size() < 3)
			tournament.add(popAsList.get(RandUtils.rand.nextInt(popAsList.size())));
		return tournament.iterator();
	}

}
