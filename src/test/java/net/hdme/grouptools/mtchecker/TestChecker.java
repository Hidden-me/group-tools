package net.hdme.grouptools.mtchecker;

import net.hdme.grouptools.mtchecker.result.CheckerResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class TestChecker {

    private final MTChecker checker = new MTChecker();

    @Test
    public void testAll() {
        testFile("tests/tests_pass", -1);
    }

    /**
     * Test against test cases in the specified file.
     * @param filename the name of the file containing test cases
     * @param target specify the target line; a negative value indicates all lines
     */
    public void testFile(String filename, int target) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            boolean pass = true;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    count++;
                    // skip not targeted lines
                    if (target >= 0 && target != count) {
                        continue;
                    }
                    // it is the target line
                    // or no target is specified
                    try {
                        testLine(line);
                    } catch (AssertionError e) {
                        System.err.println("Broken assertion at line " + count + ": ");
                        e.printStackTrace();
                        pass = false;
                    } catch (Throwable e) {
                        System.err.println("Exception at line " + count + ": ");
                        e.printStackTrace();
                        pass = false;
                    }
                }
            }
            if (!pass) {
                Assertions.fail("Tests failed.");
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
    public void testLine(String line) {
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
