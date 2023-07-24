package net.hdme.grouptools.mtchecker.result;

public class CheckerResult {

    /**
     * Whether the check passes or fails.
     * If the check fails, it reports a counter-example.
     * Otherwise, this field should be null.
     */
    public final CounterExample counterExample;

    public CheckerResult(CounterExample example) {
        this.counterExample = example;
    }

    public boolean succeeds() {
        return counterExample == null;
    }

    @Override
    public String toString() {
        return succeeds() ? "PASS" : "FAIL: " + counterExample;
    }

}
