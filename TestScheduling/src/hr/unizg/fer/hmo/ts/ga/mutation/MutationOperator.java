package hr.unizg.fer.hmo.ts.ga.mutation;

public interface MutationOperator<T> {
	public T mutate(T individual);
}
