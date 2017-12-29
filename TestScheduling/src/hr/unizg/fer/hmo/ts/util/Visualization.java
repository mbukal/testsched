package hr.unizg.fer.hmo.ts.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.TestTimeSeq;

public final class Visualization {
	public static String convertSolutionToHTML(Solution solution, Problem problem) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>").append("\n");
		sb.append("  <style>").append("\n");
		sb.append(
				"    .line  { height: 50px; background-color: #eee; white-space: nowrap; padding:0px; margin: 5px }\n");
		sb.append(
				"    .event { height: 40px; background-color: #ccc; display:inline-block; margin-left: -2px; }\n");
		sb.append(
				"    .gap   { height:  0px; background-color: #eee; display:inline-block; margin: 0px }\n");
		sb.append("  </style>").append("\n");
		sb.append("  <body>").append("\n");

		sb.append("    test count: " + problem.testCount).append("<br>\n");
		sb.append("    machine count: " + problem.machineCount).append("<br>\n");
		sb.append("    resource count: " + problem.resourceCount).append("<br>\n");
		sb.append("    resource multiplicities: ");
		for (int r = 0; r < problem.resourceCount; r++)
			sb.append(r).append(": ").append(problem.resourceToMultiplicity[r]).append(", ");
		sb.setLength(sb.length()-2);
		sb.append("<br>\n");
		for (int m = 0; m < problem.machineCount; m++) {
			TestTimeSeq mtts = solution.machineToTestTimeSeq[m];
			sb.append("    <div class=\"line\">").append("\n");
			for (int i = 0; i < mtts.size(); i++) {
				int test = mtts.tests[i];
				sb.append("      ");
				if (mtts.getStartDelay(i) > 0) {
					sb.append("<div class=\"gap\" style=\"");
					sb.append("width:" + mtts.getStartDelay(i) + "px\"");
					sb.append(">").append("</div>");
				}
				sb.append("<div class=\"event\" style=\"");
				sb.append("width:" + problem.testToDuration[test] + "px\"");
				sb.append(">")
						.append("t" + test + ", resources: "
								+ Arrays.toString(problem.testToResources[test]))
						.append("</div>").append("\n");
			}
			sb.append("    </div>").append("\n");
		}
		sb.append("  </body/>").append("\n");
		sb.append("/<html>").append("\n");
		return sb.toString();
	}

	public static void pyDisplayHistogram(List<Integer> sample) {
		String scriptPath = FileUtils.findInAncestor(new File(".").getAbsolutePath(),
				"scripts/histogram.py");
	}
}
