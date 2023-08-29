package net.hdme.grouptools.checker.result;

import net.hdme.grouptools.base.BinaryOperation;

public class NoIdentity extends CounterExample {

    private final int[][] examples;

    private void appendExample(StringBuilder sb, int[] example) {
        int p = example[2], a0 = example[0], a1 = example[1];
        int i = example[p], ap = example[p];
        sb.append("Element ").append(i).append(" is not identity, as ");
        sb.append(a0).append(" * ").append(a1).append(" != ").append(ap);
        sb.append("\n");
    }

    /**
     * For each element i,
     * create a counter-example <code>examples[i]</code> saying that i is not identity,
     * which is a triple (a_0,a_1,p):
     * <ul>
     *     <li>a_p = i</li>
     *     <li>a_0 * a_1 != a_{1-p}</li>
     * </ul>
     */
    public NoIdentity(BinaryOperation table, int[][] examples) {
        super(table);
        this.examples = examples;
    }

    @Override
    protected boolean check(BinaryOperation table) {
        for (int[] example : examples) {
            int p = example[2];
            if (table.multiply(example[0], example[1]) == example[1 - p])
                return false;
        }
        return true;
    }

    @Override
    protected String toString(BinaryOperation table) {
        StringBuilder sb = new StringBuilder("None of the elements is identity:\n");
        for (int[] example : examples) {
            appendExample(sb, example);
        }
        return sb.toString();
    }

}
