package org.smartcampus.simulation.framework.messages;

public class ReturnMessage<R> {
    private final R result;

    public ReturnMessage(final R v) {
        this.result = v;
    }

    public R getResult() {
        return this.result;
    }
}
