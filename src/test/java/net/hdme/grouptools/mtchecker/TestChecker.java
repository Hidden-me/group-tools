package net.hdme.grouptools.mtchecker;

import net.hdme.grouptools.mtchecker.result.CheckerResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class TestChecker {

    private MTChecker checker = new MTChecker();

    @Test
    public void testAll() {
        testFile("tests/tests_pass");
    }

    /**
     * Test against test cases in the specified file.
     * @param filename the name of the file containing test cases
     */
    @Test
    public void testFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    testLine(line);
                }
            }
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test against the test case described by a line (i.e. a string).
     * A line should be:
     * <code>table|result</code>.
     * @param line the string representing the test case
     */
    @Test
    public void testLine(String line) {
        // TODO: parse and test
        String[] arr = line.split("\\|");
        assert arr.length == 2;
        String input = arr[0], expectedStr = arr[1];
        // parse the input multiplication table & check validity
        MultiplicationTable table = new MultiplicationTable(input, ";", ",");
        CheckerResult result = checker.check(table);
        // check whether the result is expected
        boolean expected = Boolean.parseBoolean(expectedStr);
        Assertions.assertEquals(expected, result.succeeds());
        // check the counter-example if there is any
        if (!expected) {
            Assertions.assertTrue(result.counterExample.check());
        }
    }

}
