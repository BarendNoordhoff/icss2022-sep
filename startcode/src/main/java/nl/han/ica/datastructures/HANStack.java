package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T> {
    HANLinkedList<T> stack;

    public HANStack() {
        stack = new HANLinkedList<>();
    }

    @Override
    public void push(T value) {
        stack.addFirst(value);
    }

    @Override
    public T pop() {
        T val = stack.getFirst();
        stack.delete(0);
        return val;
    }

    @Override
    public T peek() {
        return stack.getFirst();
    }
}
