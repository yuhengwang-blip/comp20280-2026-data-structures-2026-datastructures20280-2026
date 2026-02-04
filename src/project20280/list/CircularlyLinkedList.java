package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        Node<E> curr = tail.getNext();
        for (int j = 0; j < i; j++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        if (i == 0) {
            addFirst(e);
            return;
        }
        if (i == size) {
            addLast(e);
            return;
        }
        Node<E> pred = tail.getNext();
        for (int j = 0; j < i - 1; j++) {
            pred = pred.getNext();
        }
        Node<E> newNode = new Node<>(e, pred.getNext());
        pred.setNext(newNode);
        size++;
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        if (i == 0) {
            return removeFirst();
        }
        if (i == size - 1) {
            return removeLast();
        }
        Node<E> pred = tail.getNext();
        for (int j = 0; j < i - 1; j++) {
            pred = pred.getNext();
        }
        Node<E> toRemove = pred.getNext();
        pred.setNext(toRemove.getNext());
        size--;
        return toRemove.getData();
    }

    public void rotate() {
        if (tail != null) {
            tail = tail.getNext();
        }
    }

    private class CircularlyLinkedListIterator implements Iterator<E> {
        Node<E> curr = tail != null ? tail.getNext() : null;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getData();
            curr = (curr == tail) ? null : curr.getNext();
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<E> head = tail.getNext();
        if (size == 1) {
            tail = null;
        } else {
            tail.setNext(head.getNext());
        }
        size--;
        return head.getData();
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            E data = tail.getData();
            tail = null;
            size--;
            return data;
        }
        Node<E> pred = tail.getNext();
        while (pred.getNext() != tail) {
            pred = pred.getNext();
        }
        E data = tail.getData();
        pred.setNext(tail.getNext());
        tail = pred;
        size--;
        return data;
    }

    @Override
    public void addFirst(E e) {
        if (isEmpty()) {
            tail = new Node<>(e, null);
            tail.setNext(tail);
        } else {
            Node<E> newNode = new Node<>(e, tail.getNext());
            tail.setNext(newNode);
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        addFirst(e);
        rotate();
    }


    public String toString() {
        if (tail == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;
            sb.append(curr.getData());
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}
