package net.hdme.grouptools.mtfinder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class TestFinder {

    private final MTFinder finder = new MTFinder();

    @Test
    public void testAll() {
        testFile("tests/tests_mtfinder", -1);
    }

    /**
     * Test against test cases in the specified file.
     * @param filename the name of the file containing test cases
     * @param target specify the target line; a negative value indicates all lines
     */
    public void testFile(String filename, int target) {
        try (LineNumberReader reader = new LineNumberReader(new FileReader(filename))) {
            int count = 0; // test case number
            int lineCount = 1;
            boolean pass = true;
            TestCase test = TestCase.readNext(reader);
            while (test != null) {
                count++;
                // skip not targeted cases
                if (target < 0 || target == count) {
                    try {
                        testCase(test);
                        System.out.println("Case " + count + " (line " + lineCount + ") passed.");
                    } catch (AssertionError e) {
                        System.err.println("Broken assertion at case " + count + " (line " + lineCount + "): ");
                        e.printStackTrace();
                        pass = false;
                    } catch (Throwable e) {
                        System.err.println("Exception at case " + count + " (line " + lineCount + "): ");
                        e.printStackTrace();
                        pass = false;
                    }
                }
                // next case
                lineCount = reader.getLineNumber() + 1;
                test = TestCase.readNext(reader);
            }
            if (!pass) {
                Assertions.fail("Tests failed.");
            }
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Test against the test case.
     * @param test the test case
     */
    public void testCase(TestCase test) {
        // find all possible groups
        FinderResult result = finder.findGroups(test.input);
        // check whether the result is expected
        Assertions.assertEquals(test.expected, result);
    }

}
