package hr.unizg.fer.hmo.ts.optimization.ga.mutation;

public interface MutationOperator<T> {
	public T mutate(T individual);
}
