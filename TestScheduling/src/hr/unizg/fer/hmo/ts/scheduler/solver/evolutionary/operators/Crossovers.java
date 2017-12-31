package hr.unizg.fer.hmo.ts.scheduler.solver.evolutionary.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.unizg.fer.hmo.ts.optimization.ga.crossover.CrossoverOperator;
import hr.unizg.fer.hmo.ts.scheduler.model.solution.encoding.PartialSolution;
import hr.unizg.fer.hmo.ts.util.RandUtils;

public final class Crossovers {
	private static Random rand = RandUtils.rand;

	public static CrossoverOperator<PartialSolution> randomParentDummy() {
		return (parents) -> (rand.nextBoolean() ? parents.getParent1() : parents.getParent2())
				.clone();
	}

	public static CrossoverOperator<PartialSolution> partiallyMapped() {
		return (parents) -> {
			int[] p1 = parents.getParent1().priorityToTest,
					p2 = parents.getParent2().priorityToTest;
			if (RandUtils.rand.nextBoolean()) {
				int[] temp = p1;
				p1 = p2;
				p2 = temp;
			}
			int br1 = RandUtils.rand.nextInt(p1.length), br2 = RandUtils.rand.nextInt(p1.length);
			if (br1 > br2) {
				int temp = br1;
				br1 = br2;
				br2 = temp;
			}
			int[] c = new int[p1.length]; // child
			boolean[] skips = new boolean[p1.length];
			for (int i = br1; i <= br2; i++) {
				c[i] = p1[i];
				skips[c[i]] = true;
			}
			int i = 0, j = 0;
			while (i < br1) {
				int val = p2[j++];
				if (!skips[val])
					c[i++] = val;
			}
			i = br2 + 1;
			while (i < c.length) {
				int val = p2[j++];
				if (!skips[val])
					c[i++] = val;
			}
			return new PartialSolution(c);
		};
	}
	
	public static CrossoverOperator<PartialSolution> uniformLike() {
		return (parents) -> {
			int[] p1 = parents.getParent1().priorityToTest,
					p2 = parents.getParent2().priorityToTest;
			int[] c = new int[p1.length]; // child
			boolean[] used = new boolean[c.length];
			List<Integer> unassignedPositions = new ArrayList<>();
			for (int i = 0; i < c.length; i++) {
				if (!used[p1[i]] && !used[p2[i]]) {
					c[i] = RandUtils.rand.nextBoolean() ? p1[i] : p2[i];
					used[c[i]] = true;
				} else if (!used[p1[i]] && used[p2[i]]) {
					c[i] = p1[i];
					used[c[i]] = true;
				} else if (used[p1[i]] && !used[p2[i]]) {
					c[i] = p2[i];
					used[c[i]] = true;
				} else {
					unassignedPositions.add(i);
				}
			}
			
			for (int test = 0; test < used.length; test++ ) {
				if (!used[test]) {
					int i = unassignedPositions.remove(RandUtils.rand.nextInt(unassignedPositions.size()));
					c[i] = test;
				}
			}
			
			return new PartialSolution(c);
		};
	}
}
