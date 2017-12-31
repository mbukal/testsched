package hr.unizg.fer.hmo.ts.optimization.ga.inddist;

public interface InterIndividualDistance<T> {
	public int distance(T ind1, T ind2);
}
