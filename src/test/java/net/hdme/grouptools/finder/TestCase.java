package net.hdme.grouptools.finder;

import net.hdme.grouptools.base.BinaryOperation;
import net.hdme.grouptools.util.TableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestCase {

    private static final String ROW_DELIM = ";", COLUMN_DELIM = ",";

    public int[][] input;
    public FinderResult expected;

    private int[][] parseTemplate(String str) {
        return TableUtils.parseTableSquareNBased(str, ROW_DELIM, COLUMN_DELIM, 0);
    }

    private BinaryOperation parseTable(String str) {
        return new BinaryOperation(str, ROW_DELIM, COLUMN_DELIM);
    }

    private TestCase(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        assert line != null;
        // template|N
        String[] arr = line.split("\\|");
        input = parseTemplate(arr[0]);
        // N lines
        int n = Integer.parseInt(arr[1]);
        List<BinaryOperation> tables = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            line = reader.readLine();
            assert line != null;
            tables.add(i, parseTable(line));
        }
        expected = new FinderResult(tables);
    }

    /**
     * Load a test case from a BufferedReader.
     * It expects that there are lines not read by the reader.
     * A MTFinder test case consists of several lines:
     * <ul>
     *     <li>template|N</li>
     *     <li>followed by N lines, each of which represents a possible table</li>
     * </ul>
     * @param reader the source of test cases
     */
    public static TestCase readNext(BufferedReader reader) throws IOException {
        try {
            return new TestCase(reader);
        } catch (AssertionError e) {
            // readLine has reached the end
            return null;
        }
    }

}
