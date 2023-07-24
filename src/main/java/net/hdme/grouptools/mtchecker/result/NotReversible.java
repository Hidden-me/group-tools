package net.hdme.grouptools.mtchecker.result;

import net.hdme.grouptools.mtchecker.MultiplicationTable;

public class NotReversible extends CounterExample {

    private final int a;
    private final boolean left;

    /**
     * Create a counter-example:
     * forall x. a * x != e (when !left)
     * or forall x. x * a != e (when left)
     */
    public NotReversible(MultiplicationTable table, int a, boolean left) {
        super(table);
        this.a = a;
        this.left = left;
    }

    /**
     * Check whether it has no inverse on the specified side.
     * @return whether it has no inverse on the left side if
     *   <code>left</code>, or on the right side otherwise
     */
    @Override
    protected boolean check(MultiplicationTable table) {
        if (left) {
            // whether "a" has inverse on its left side
            for (int x = 1, bound = table.getSize(); x <= bound; x++) {
                if (table.multiply(x, a) == 0) {
                    return false;
                }
            }
        } else {
            // whether "a" has inverse on its right side
            for (int x = 1, bound = table.getSize(); x <= bound; x++) {
                if (table.multiply(a, x) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected String toString(MultiplicationTable table) {
        return a + " has no inverse on its "
                + (left ? "left" : "right")
                + " side, which breaks reversibility";
    }

}
