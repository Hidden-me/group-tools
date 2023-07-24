package net.hdme.grouptools.mtchecker.result;

import net.hdme.grouptools.mtchecker.MultiplicationTable;

public abstract class CounterExample {

    private final MultiplicationTable table;

    public CounterExample(MultiplicationTable table) {
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
    protected abstract boolean check(MultiplicationTable table);

    protected abstract String toString(MultiplicationTable table);

    @Override
    public String toString() {
        return toString(table);
    }

}
