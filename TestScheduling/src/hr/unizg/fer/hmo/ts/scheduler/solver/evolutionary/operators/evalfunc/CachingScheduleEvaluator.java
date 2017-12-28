package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators.evalfunc;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import hr.unizg.fer.hmo.ts.optimization.ga.evalfunc.EvaluationFunction;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.decoding.SolutionDecoder;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;

public class CachingScheduleEvaluator implements EvaluationFunction<PartialSolution> {
	private final LoadingCache<PartialSolution, Integer> cache;
	
	public CachingScheduleEvaluator(SolutionDecoder decoder) {
		CacheLoader<PartialSolution, Integer> loader = new CacheLoader<>() {
	        @Override
	        public Integer load(PartialSolution key) {
	        	Solution completeLegalSolution = decoder.decode(key);
	            return completeLegalSolution.getDuration();
	        }
	    };
	    cache = CacheBuilder.newBuilder().weakKeys().build(loader);
	}

	@Override
	public int evaluate(PartialSolution individual) {
		return cache.getUnchecked(individual);
	}

}
