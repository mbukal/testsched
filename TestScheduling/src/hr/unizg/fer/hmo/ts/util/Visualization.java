package hr.unizg.fer.hmo.ts.util;

import hr.unizg.fer.hmo.ts.scheduler.model.problem.Problem;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.Solution;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.TestTimeSeq;

public final class Visualization {
	public static String convertSolutionToHTML(Solution solution, Problem problem) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>").append("\n");
		sb.append("  <style>").append("\n");
		sb.append("    .line  { height: 50px; background-color: #eee; white-space: nowrap; padding:5px; margin: 5px }\n");
		sb.append("    .event { height: 40px; background-color: #ccc; display:inline-block; margin: 0px }\n");
		sb.append("    .space { height:  0px; background-color: #eee; display:inline-block; margin: 0px }\n");
		sb.append("  </style>").append("\n");
		sb.append("  <body>").append("\n");
		for (int m = 0; m < problem.machineCount; m++) {
			TestTimeSeq mtts = solution.machineToTestTimeSeq[m];
			sb.append("    <div class=\"line\">").append("\n");
			for (int i = 0; i < mtts.size(); i++) {
				int test = mtts.tests[i];
				System.out.println(mtts.getStartDelay(i));
				sb.append("      ");
				if(mtts.getStartDelay(i)>0) {
					sb.append("<div class=\"space\" style=\"");
					sb.append("width:" + mtts.getStartDelay(i) + "px\"");
					sb.append(">").append("</div>");			
				}				
				sb.append("<div class=\"event\" style=\"");
				sb.append("width:" + problem.testToDuration[test] + "px\"");
				sb.append(">").append(test).append("</div>").append("\n");
			}
			sb.append("    </div>").append("\n");
		}
		sb.append("  </body/>").append("\n");
		sb.append("/<html>").append("\n");
		return sb.toString();
	}
}
