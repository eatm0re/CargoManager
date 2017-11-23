package com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.operation;

public interface OperationQueue {

    void queueOperation(DelayedOperation operation);

    void executeQueuedOperations();

    void clear();
}
