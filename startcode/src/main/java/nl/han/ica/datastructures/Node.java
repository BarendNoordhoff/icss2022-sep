package nl.han.ica.datastructures;

public class Node<T> {
    public T value;
    public Node<T> next;

    Node(T value) {
        this.value = value;
        this.next = null;
    }
}
