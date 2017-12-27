package hr.unizg.fer.hmo.ts.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.unizg.fer.hmo.ts.scheduler.dec.TestTimeSeq;

public class VerboseSolution {
	public class Entry {
		public String testName, machineName;
		public int startTime;

		public Entry(String testName, String machineName, int startTime) {
			this.testName = testName;
			this.startTime = startTime;
			this.machineName = machineName;
		}
		
		@Override
		public String toString() {
			return String.format("'%s',%d,'%s'", testName, startTime, machineName);
		}
	}
	
	public List<Entry> entries;

	public VerboseSolution(VerboseProblem verboseProblem, Solution solution) {
		List<Entry> entries = new ArrayList<Entry>();
		for (int m = 0; m < solution.machineToTestTimeSeq.length; m++) {
			TestTimeSeq tts = solution.machineToTestTimeSeq[m];
			String machineName = verboseProblem.machines.get(m).name;
			for (int i = 0; i < tts.size(); i++) {
				String testName = verboseProblem.tests.get(tts.tests[i]).name;
				entries.add(new Entry(testName, machineName, tts.getStartTime(i)));
			}
		}
		this.entries = Collections.unmodifiableList(entries);		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry e : entries)
			sb.append(e).append(System.lineSeparator());
		return sb.toString();
	}
}
