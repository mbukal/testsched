package scheduler;

import java.util.stream.Stream;

class EncodedProblemOld {
	class Test {
		int duration;
		int[] machines, resources;

		public Test(Problem problem, Problem.Test test) {
			this.duration = test.duration;
			this.machines = Stream.of(test.machines).mapToInt(m -> problem.getMachineIndex(m))
					.toArray();
			this.resources = Stream.of(test.resources).mapToInt(r -> problem.getResourceIndex(r))
					.toArray();
		}
	}

	int machineCount;
	int[] resourceMultiplicities;
	Test[] tests;

	public EncodedProblemOld(Problem problem) {
		this.machineCount = problem.machines.size();
		this.resourceMultiplicities = problem.resources.stream().mapToInt(r -> r.multiplicity)
				.toArray();
		this.tests = (Test[]) problem.tests.stream().map(t -> new Test(problem, t)).toArray();
	}
}
