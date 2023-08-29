package net.hdme.grouptools.checker.result;

import net.hdme.grouptools.base.BinaryOperation;

public class NotAssociative extends CounterExample {

    private final int a, b, c;

    private String buildString(BinaryOperation table, int a, int b, int c, boolean rightAssoc) {
        StringBuilder sb = new StringBuilder();
        if (rightAssoc) {
            sb.append(a).append(" * (").append(b)
                    .append(" * ").append(c).append(") = ");
            int prod = table.multiply(b, c);
            sb.append(a).append(" * ").append(prod).append(" = ");
            prod = table.multiply(a, prod);
            sb.append(prod);
        } else {
            sb.append("(").append(a).append(" * ").append(b)
                    .append(") * ").append(c).append(" = ");
            int prod = table.multiply(a, b);
            sb.append(prod).append(" * ").append(c).append(" = ");
            prod = table.multiply(prod, c);
            sb.append(prod);
        }
        return sb.toString();
    }

    /**
     * Create a counter-example:
     * (a * b) * c != a * (b * c)
     */
    public NotAssociative(BinaryOperation table, int a, int b, int c) {
        super(table);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    protected boolean check(BinaryOperation table) {
        int left = table.multiply(a, b);
        left = table.multiply(left, c);
        int right = table.multiply(b, c);
        right = table.multiply(a, right);
        return left != right;
    }

    @Override
    protected String toString(BinaryOperation table) {
        return buildString(table, a, b, c, false)
                + ", but "
                + buildString(table, a, b, c, true)
                + ", which breaks associativity";
    }

}
