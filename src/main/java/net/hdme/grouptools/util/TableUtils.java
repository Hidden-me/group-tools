package net.hdme.grouptools.util;

public class TableUtils {

    /**
     * Parse a string as a table whose width and height equal
     * and rows/columns start at the index <code>base</code>.
     * The i-th (forall i in [0,base)) row and column are left undefined.
     * <p>
     * The format of the string:
     * <code>row1 rowDelim row2 ... rowDelim rowN</code>
     * <br/>
     * The format of each row:
     * <code>e1 colDelim e2 ... colDelim eN</code>
     * </p>
     *
     * @param tableStr the string to parse
     * @param rowDelim the row delimiter
     * @param colDelim the column delimiter
     * @param base the starting index
     * @return a <code>base</code>-based square table with paddings
     */
    public static int[][] parseTableSquareNBased(String tableStr, String rowDelim, String colDelim, int base) {
        // empty string
        if (tableStr.isEmpty()) {
            return new int[base][base];
        }
        // parse string
        String[] rows = tableStr.split(rowDelim);
        int size = rows.length;
        int[][] table = new int[size + base][size + base];
        for (int i = base; i < size + base; i++) {
            // split the current row into elements
            String[] cols = rows[i - base].split(colDelim);
            if (size != cols.length) {
                throw new IllegalArgumentException("The table should have the same number of rows and columns");
            }
            // parse each element in the row
            for (int j = base; j < size + base; j++) {
                table[i][j] = Integer.parseInt(cols[j - base]);
            }
        }
        return table;
    }

    /**
     * Copy the given table.
     * @param table the original table
     * @return a copy of <code>table</code>
     */
    public static int[][] copyTable(int[][] table) {
        int rowCount = table.length;
        if (rowCount == 0) return null;
        int colCount = table[0].length;
        if (colCount == 0) return null;
        int[][] copy = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            System.arraycopy(table[i], 0, copy[i], 0, colCount);
        }
        return copy;
    }

}
