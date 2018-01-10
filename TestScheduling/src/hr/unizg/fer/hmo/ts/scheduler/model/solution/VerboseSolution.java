package hr.unizg.fer.hmo.ts.scheduler.model.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem;
import hr.unizg.fer.hmo.ts.scheduler.model.problem.VerboseProblem.Test;

public class VerboseSolution {
	public class Entry {
		public Test test;
		public String machineName;
		public int startTime;

		public Entry(Test test, String machineName, int startTime) {
			this.test = test;
			this.startTime = startTime;
			this.machineName = machineName;
		}

		@Override
		public String toString() {
			return String.format("'%s',%d,'%s'", test.name, startTime, machineName);
		}
	}

	public final VerboseProblem problem;
	public final List<Entry> entries;
	public final int duration;

	public VerboseSolution(VerboseProblem verboseProblem, Solution solution) {
		this.problem = verboseProblem;
		List<Entry> entries = new ArrayList<Entry>();
		for (int m = 0; m < solution.machineToTestTimeSeq.length; m++) {
			TestTimeSeq tts = solution.machineToTestTimeSeq[m];
			String machineName = verboseProblem.machines.get(m).name;
			for (int i = 0; i < tts.size(); i++) {
				Test test = verboseProblem.tests.get(tts.tests[i]);
				entries.add(new Entry(test, machineName, tts.getStartTime(i)));
			}
		}
		this.entries = Collections.unmodifiableList(entries);
		this.duration = solution.getDuration();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry e : entries)
			sb.append(e).append(System.lineSeparator());
		return sb.toString();
	}
}
