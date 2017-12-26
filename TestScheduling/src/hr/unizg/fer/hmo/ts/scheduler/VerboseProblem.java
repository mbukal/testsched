package hr.unizg.fer.hmo.ts.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class VerboseProblem {
	public class Resource {
		public final String name;
		public final int multiplicity;

		public Resource(String name, int multiplicity) {
			this.name = name;
			this.multiplicity = multiplicity;
		}

		@Override
		public String toString() {
			return "resource('" + name + "', " + multiplicity + ").";
		}
	}

	public class Machine {
		public final String name;

		public Machine(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "embedded_board('" + name + "').";
		}
	}

	public class Test {
		public final String name;
		public final int duration;
		public final String[] machines, resources;

		public Test(String name, int duration, String[] machines, String[] resources) {
			this.name = name;
			this.duration = duration;
			this.machines = machines;
			this.resources = resources;
		}

		@Override
		public String toString() {
			return "embedded_board('" + name + "').";
		}
	}

	public final List<Machine> machines;
	public final List<Resource> resources;
	public final List<Test> tests;

	public VerboseProblem(String definition) {
		List<Machine> machines = new ArrayList<Machine>();
		List<Resource> resources = new ArrayList<Resource>();
		List<Test> tests = new ArrayList<Test>();
		Pattern testRegex = Pattern.compile(
				"'([^']*)'\\s*,\\s*(\\d+)\\s*,\\s*(\\[[^\\]]*\\])\\s*,\\s*(\\[[^\\]]*\\])");
		Pattern machineRegex = Pattern.compile("'([^']*)'");
		Pattern resourceRegex = Pattern.compile("'([^']*)'\\s*,\\s*(\\d+)");
		for (String line : definition.split("\\r?\\n", -1)) {
			try {
				if (line.length() == 0 || line.matches("\\s*(%.*|$)"))
					continue;
				if (line.startsWith("test")) {
					Matcher m = testRegex.matcher(line);
					String name = m.group(1);
					int duration = Integer.parseInt(m.group(2));
					String[] tmachines = m.group(3).split("'?\\s*,\\s*'?");
					String[] tresources = m.group(4).split("'?\\s*,\\s*'?");
					tests.add(new Test(name, duration, tmachines, tresources));
				} else if (line.startsWith("embedded_board")) {
					machines.add(new Machine(machineRegex.matcher(line).group(1)));
				} else if (line.startsWith("resource")) {
					Matcher m = resourceRegex.matcher(line);
					resources.add(new Resource(m.group(1), Integer.parseInt((m.group(2)))));
				} else
					throw new Exception("Invalid problem definition line");
			} catch (Exception ex) {
				throw new IllegalArgumentException("Invalid problem definition line: \"" + line
						+ "\".\n(" + ex.getMessage() + ")");
			}
		}
		this.machines = Collections.unmodifiableList(machines);
		this.resources = Collections.unmodifiableList(resources);
		this.tests = Collections.unmodifiableList(tests);
	}
	
	public int getTestIndex(String name) {
		return IntStream.range(0, this.tests.size())
				.filter(i -> this.tests.get(i).name == name).findFirst().getAsInt();
	}

	public int getMachineIndex(String name) {
		return IntStream.range(0, this.machines.size())
				.filter(i -> this.machines.get(i).name == name).findFirst().getAsInt();
	}

	public int getResourceIndex(String name) {
		return IntStream.range(0, this.resources.size())
				.filter(i -> this.resources.get(i).name == name).findFirst().getAsInt();
	}

	@Override
	public String toString() {
		String nl = System.lineSeparator();
		StringBuilder sb = new StringBuilder("%% **** Testsuite ****").append(nl);
		sb.append("Number of tests: " + tests.size()).append(nl);
		sb.append("Number of machines: " + machines.size()).append(nl);
		sb.append("Number of resources: " + resources.size()).append(nl);
		sb.append(nl);
		for (Test t : tests)
			sb.append(t.toString()).append(nl);
		sb.append(nl);
		for (Machine m : machines)
			sb.append(m.toString()).append(nl);
		sb.append(nl);
		for (Resource r : resources)
			sb.append(r.toString()).append(nl);
		sb.append(nl);
		return sb.toString();
	}
}
