package net.hdme.grouptools.mtchecker;

import net.hdme.grouptools.mtchecker.result.CheckerResult;
import net.hdme.grouptools.mtchecker.result.CounterExample;
import net.hdme.grouptools.mtchecker.result.NotAssociative;
import net.hdme.grouptools.mtchecker.result.NotReversible;

/**
 * A checker for multiplication tables.
 */
public class MTChecker {

    public MTChecker() {}

    public CheckerResult check(MultiplicationTable table) {
        int size = table.getSize();
        // check associativity
        for (int a = 0; a <= size; a++) {
            for (int b = 0; b <= size; b++) {
                for (int c = 0; c <= size; c++) {
                    int left = table.multiply(a, b);
                    left = table.multiply(left, c);
                    int right = table.multiply(b, c);
                    right = table.multiply(a, right);
                    if (left != right) {
                        CounterExample example = new NotAssociative(table, a, b, c);
                        return new CheckerResult(example);
                    }
                }
            }
        }
        // check reversibility
        for (int a = 0; a <= size; a++) {
            // find inverse on a's right
            boolean hasInverse = false;
            for (int x = 0; x <= size; x++) {
                if (table.multiply(a, x) == 0) {
                    hasInverse = true;
                    break;
                }
            }
            if (!hasInverse) {
                CounterExample example = new NotReversible(table, a, false);
                return new CheckerResult(example);
            }
            // find inverse on a's left
            hasInverse = false;
            for (int x = 0; x <= size; x++) {
                if (table.multiply(a, x) == 0) {
                    hasInverse = true;
                    break;
                }
            }
            if (!hasInverse) {
                CounterExample example = new NotReversible(table, a, true);
                return new CheckerResult(example);
            }
        }
        // pass
        return new CheckerResult(null);
    }

}
