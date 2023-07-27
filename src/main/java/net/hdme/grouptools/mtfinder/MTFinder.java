package net.hdme.grouptools.mtfinder;

import net.hdme.grouptools.mtchecker.MTChecker;
import net.hdme.grouptools.mtchecker.MultiplicationTable;
import net.hdme.grouptools.util.TableUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Given a partially filled multiplication table,
 * a MTFinder searches for all tables representing a group.
 */
public class MTFinder {

    private final MTChecker checker = new MTChecker();
    private List<MultiplicationTable> results;

    // add 0-th row/column
    // a[i,0]=a[0,i]=i
    private int[][] pad0thRowColumn(int[][] table) {
        int size = table.length;
        int[][] pad = new int[size + 1][size + 1];
        pad[0][0] = 0;
        for (int i = 1; i <= size; i++) pad[0][i] = i;
        for (int i = 1; i <= size; i++) pad[i][0] = i;
        for (int i = 1; i <= size; i++) {
            System.arraycopy(table[i - 1], 0, pad[i], 1, size);
        }
        return pad;
    }

    // get elements table[row][c] and table[r][column]
    // where 0<=c<column and 0<=r<row
    private Set<Integer> getPrefixElementsInRowColumn(int[][] table, int row, int column) {
        Set<Integer> elements = new HashSet<>();
        // collect elements in the row
        for (int c = 0; c < column; c++) {
            int element = table[row][c];
            if (element >= 0) {
                elements.add(element);
            }
        }
        // collect elements in the column
        for (int r = 0; r < row; r++) {
            int element = table[r][column];
            if (element >= 0) {
                elements.add(element);
            }
        }
        return elements;
    }

    // decide what can be filled in the cell at row i, column j
    // depth = (i-1)*size+(j-1)
    // where i/j is the row/column index and size is #elements except 0
    // table should contain the identity element row/column
    private void search(int[][] table, int depth) {
        int size = table.length - 1;
        // border
        if (depth >= size * size) {
            int[][] copy = TableUtils.copyTable(table);
            assert copy != null;
            MultiplicationTable mt = new MultiplicationTable(copy);
            if (checker.check(mt).succeeds()) {
                results.add(mt);
            }
            return;
        }
        // determine the cell to fill
        int i = depth / size + 1, j = depth % size + 1;
        // skip non-blank cells
        if (table[i][j] >= 0) {
            search(table, depth + 1);
            return;
        }
        // find existing elements in row i, column j
        Set<Integer> existing = getPrefixElementsInRowColumn(table, i, j);
        // recursion
        for (int e = 0; e <= size; e++) {
            if (!existing.contains(e)) {
                table[i][j] = e;
                search(table, depth+ 1);
                table[i][j] = -1;
            }
        }
    }

    /**
     * Given a partially filled multiplication table,
     * find all possible tables representing groups.
     * The table contains N rows and columns representing elements 1~N,
     * while the identity element 0 and multiplication with it is implicitly defined.
     * @param template a partially filled table, where negative values indicate blanks and the identity element row/column is omitted
     * @return all possible tables representing groups
     */
    public FinderResult findGroups(int[][] template) {
        results = new LinkedList<>();
        int[][] table = pad0thRowColumn(template);
        search(table, 0);
        return new FinderResult(results);
    }

}
