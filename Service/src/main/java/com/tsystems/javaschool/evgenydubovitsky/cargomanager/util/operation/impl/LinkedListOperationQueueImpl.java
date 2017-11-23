package com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.operation.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.operation.DelayedOperation;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.operation.OperationQueue;

import java.util.LinkedList;

public class LinkedListOperationQueueImpl implements OperationQueue {

    private LinkedList<DelayedOperation> queue;

    @Override
    public void queueOperation(DelayedOperation operation) {
        if (queue == null) {
            queue = new LinkedList<>();
        }
        queue.add(operation);
    }

    @Override
    public void executeQueuedOperations() {
        if (queue == null) {
            return;
        }

        for (DelayedOperation operation : queue) {
            operation.operate();
        }

        queue.clear();
    }

    @Override
    public void clear() {
        queue.clear();
    }
}
