package hr.unizg.fer.hmo.ts.optimization.ga.evalfunc;

public interface EvaluationFunction<T> {
	public int evaluate(T individual);
}
