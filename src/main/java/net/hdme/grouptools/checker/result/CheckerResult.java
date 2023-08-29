package net.hdme.grouptools.checker.result;

public class CheckerResult {

    /**
     * Whether the check passes or fails.
     * If the check fails, it reports a counter-example.
     * Otherwise, this field should be <code>null</code>.
     */
    public final CounterExample counterExample;

    /**
     * It should be the identity element if the check passes.
     */
    public final int identity;

    public CheckerResult(CounterExample example, int identity) {
        this.counterExample = example;
        this.identity = identity;
    }

    public static CheckerResult mkSuccess(int identity) {
        return new CheckerResult(null, identity);
    }

    public static CheckerResult mkFailure(CounterExample example) {
        return new CheckerResult(example, -1);
    }

    public boolean succeeds() {
        return counterExample == null;
    }

    @Override
    public String toString() {
        return succeeds() ? "PASS" : "FAIL: " + counterExample;
    }

}
