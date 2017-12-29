package hr.unizg.fer.hmo.ts.optimization.tabusearch.neighborhood;

public interface Neighborhood<T> {
	public Iterable<T> neighbors(T element);
}
