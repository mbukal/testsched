package hr.unizg.fer.hmo.ts.optimization.ga.proxies;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.util.Event;

public class FitnessUpdateMonitor<T> implements EvaluationFunction<T> {
	private EvaluationFunction<T> evalFunc;
	private int best = Integer.MAX_VALUE;

	public final Event<Integer> onUpdate = new Event<Integer>();

	public FitnessUpdateMonitor(EvaluationFunction<T> evalFunc) {
		this.evalFunc = evalFunc;
	}

	@Override
	public int evaluate(T individual) {
		int fitness = evalFunc.evaluate(individual);
		if (fitness < best) {
			best = fitness;
			onUpdate.broadcast(best);
		}
		return fitness;
	}
}
