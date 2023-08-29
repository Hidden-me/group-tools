package net.hdme.grouptools.base;

import net.hdme.grouptools.util.TableUtils;

import java.util.Arrays;
import java.util.Set;

/**
 * A binary operation upon a finite set.
 * The operation result should always lie in the same set.
 */
public class BinaryOperation {

    private final int[][] table;
    private final int size;

    private void checkTable() {
        if (size == 0) {
            throw new IllegalArgumentException("The table size should be positive");
        }
        for (int[] row : table) {
            if (row.length != size) {
                throw new IllegalArgumentException("The table should have the same number of rows and columns");
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!isElementValid(table[i][j])) {
                    throw new IllegalArgumentException("The table should only contain integers within [0,size), " +
                            "but the element at row " + i + " column " + j + " breaks the rule");
                }
            }
        }
    }

    /**
     * Specify the binary operation using a 2D table.
     * <p>
     *     The 2D table should meet the following requirements:
     *     <ul>
     *         <li>It has the same number of columns and rows (denoted by N).</li>
     *         <li>N should be a positive integer.</li>
     *         <li>Its elements should be integers within [0,N).</li>
     *     </ul>
     * </p>
     */
    public BinaryOperation(int[][] table) {
        size = table.length;
        this.table = table;
        checkTable();
    }

    /**
     * Specify the binary operation using the string representation of a table.
     * See {@link TableUtils#parseTableSquareNBased(String, String, String, int)} for details of the string format.
     * @param tableStr the string representing the table
     * @param rowDelim the row delimiter
     * @param colDelim the column delimiter
     * @see #BinaryOperation(int[][])
     * @see TableUtils#parseTableSquareNBased(String, String, String, int)
     */
    public BinaryOperation(String tableStr, String rowDelim, String colDelim) {
        this(TableUtils.parseTableSquareNBased(tableStr, rowDelim, colDelim, 0));
    }

    /**
     * Whether the given element is in the domain/codomain.
     * @param element the element whose validity is to be checked
     * @return whether <code>element</code> is within [0,size)
     */
    public boolean isElementValid(int element) {
        return element >= 0 && element < size;
    }

    /**
     * Return a sub-table of this table with the selected elements.
     * @param elements the selected elements
     * @return a sub-table with selected elements,
     * or <code>null</code> if the sub
     */
    public BinaryOperation subTable(Set<Integer> elements) {
        // TODO: impl
        return null;
    }

    /**
     * Compute the operation result of given elements.
     * @param e1 the first operand
     * @param e2 the second operand
     * @return the operation result
     */
    public int multiply(int e1, int e2) {
        if (!isElementValid(e1)) {
            throw new IllegalArgumentException("The first operand should be within [0,size]");
        }
        if (!isElementValid(e2)) {
            throw new IllegalArgumentException("The second operand should be within [0,size]");
        }
        return table[e1][e2];
    }

    /**
     * Get the domain/codomain size.
     * @return the domain/codomain size
     */
    public int getSize() {
        return size;
    }

    /**
     * Whether the table represents a commutative operation.
     * If so, the table should be symmetric with respect to its main diagonal.
     * @return whether the binary operation is commutative
     */
    public boolean isCommutative() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (table[i][j] != table[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation op) {
            return Arrays.deepEquals(table, op.table);
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
