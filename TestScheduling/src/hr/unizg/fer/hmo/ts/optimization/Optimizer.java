package hr.unizg.fer.hmo.ts.optimization;

public interface Optimizer<TProblem, TSolution> {
	public TSolution optimize(TProblem problem);
}
