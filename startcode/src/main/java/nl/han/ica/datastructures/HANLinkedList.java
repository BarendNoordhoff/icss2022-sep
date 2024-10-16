package nl.han.ica.datastructures;

import java.util.Iterator;

public class HANLinkedList<T> implements IHANLinkedList<T> {
    Node<T> header;
    int size;

    public HANLinkedList() {
        header = new Node<T>(null);
        size = 0;
    }

    @Override
    public void addFirst(T value) {
        header = new Node<T>(value);
        size++;
    }

    @Override
    public void clear() {
        header.next = null;
        size = 0;
    }

    @Override
    public void insert(int index, T value) {
        Node<T> newNode = new Node<T>(value);
        Node<T> current = header;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    @Override
    public void delete(int pos) {
        Node<T> current = header;

        for (int i = 0; i < pos; i++) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        } else {
            current = null;
        }
        size--;
    }

    @Override
    public T get(int pos) {
        Node<T> current = header;

        for (int i = 0; i < pos; i++) {
            current = current.next;
        }

        return current.value;
    }

    @Override
    public void removeFirst() {
        header.next = header.next.next;
    }

    @Override
    public T getFirst() {
        return header.next.value;
    }

    @Override
    public int getSize() {
        return size;
    }
}
