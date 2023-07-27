package net.hdme.grouptools.mtchecker;

import net.hdme.grouptools.util.TableUtils;

import java.util.Arrays;

/**
 * <p>
 *     A multiplication table is a 2D table that meets these requirements:
 *     <ul>
 *         <li>It has the same number of columns and rows, say N.</li>
 *         <li>N should be a positive integer.</li>
 *         <li>It only contains elements that are integers within [0,N].</li>
 *     </ul>
 *     Each integer within [1,N] actually represents an element in a group,
 *     while 0 stands for the identity element.
 *     Multiplication with the identity element is implicitly defined.
 * </p>
 */
public class MultiplicationTable {

    private final int[][] table;
    private final int size;

    private boolean isValid(int element) {
        return element >= 0 && element <= size;
    }

    private static void init0thRowCol(int[][] table, int size) {
        table[0][0] = 0;
        for (int i = 1; i <= size; i++) table[0][i] = i;
        for (int i = 1; i <= size; i++) table[i][0] = i;
    }

    private void checkTable() {
        if (table.length == 0) {
            throw new IllegalArgumentException("The table size should be positive");
        }
        if (table[0].length != table.length) {
            throw new IllegalArgumentException("The table should have the same number of rows and columns");
        }
        int size = table.length - 1;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (!isValid(table[i][j])) {
                    throw new IllegalArgumentException("The table should only contain integers within [0,size], " +
                            "but the element at row " + i + " column " + j + " breaks the rule");
                }
            }
        }
    }

    /**
     * Specify a table using a 2D array.
     * Note that the 2D array should contain <b>N+1</b> rows/columns,
     * where the 0th row/column are paddings and ignored,
     * while the 1st to N-th rows/columns are the content of the multiplication table.
     * @param table the 2D array with paddings
     */
    public MultiplicationTable(int[][] table) {
        size = table.length - 1;
        this.table = table;
        // check arguments
        checkTable();
        // override the 0th row/column
        init0thRowCol(table, size);
    }

    /**
     * Specify a table using a string.
     * The string should describe rows sequentially.
     * See {@link TableUtils#parseTableSquareNBased(String, String, String, int)} for details of the table string format.
     * <p>
     *     Unlike {@link #MultiplicationTable(int[][])},
     *     the table represented by the string does not need paddings,
     *     so the number of rows/columns should be N.
     * </p>
     * @param tableStr the string representing the table
     * @param rowDelim the row delimiter
     * @param colDelim the column delimiter
     * @see #MultiplicationTable(int[][])
     * @see TableUtils#parseTableSquareNBased(String, String, String, int)
     */
    public MultiplicationTable(String tableStr, String rowDelim, String colDelim) {
        // parse the string
        table = TableUtils.parseTableSquareNBased(tableStr, rowDelim, colDelim, 1);
        size = table.length - 1;
        // check table validity
        checkTable();
        // fill the 0-th row/column
        init0thRowCol(table, size);
    }

    /**
     * Compute the multiplication of given elements.
     * @param e1 the first operand of multiplication
     * @param e2 the second operand of multiplication
     * @return the multiplication result
     */
    public int multiply(int e1, int e2) {
        if (!isValid(e1)) {
            throw new IllegalArgumentException("The first operand should be within [0,size]");
        }
        if (!isValid(e2)) {
            throw new IllegalArgumentException("The second operand should be within [0,size]");
        }
        return table[e1][e2];
    }

    /**
     * Get the size of a group set, excluding the identity element.
     * @return the number of elements excluding identity
     */
    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MultiplicationTable mt) {
            return Arrays.deepEquals(table, mt.table);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(table);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(table);
    }

}
