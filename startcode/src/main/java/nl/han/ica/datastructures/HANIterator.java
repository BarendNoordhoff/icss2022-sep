package nl.han.ica.datastructures;

import java.util.Iterator;

public class HANIterator<T> implements Iterator<T> {
    private Node<T> current;

    public HANIterator(Node<T> current) {
        this.current = current.next;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            return null;
        }
        Node<T> next = current;
        current = current.next;
        return next.value;
    }
}
