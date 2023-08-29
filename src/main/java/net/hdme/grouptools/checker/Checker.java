package net.hdme.grouptools.checker;

import net.hdme.grouptools.base.BinaryOperation;
import net.hdme.grouptools.checker.result.*;

/**
 * A Checker checks whether a multiplication table
 * represents a group.
 */
public class Checker {

    private int identity;

    public Checker() {}

    private int checkIdentityLeft(BinaryOperation table, int element) {
        for (int x = 0, size = table.getSize(); x < size; x++) {
            if (table.multiply(element, x) != x) return x;
        }
        return -1;
    }

    private int checkIdentityRight(BinaryOperation table, int element) {
        for (int x = 0, size = table.getSize(); x < size; x++) {
            if (table.multiply(x, element) != x) return x;
        }
        return -1;
    }

    private CounterExample checkIdentity(BinaryOperation table) {
        int size = table.getSize();
        int[][] examples = new int[size][3];
        identity = -1;
        while (++identity < size) {
            int right = checkIdentityLeft(table, identity);
            if (right >= 0) {
                examples[identity][0] = identity;
                examples[identity][1] = right;
                examples[identity][2] = 0;
                continue;
            }
            int left = checkIdentityRight(table, identity);
            if (left >= 0) {
                examples[identity][0] = left;
                examples[identity][1] = identity;
                examples[identity][2] = 1;
                continue;
            }
            // identity is found
            return null;
        }
        return new NoIdentity(table, examples);
    }

    private CounterExample checkAssociativity(BinaryOperation table) {
        int size = table.getSize();
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                for (int c = 0; c < size; c++) {
                    int left = table.multiply(a, b);
                    left = table.multiply(left, c);
                    int right = table.multiply(b, c);
                    right = table.multiply(a, right);
                    if (left != right) {
                        return new NotAssociative(table, a, b, c);
                    }
                }
            }
        }
        return null;
    }

    private CounterExample checkInvertibility(BinaryOperation table) {
        int size = table.getSize();
        for (int a = 0; a < size; a++) {
            // find inverse on a's right
            boolean hasInverse = false;
            for (int x = 0; x < size; x++) {
                if (table.multiply(a, x) == identity) {
                    hasInverse = true;
                    break;
                }
            }
            if (!hasInverse) {
                return new NotInvertible(table, a, false);
            }
            // find inverse on a's left
            hasInverse = false;
            for (int x = 0; x < size; x++) {
                if (table.multiply(a, x) == identity) {
                    hasInverse = true;
                    break;
                }
            }
            if (!hasInverse) {
                return new NotInvertible(table, a, true);
            }
        }
        return null;
    }

    public CheckerResult check(BinaryOperation table) {
        CounterExample example = checkIdentity(table);
        if (example != null) return CheckerResult.mkFailure(example);
        assert table.isElementValid(identity);
        example = checkAssociativity(table);
        if (example != null) return CheckerResult.mkFailure(example);
        example = checkInvertibility(table);
        if (example != null) return CheckerResult.mkFailure(example);
        return CheckerResult.mkSuccess(identity);
    }

}
