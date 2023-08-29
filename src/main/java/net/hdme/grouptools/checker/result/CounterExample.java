package net.hdme.grouptools.checker.result;

import net.hdme.grouptools.base.BinaryOperation;

public abstract class CounterExample {

    private final BinaryOperation table;

    public CounterExample(BinaryOperation table) {
        this.table = table;
    }

    public boolean check() {
        return check(table);
    }

    /**
     * The method is for testing use.
     * It should check whether the counter-example really belongs to its type.
     * @param table the binary operation in the counter-example
     * @return whether the counter-example IS an example of its current type
     */
    protected abstract boolean check(BinaryOperation table);

    protected abstract String toString(BinaryOperation table);

    @Override
    public String toString() {
        return toString(table);
    }

}
