package scheduler;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.io.FileInputStream;
import java.io.IOException;

class Problem {
	class Resource {
		String name;
		int multiplicity;

		public Resource(String name, int multiplicity) {
			this.name = name;
			this.multiplicity = multiplicity;
		}
	}

	class Test {
		public final String name;
		public final int duration;
		public final String[] machines, resources;

		public Test(String name, int duration, String[] machines, String[] resources) {
			this.name = name;
			this.duration = duration;
			this.machines = machines;
			this.resources = resources;
		}
	}

	public ArrayList<String> machines;
	public ArrayList<Resource> resources;
	public ArrayList<Test> tests;

	public Problem(FileInputStream fs) throws IOException {
		this(new String(fs.readAllBytes()));
	}

	public Problem(String definition) {
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
					String[] machines = m.group(3).split("'?\\s*,\\s*'?");
					String[] resources = m.group(4).split("'?\\s*,\\s*'?");
					this.tests.add(new Test(name, duration, machines, resources));
				} else if (line.startsWith("embedded_board")) {
					this.machines.add(machineRegex.matcher(line).group(1));
				} else if (line.startsWith("resource")) {
					Matcher m = resourceRegex.matcher(line);
					this.resources.add(new Resource(m.group(1), Integer.parseInt((m.group(2)))));
				} else
					throw new Exception("Invalid problem definition line");
			} catch (Exception ex) {
				throw new IllegalArgumentException("Invalid problem definition line: \"" + line
						+ "\".\n(" + ex.getMessage() + ")");
			}
		}
	}

	public int getMachineIndex(String name) {
		int ind = this.machines.indexOf(name);
		if (ind == -1)
			throw new NoSuchElementException("Machine with name=" + name + "doesn't exist.");
		return ind;
	}

	public int getResourceIndex(String name) {
		return IntStream.range(0, this.resources.size())
				.filter(i -> this.resources.get(i).name == name).findFirst().getAsInt();
	}
}
