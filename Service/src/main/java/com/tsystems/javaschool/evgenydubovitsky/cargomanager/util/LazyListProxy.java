package com.tsystems.javaschool.evgenydubovitsky.cargomanager.util;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.operation.DelayedOperation;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.operation.OperationQueue;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.operation.impl.LinkedListOperationQueueImpl;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class LazyListProxy<E> implements Serializable, List<E> {

    private List<E> list;
    private OperationQueue queue = new LinkedListOperationQueueImpl();

    public LazyListProxy(List<E> list) {
        this.list = list;
    }

    @Override
    public boolean remove(Object o) {
        queue.queueOperation(new RemoveOperation(o));
        return true;
    }

    @Override
    public int size() {
        queue.executeQueuedOperations();
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        queue.executeQueuedOperations();
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        queue.executeQueuedOperations();
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        queue.executeQueuedOperations();
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        queue.executeQueuedOperations();
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        queue.executeQueuedOperations();
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return list.add(e);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        queue.executeQueuedOperations();
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        queue.executeQueuedOperations();
        return list.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        queue.executeQueuedOperations();
        list.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        queue.executeQueuedOperations();
        list.sort(c);
    }

    @Override
    public void clear() {
        list.clear();
        queue.clear();
    }

    @Override
    public boolean equals(Object o) {
        queue.executeQueuedOperations();
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        queue.executeQueuedOperations();
        return list.hashCode();
    }

    @Override
    public E get(int index) {
        queue.executeQueuedOperations();
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        queue.executeQueuedOperations();
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        queue.executeQueuedOperations();
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        queue.executeQueuedOperations();
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        queue.executeQueuedOperations();
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        queue.executeQueuedOperations();
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        queue.executeQueuedOperations();
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        queue.executeQueuedOperations();
        return list.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        queue.executeQueuedOperations();
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<E> spliterator() {
        queue.executeQueuedOperations();
        return list.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        queue.executeQueuedOperations();
        return list.removeIf(filter);
    }

    @Override
    public Stream<E> stream() {
        queue.executeQueuedOperations();
        return list.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        queue.executeQueuedOperations();
        return list.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        queue.executeQueuedOperations();
        list.forEach(action);
    }

    private final class RemoveOperation implements Serializable, DelayedOperation {

        private Object value;

        public RemoveOperation(Object value) {
            this.value = value;
        }

        @Override
        public void operate() {
            if (!list.remove(value)) {
                throw new ConcurrentModificationException("Lazy remove failed: instance not found");
            }
        }
    }
}
