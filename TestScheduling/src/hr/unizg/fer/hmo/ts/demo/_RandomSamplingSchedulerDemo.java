package hr.unizg.fer.hmo.ts.demo;

import java.io.FileInputStream;
import java.io.IOException;

import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.solver.random.RandomSamplingScheduler;

public class _RandomSamplingSchedulerDemo {
	public static void main(String[] args) throws IOException {
		String path = "C:/Users/Marko/git/testsched/TestScheduling/data/problem-instances/ts1.txt";
		String problemDefinitionString;
		try (FileInputStream problemFile = new FileInputStream(path)) {
			problemDefinitionString = new String(problemFile.readAllBytes());
		}
		VerboseProblem verboseProblem = new VerboseProblem(problemDefinitionString);
		Problem problem = new Problem(verboseProblem);
		
		RandomSamplingScheduler scheduler = new RandomSamplingScheduler(100000);
		Solution solution = scheduler.optimize(problem);
		System.out.println(solution.getDuration());
		System.out.println(verboseProblem.tests.stream().mapToInt(t -> t.duration).sum());
		
	}
}
