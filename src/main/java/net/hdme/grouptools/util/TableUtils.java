package net.hdme.grouptools.util;

public class TableUtils {
    /**
     * Parse a string as a table whose width and height equal
     * and the row/column with index 1 is the first row/column.
     * The 0-th row and column are left undefined.
     * <p>
     *     The format of the string:
     *     <code>row1 rowDelim row2 ... rowDelim rowN</code>
     *     <br/>
     *     The format of a row:
     *     <code>e1 colDelim e2 ... colDelim eN</code>
     * </p>
     * @param tableStr the string to parse
     * @param rowDelim the row delimiter
     * @param colDelim the column delimiter
     * @return a one-based square table
     */
    public static int[][] parseTableSquare1Based(String tableStr, String rowDelim, String colDelim) {
        // empty string
        if (tableStr.isEmpty()) {
            return new int[1][1];
        }
        // parse string
        String[] rows = tableStr.split(rowDelim);
        int size = rows.length;
        int[][] table = new int[size + 1][size + 1];
        for (int i = 1; i <= size; i++) {
            // split the current row into elements
            String[] cols = rows[i - 1].split(colDelim);
            if (size != cols.length) {
                throw new IllegalArgumentException("The table should have the same number of rows and columns");
            }
            // parse each element in the row
            for (int j = 1; j <= size; j++) {
                table[i][j] = Integer.parseInt(cols[j - 1]);
            }
        }
        return table;
    }
}
