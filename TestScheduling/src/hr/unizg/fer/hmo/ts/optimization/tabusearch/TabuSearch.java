package hr.unizg.fer.hmo.ts.optimization.tabusearch;

public interface TabuSearch<T> {
	public T optimize(T initSolution);
}
