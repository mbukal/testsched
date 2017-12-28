package hr.unizg.fer.hmo.ts.util;

/******************************************************************************
 *  Compilation:  javac KendallTau.java
 *  Execution:    java KendallTau n
 *  Dependencies: StdOut.java Inversions.java
 *  
 *  Generate two random permutations of size N and compute their
 *  Kendall tau distance (number of inversions).
 *
 ******************************************************************************/

public class KendallTau {

    // return Kendall tau distance between two permutations
    public static long distance(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int n = a.length;

        int[] ainv = new int[n];
        for (int i = 0; i < n; i++)
            ainv[a[i]] = i;

        Integer[] bnew = new Integer[n];
        for (int i = 0; i < n; i++)
            bnew[i] = ainv[b[i]];

        return Inversions.count(bnew);
    } 
}

